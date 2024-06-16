package com.afklm.repind.common.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ProcessingMetricLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessingMetricLogger.class);

    private ProcessingMetricLogger(){}
    public static void log(ProcessingMetricDTO processingMetric) {
        MDC.put("processType", enumToStringOrElseNull(processingMetric.getProcessType()));
        MDC.put("consumerRecord", processingMetric.getConsumerRecord());
        MDC.put("status", enumToStringOrElseNull(processingMetric.getStatus()));
        MDC.put("gin", processingMetric.getGin());
        MDC.put("customMsg", processingMetric.getCustomMsg());
        LOGGER.info("MDC - customMsg={}", processingMetric.getCustomMsg());
        MDC.clear();
    }

    private static String enumToStringOrElseNull(Enum<?> enumeration) {
        if (enumeration != null) {
            return enumeration.name();
        }
        return "";
    }
}