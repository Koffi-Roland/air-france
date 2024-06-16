package com.airfrance.batch.common.metric;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProcessingMetricDTO {
    private final String trigger;
    private final ProcessTypeEnum processType;
    private final String gin;
    private final StatusEnum status;
    private final String message;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String trigger;
        private ProcessTypeEnum processType;
        private String gin;
        private StatusEnum status;
        private String message;

        public Builder withTrigger(String trigger) {
            this.trigger = trigger;
            return this;
        }

        public Builder withProcessType(ProcessTypeEnum processType) {
            this.processType = processType;
            return this;
        }

        public Builder withGin(String gin) {
            this.gin = gin;
            return this;
        }

        public Builder withStatus(StatusEnum status) {
            this.status = status;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ProcessingMetricDTO build() {
            return new ProcessingMetricDTO(trigger, processType, gin, status, message);
        }
    }
}

