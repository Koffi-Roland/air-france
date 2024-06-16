package com.afklm.repind.common.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public final class FullRestError {

    /**
     * Builder of FullRestError
     *
     * @author m312812
     */
    public static class Builder {

        private HttpStatus status;
        private RestError error;
        private String technicalDetails;
        private String requestedURI;
        private Throwable throwable;

        private List<ErrorDetail> errorMessage;

        public FullRestError build() {
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            final FullRestError restError = new FullRestError(status, error, technicalDetails, requestedURI);

            if (errorMessage != null && !errorMessage.isEmpty()) {
                restError.setErrorMessage(errorMessage);
            }

            restError.setThrowable(throwable);

            return restError;
        }

        public Builder setErrorMessage(final List<ErrorDetail> errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder setRequestedURI(final String requestedURI) {
            this.requestedURI = requestedURI;
            return this;
        }

        public Builder setRestError(final RestError error) {
            this.error = error;
            return this;
        }

        public Builder setStatus(final HttpStatus status) {
            this.status = status;
            return this;
        }


        public Builder setTechnicalDetails(final String technicalDetails) {
            this.technicalDetails = technicalDetails;
            return this;
        }

        public Builder setThrowable(final Throwable throwable) {
            this.throwable = throwable;
            technicalDetails = throwable.getLocalizedMessage();
            return this;
        }
    }

    private final HttpStatus status;
    private final RestError error;
    private final String technicalDetails;
    private final String requestedURI;
    private List<ErrorDetail> errorMessage;
    @JsonIgnore
    private Throwable throwable;

    private FullRestError(final HttpStatus status, final RestError error, final String technicalDetails,
                          final String requestedURI) {
        if (status == null) {
            throw new IllegalArgumentException("HttpStatus argument cannot be null.");
        }
        this.status = status;
        this.error = error;
        this.technicalDetails = technicalDetails;
        this.requestedURI = requestedURI;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getStatus().value()).append(" (").append(getStatus().getReasonPhrase())
                .append(" )").toString();
    }

}
