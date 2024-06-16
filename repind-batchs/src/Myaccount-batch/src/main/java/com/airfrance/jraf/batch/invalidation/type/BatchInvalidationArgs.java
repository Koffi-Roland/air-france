package com.airfrance.jraf.batch.invalidation.type;
import com.airfrance.jraf.batch.common.BatchArgs;
import com.airfrance.jraf.batch.common.type.RequirementEnum;
import com.airfrance.jraf.batch.individu.exception.BatchException;
import com.airfrance.ref.exception.jraf.JrafDomainException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BatchInvalidationArgs extends BatchArgs {

	// Arguments
	private static String fileToProcess = null;
	public static String currentFileTraited = null;
	protected BufferedReader bfr;
	private File inputFile;

	protected Boolean trace = false;

	protected enum TraceLevel {
		ALL, DEBUG, INFO, WARN, ERROR, FATAL, OFF
	};

	protected TraceLevel traceLevel;

	protected enum SignatureType {
		FBSP, CRMP
	};

	protected SignatureType signatureType;

	protected Boolean commit = false;
	protected Boolean local = false;
	protected Boolean force = false;
	protected Boolean header = false;

	protected String localPath = "";

	public static final String ARGS_SEPARATOR = "-";
	public static final String SEPARATOR = ";";

	public static final String LOG_FILE_READ_ARGS = "Reading args...";
	public static final String LOG_FILE_EXECUTION = "Batch execution...";
	public static final String LOG_FILE_ERROR = "Error during test execution";

	public abstract void execute() throws JrafDomainException;

	public void openBufferReader(String inputFile) throws IOException {
		this.setInputFile(new File(inputFile));
		this.bfr = new BufferedReader(new FileReader(getInputFile()));

	}

	@Override
	protected void parseArgs(String[] args) throws BatchException {
		if (args == null || args.length == 0) {
			printHelp();
			throw new BatchException(NO_ARGUMENTS_TO_THE_BATCH);
		}
		List<BatchInvalidationArgsEnum> argsProvided = new ArrayList<BatchInvalidationArgsEnum>();
		for (int i = 0; i < args.length; i++) {

			String s = args[i];

			if (s.contains("-")) {
				// Type of arg
				String currentArg = s.split(ARGS_SEPARATOR)[1];

				try {

					BatchInvalidationArgsEnum currArgEnum = BatchInvalidationArgsEnum
							.valueOf(currentArg);
					argsProvided.add(currArgEnum);

					switch (currArgEnum) {
					case f:
						BatchInvalidationArgs.setFileToProcess(args[i + 1]);
						++i;
						break;
					case t:
						this.trace = true;
						setTraceLevel(args[i + 1]);
						break;
					case s:
						setSignature(args[i + 1]);
						break;
					case C:
						this.setCommit(true);
						break;
					case l:
						this.local = true;
						this.localPath = args[i + 1];
						break;
					case force:
						this.force = true;
						// REPIND-260 : SONAR - On doit terminer un "case" par un "break;"
						break;
					default:
						break;
					}
				}

				catch (IllegalArgumentException e) {
					printHelp();
					throw new BatchException(ARGUMENT_NOT_VALID);

				}

			}
		}

		// Check if all mandatory args are filled in
		for (BatchInvalidationArgsEnum currEnum : BatchInvalidationArgsEnum
				.values()) {

			if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY)) {

				if (!argsProvided.contains(currEnum)) {
					printHelp();
					throw new BatchException(MANDATORY_ARGUMENT_MISSING + currEnum);
				}
			}
		}

	}

	@Override
	protected void printHelp() {
		System.out
				.println("\n###\n"
						+ "USER GUIDE: \n"
						+ this.batchName
						+ " -option:\n"
						+ "-l path  : local path[OPTIONAL]\n"
						+ "-f file  : file to load [MANDATORY]\n"
						+ "-t       : trace mode on [OPTIONAL]\n"
						+ "-C       : commit mode on [OPTIONAL], only check validity of input file is done if commit mode = off\n"
						+ "-force   : force mode on [OPTIONAL], force the treatment even if input file validity is not OK "
						+ "\n");
	}

	// Getters and setters
	public static void setFileToProcess(String fileToProcess) {
		BatchInvalidationArgs.fileToProcess = fileToProcess;
	}

	public static String getFileToProcess() {
		return fileToProcess;
	}

	// Getter et Setter
	public static void setCurrentFileTraited(String currentFileTraited) {
		BatchInvalidationArgs.currentFileTraited = currentFileTraited;
	}

	public static String getCurrentFileTraited() {
		return currentFileTraited;
	}
	
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public String getTraceLevel() {
		return traceLevel.toString();
	}

	// ALL,DEBUG,INFO,WARN,ERROR,FATAL,OFF
	public void setTraceLevel(String d) {
		if (d.equalsIgnoreCase(TraceLevel.ALL.toString()))
			traceLevel = TraceLevel.ALL;
		else if (d.equalsIgnoreCase(TraceLevel.DEBUG.toString()))
			traceLevel = TraceLevel.DEBUG;
		else if (d.equalsIgnoreCase(TraceLevel.INFO.toString()))
			traceLevel = TraceLevel.INFO;
		else if (d.equalsIgnoreCase(TraceLevel.WARN.toString()))
			traceLevel = TraceLevel.WARN;
		else if (d.equalsIgnoreCase(TraceLevel.ERROR.toString()))
			traceLevel = TraceLevel.ERROR;
		else if (d.equalsIgnoreCase(TraceLevel.FATAL.toString()))
			traceLevel = TraceLevel.FATAL;
		else if (d.equalsIgnoreCase(TraceLevel.OFF.toString()))
			traceLevel = TraceLevel.OFF;
		// DEFAULT OPTION IF NOTHING CORRESPONDS BUT OPTION -t SET IN BATCH ARGS
		else
			traceLevel = TraceLevel.ALL;
	}

	public void setSignature(String s) {
		if (s.equalsIgnoreCase(SignatureType.FBSP.toString()))
			signatureType = SignatureType.FBSP;
		else if (s.equalsIgnoreCase(SignatureType.CRMP.toString()))
			signatureType = SignatureType.CRMP;
	}

	public String getSignature() {
		return signatureType.toString();
	}

	public BufferedReader getBfr() {
		return bfr;
	}

	public void setBfr(BufferedReader bfr) {
		this.bfr = bfr;
	}

	public void setCommit(Boolean commit) {
		this.commit = commit;
	}

	public Boolean getCommit() {
		return commit;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(Boolean local) {
		this.local = local;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public Boolean getTrace() {
		return trace;
	}

	public void setTrace(Boolean trace) {
		this.trace = trace;
	}
}
