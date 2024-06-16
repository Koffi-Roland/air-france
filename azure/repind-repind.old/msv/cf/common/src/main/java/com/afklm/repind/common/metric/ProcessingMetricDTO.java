package com.afklm.repind.common.metric;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessingMetricDTO {
    private final ProcessTypeEnum processType;
    private final String consumerRecord;
    private final StatusEnum status;
    private final String gin;
    private final String customMsg;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ProcessTypeEnum processType;
        private String consumerRecord;
        private StatusEnum status;
        private String gin;
        private String customMsg;



        public Builder withProcessType(ProcessTypeEnum processType) {
            this.processType = processType;
            return this;
        }

        public Builder withConsumerRecord(String consumerRecord) {
            this.consumerRecord = consumerRecord;
            return this;
        }

        public Builder withStatus(StatusEnum status) {
            this.status = status;
            return this;
        }

        public Builder withGin(String gin) {
            this.gin = gin;
            return this;
        }


        public Builder withCustomMsg(String message) {
            this.customMsg = message;
            return this;
        }


        public ProcessingMetricDTO build() {
            return new ProcessingMetricDTO(processType, consumerRecord, status, gin, customMsg);
        }
    }
}
