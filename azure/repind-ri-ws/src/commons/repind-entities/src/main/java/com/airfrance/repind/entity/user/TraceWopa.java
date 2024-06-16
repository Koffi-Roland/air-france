package com.airfrance.repind.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TRACE_WOPA")
@Data
public class TraceWopa {

	/**
	 * Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_TRACE_WOPA")
	@SequenceGenerator(name = "ISEQ_TRACE_WOPA", sequenceName = "ISEQ_TRACE_WOPA", allocationSize = 1)
	@Column(name = "ID_TRACE")
	private Integer idTrace;

	/**
	 * Matricule
	 */
	@Column(name = "SMATRICULE")
	private String matricule;

	/**
	 * Last name
	 */
	@Column(name = "SLAST_NAME")
	private String lastName;

	/**
	 * First name
	 */
	@Column(name = "SFIRST_NAME")
	private String firstName;

	/**
	 * Connection date
	 */
	@Column(name = "DDATE_CONNECTION")
	private LocalDate dateConnection;

}
