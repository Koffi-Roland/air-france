package com.afklm.repind.common.entity.individual;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "PROFILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * It's an entity designed to store data about Profile
 */
public class ProfilsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SGIN", length = 12, nullable = false, unique = true, updatable = false)
    private String gin;

    /**
     * code langue
     */
    @Column(name = "SCODE_LANGUE")
    private String codeLangue;
}
