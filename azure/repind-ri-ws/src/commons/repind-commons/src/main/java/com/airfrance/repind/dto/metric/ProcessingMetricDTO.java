package com.airfrance.repind.dto.metric;

public class ProcessingMetricDTO {
    private final String trigger;
    private final ProcessTypeEnum processType;
    private final String gin;
    private final StatusEnum status;
    private final String message;

    private ProcessingMetricDTO(String trigger, ProcessTypeEnum processType, String gin,
                               StatusEnum status, String message) {
        this.trigger = trigger;
        this.processType = processType;
        this.gin = gin;
        this.status = status;
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTrigger() {
        return trigger;
    }

    public ProcessTypeEnum getProcessType() {
        return processType;
    }

    public String getGin() {
        return gin;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
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
