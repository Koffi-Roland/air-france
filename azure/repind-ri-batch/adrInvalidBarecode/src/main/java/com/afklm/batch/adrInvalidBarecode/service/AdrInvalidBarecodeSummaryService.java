package com.afklm.batch.adrInvalidBarecode.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
@Setter
@Getter
@Slf4j
public class AdrInvalidBarecodeSummaryService {
	private static String PATH = "BARCODE_";

	private static final String DAY = new SimpleDateFormat("ddMMyyyy").format(new Date());

	private static final Set<PosixFilePermission> PERMISSIONS_FILE = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ);

	private static final Set<PosixFilePermission> PERMISSIONS_DIRECTORY = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ,
			PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);
	private final AtomicInteger processedLines = new AtomicInteger();
	private final AtomicInteger updatedLines = new AtomicInteger();
	private final AtomicInteger rejectedLines = new AtomicInteger();
	private final AtomicInteger rejectedAlreadyInvalidLines = new AtomicInteger();

	private final AtomicInteger rejectInvalidedAinUnknownLines = new AtomicInteger();

	private final AtomicInteger rejectedInvalidRoleContratGinNotMatchLines = new AtomicInteger();
	private final AtomicInteger rejectedInvalidRoleContratNotFoundLines = new AtomicInteger();

	private final AtomicInteger rejectedInvalidStatusXLines = new AtomicInteger();

	private final AtomicInteger rejectedInvalidHystoryLines = new AtomicInteger();

	private final AtomicInteger rejectedUnknownReasonLines = new AtomicInteger();

	public void incrementProcessedLinesCounter() { processedLines.incrementAndGet();}
	public void incrementUpdatededLinesCounter() { updatedLines.incrementAndGet();}
	public void incrementRejectedCounter() { rejectedLines.incrementAndGet();}
	public void incrementAlreadyInvalidLinesCounter() {rejectedAlreadyInvalidLines.incrementAndGet();}
	public void incrementInvalidedAinUnknownLinesCounter() {rejectInvalidedAinUnknownLines.incrementAndGet();}
	public void incrementInvalidRoleContratGinNotMatchLinesCounter() {rejectedInvalidRoleContratGinNotMatchLines.incrementAndGet();}
	public void incrementInvalidRoleContratNotFoundLinesCounter() {rejectedInvalidRoleContratNotFoundLines.incrementAndGet();}
	public void incrementInvalidStatusXLinesCounter() {rejectedInvalidStatusXLines.incrementAndGet();}
	public void incrementInvalidHystoryLinesCounter() {rejectedInvalidHystoryLines.incrementAndGet();}
	public void incrementUnknownReasonLinesCounter() {rejectedUnknownReasonLines.incrementAndGet();}

	public void generateMetricFile(String outputPath, String metricFileName, Date startBatch) {

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dDay = sdf.format(new Date());
		try {

			File metricFile = new File(Paths.get(outputPath, metricFileName).toString());
			try (BufferedWriter bfwMetricTxt = new BufferedWriter(new FileWriter(metricFile, true))) {
				bfwMetricTxt.write("COMPTE RENDU\n============" +
								"\nFin du batch le : " + dDay +
				        "\nDURATION : " + getDifferenceBetweenTwoDates(startBatch, new Date()) +
						" \n\n******************** " +
								" \n**** CONCLUSION **** " +
								" \n******************** " +
						"\nNombre total de lignes :" + this.getProcessedLines().get() +
						"\nNombre d'adresses modifiees :" + this.getUpdatedLines().get() +
						"\nNombre d'adresses rejetees :" + this.getRejectedLines().get()+
						"\n Dont :"+
								"\n 	Nombre d'adresses deja non valides :"+ this.getRejectedAlreadyInvalidLines() +
								"\n 	Nombre d'adresses rejet Adresses Ain Inconnu :"+ this.getRejectInvalidedAinUnknownLines() +
								"\n 	Nombre d'adresses rejet Adresses Gin ne correspond pas au Role contrat gin :"+ this.getRejectedInvalidRoleContratGinNotMatchLines() +
								"\n 	Nombre d'adresses rejet Role contrat non trouvé :"+ this.getRejectedInvalidRoleContratNotFoundLines() +
								"\n 	Nombre d'adresses rejet Adresses trouvé avec statut X (REMOVED) :"+ this.getRejectedInvalidStatusXLines() +
								"\n 	Nombre d'adresses rejet Adresses trouvé avec statut H (HISTORIZED) :"+ this.getRejectedInvalidHystoryLines() +
								"\n 	Nombre d'adresses rejet erreur inconnu :"+ this.getRejectedUnknownReasonLines() +
								"\n "+"\n "
				);
			}
			log.info("[+] Successfully generated metric file in location : {}", metricFile);
		} catch (IOException e) {
			log.error("[-] Error while generating metric file", e);
		}
	}

    public String zipFiles(String outputPath,String fileName,String resultFileName, String metricFileName)
            throws IOException {

        final Date date = new Date();
        final String zipFileName = PATH + DAY +"_"+date.getTime() + ".gz";

		File procfile = new File(Paths.get(outputPath, fileName).toString());
		File resultFile = new File(Paths.get(outputPath, resultFileName).toString());
		File metricFile = new File(Paths.get(outputPath, metricFileName).toString());
		List<File> myFiles = List.of(procfile, resultFile);

		FileOutputStream fos = new FileOutputStream(Paths.get(outputPath, zipFileName).toString());
		ZipOutputStream zos = new ZipOutputStream(fos);

		fileOutputStreamWrite(metricFile, zos);

		for(File inputFile: myFiles){
			fileOutputStreamWrite(inputFile, zos);
			if (inputFile.delete()) {
				log.info("File deleted successfully");
			}else {
				log.error("Failed to delete the file");
			}
		}
		zos.closeEntry();
		zos.close();

		if (SystemUtils.IS_OS_UNIX) {
			Files.setPosixFilePermissions(Paths.get(metricFile.toURI()), PERMISSIONS_FILE);
		}

        log.info("zip filename : " + zipFileName);

        return zipFileName;
    }

	public void fileOutputStreamWrite(File inputFile, ZipOutputStream zos) throws IOException {
		zos.putNextEntry(new ZipEntry(inputFile.getName()));
		byte[] bytes = Files.readAllBytes(inputFile.getAbsoluteFile().toPath());
		zos.write(bytes, 0, bytes.length);
	}
	protected String getDifferenceBetweenTwoDates(Date startDate, Date endDate){

		//milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		long elapsedMilli = different % secondsInMilli;

		return elapsedMinutes + " minutes, "+ elapsedSeconds + " seconds, " + elapsedMilli + " milliseconds. (total in milliseconds : " + different + ")";
	}
}
