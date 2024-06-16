package com.airfrance.repind.util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Component
public class LoggerUtils {

    Map<String, StopWatch> stopWatchMap = new HashMap<>();

    public static String buidErrorMessage(Exception e) {
        return "Error - " + e.toString();
    }

    /**
     * Create a StopWatch with a stopWatchName
     *
     * @param stopWatchName Key for the stopwatch of you want to start
     */
    public void startStopWatch(String stopWatchName) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        stopWatchMap.put(stopWatchName, stopwatch);
    }

    /**
     * Stop a StopWatch link to a stopWatchName
     *
     * @param stopWatchName Key for the stopwatch of you want to stop
     */
    public void stopStopWatch(String stopWatchName) {
        if (stopWatchMap.containsKey(stopWatchName) && stopWatchMap.get(stopWatchName).isRunning()) {
            stopWatchMap.get(stopWatchName).stop();
        }
    }

    /**
     * Print start log message
     *
     * @param log           Logger instance of your class
     * @param uuid          UUID of the instance of you want to log
     * @param logType       Type of log. For exemple "ENDPOINT","METHOD"
     * @param logTypeName   Name of the type. For exemple "ProvideIndividualData"
     * @param dataType      Type of the input of the endpoint or method (exemple: "GIN")
     * @param dataValue     Value of the input
     * @param stopWatchName If not null create a stopwatch with this name as key
     */
    public void logStartMessage(Logger log, UUID uuid, String logType, String logTypeName, String dataType, String dataValue, String stopWatchName) {
        if (StringUtils.isNotEmpty(stopWatchName)) {
            startStopWatch(stopWatchName);
        }
        log.info(String.format("UUID: %s - %s: %s - %s: %s - START", uuid.toString(), logType, logTypeName, dataType, dataValue));

    }

    /**
     * Print start log message
     *
     * @param log           Logger instance of your class
     * @param uuid          UUID of the instance of you want to log
     * @param logType       Type of log. For exemple "ENDPOINT","METHOD"
     * @param logTypeName   Name of the type. For exemple "ProvideIndividualData"
     * @param stopWatchName If not null create a stopwatch with this name as key
     */
    public void logStartMessage(Logger log, UUID uuid, String logType, String logTypeName, String stopWatchName) {
        if (StringUtils.isNotEmpty(stopWatchName)) {
            startStopWatch(stopWatchName);
        }
        log.info(String.format("UUID: %s - %s: %s - START", uuid.toString(), logType, logTypeName));

    }

    /**
     * Print end log message with/without duration
     *
     * @param log                   Logger instance of your class
     * @param uuid                  UUID of the instance of you want to log
     * @param logType               Type of log for exemple "ENDPOINT","METHOD"
     * @param logTypeName           Name of the type. For exemple "ProvideIndividualData"
     * @param dataType              Type of the input of the endpoint or method (exemple: "GIN")
     * @param dataValue             Value of the input
     * @param stopWatchName         If not null stop a stopwatch with this name as key and print log with duration
     */
    public void logStopMessage(Logger log, UUID uuid, String logType, String logTypeName, String dataType, String dataValue, String stopWatchName) {
        if (StringUtils.isNotEmpty(stopWatchName)) {
            stopStopWatch(stopWatchName);
            log.info(String.format("UUID: %s - %s: %s - %s: %s - END - DURATION: %s ms", uuid.toString(), logType, logTypeName, dataType, dataValue, getStopWatch(stopWatchName).getTotalTimeMillis()));
        } else {
            log.info(String.format("UUID: %s - %s: %s - %s: %s - END", uuid.toString(), logType, logTypeName, dataType, dataValue));
        }
    }

    /**
     * Print end log message with/without duration
     *
     * @param log           Logger instance of your class
     * @param uuid          UUID of the instance of you want to log
     * @param logType       Type of log for exemple "ENDPOINT","METHOD"
     * @param logTypeName   Name of the type. For exemple "ProvideIndividualData"
     * @param stopWatchName If not null stop a stopwatch with this name as key and print log with duration
     */
    public void logStopMessage(Logger log, UUID uuid, String logType, String logTypeName, String stopWatchName) {
        if (StringUtils.isNotEmpty(stopWatchName)) {
            stopStopWatch(stopWatchName);
            log.info(String.format("UUID: %s - %s: %s - END - DURATION: %s ms", uuid.toString(), logType, logTypeName, getStopWatch(stopWatchName).getTotalTimeMillis()));
        } else {
            log.info(String.format("UUID: %s - %s: %s - END", uuid.toString(), logType, logTypeName));
        }

    }

    /**
     * Return StopWatch with specific stopWatchName
     *
     * @param stopWatchName Key for the stopwatch of you want to get
     * @return The stopWatch if found or null
     */
    public StopWatch getStopWatch(String stopWatchName) {
        if (stopWatchMap.containsKey(stopWatchName)) {
            return stopWatchMap.get(stopWatchName);
        }
        return null;
    }
}
