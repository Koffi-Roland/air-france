package com.airfrance.repind.util;

import com.airfrance.repind.dto.metric.ProcessingMetricDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ProcessingMetricLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessingMetricLogger.class);


    public static void log(ProcessingMetricDTO processingMetric) {
        MDC.put("labels.shipping_component", processingMetric.getTrigger());
        MDC.put("processType", enumToStringOrElseNull(processingMetric.getProcessType()));
        MDC.put("gin", processingMetric.getGin());
        MDC.put("gin_statuss", enumToStringOrElseNull(processingMetric.getStatus()));
        MDC.put("message", processingMetric.getMessage());
        LOGGER.info(processingMetric.getMessage());
        MDC.clear();
    }

    private static String toString(ProcessingMetricDTO processingMetric) {

        return processingMetric.getTrigger() + "|" +
                processingMetric.getProcessType() + "|" +
                processingMetric.getGin() + "|" +
                processingMetric.getStatus() + "|" +
                processingMetric.getMessage() + "|";
    }

    private static String enumToStringOrElseNull(Enum<?> enumeration) {
        if (enumeration != null) {
            return enumeration.name();
        }
        return "";
    }
}
