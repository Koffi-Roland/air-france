package com.afklm.repind.msv.forgetme.asked.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="FORGOTTEN_INDIVIDUAL")
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ForgottenIndividual implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * forgottenIndividualId
         */
        @Id
        @Column(name = "FORGOTTEN_INDIVIDUAL_ID")
        private Long forgottenIndividualId;

        /**
         * identifier
         */
        @Column(name = "IDENTIFIER")
        private String identifier;

        /**
         * identifierType
         */
        @Column(name = "IDENTIFIER_TYPE")
        private String identifierType;

        /**
         * context
         */
        @Column(name = "CONTEXT")
        private String context;

        /**
         * deletionDate
         */
        @Column(name = "DELETION_DATE")
        private Date deletionDate;

        /**
         * signature
         */
        @Column(name = "SIGNATURE")
        private String signature;

        /**
         * applicationCode
         */
        @Column(name = "APPLI")
        private String applicationCode;

        /**
         * site
         */
        @Column(name = "SITE")
        private String site;

        /**
         * modificationDate
         */
        @Column(name = "MODIFICATION_DATE")
        private Date modificationDate;

}
