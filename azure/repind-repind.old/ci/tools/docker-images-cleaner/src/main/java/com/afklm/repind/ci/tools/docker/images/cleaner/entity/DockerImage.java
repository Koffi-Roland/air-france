package com.afklm.repind.ci.tools.docker.images.cleaner.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CI_BAMBOO_DOCKER_IMAGES")
@Getter
@Setter
public class DockerImage implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "SEQ_CI_BAMBOO_DOCKER_IMAGES"
	)
	@SequenceGenerator(
			name = "SEQ_CI_BAMBOO_DOCKER_IMAGES",
			allocationSize = 1
	)
	@Column(name="ID", length = 19, nullable = false)
	private Long id;

	@Column(name="REPOSITORY", nullable = false)
	private String repository;

	@Column(name="TAG", nullable = false)
	private String tag;

	@Column(name = "INSERT_AT", nullable = false)
	private Date insertAt;

	@Column(name = "DELETE_AT")
	private Date deleteAt;
}