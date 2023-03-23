package com.szcgc.agreement.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
public class ContractEntity implements EntitySupport{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, updatable = false, unique = true, length = VAR_CHAR_50)
	private String serialNo;

	@Column(length = VAR_CHAR_50)
	private String name;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String projectCode;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_100)
	private String filePath;

	@ManyToOne(targetEntity = TemplateEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "template_id", referencedColumnName = "id", nullable = false)
	private TemplateEntity template;

	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
	private Set<ParamEntity> params = new HashSet<>();

	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public TemplateEntity getTemplate() {
		return template;
	}

	public void setTemplate(TemplateEntity template) {
		this.template = template;
	}

	public Set<ParamEntity> getParams() {
		return params;
	}

	public void setParams(Set<ParamEntity> params) {
		this.params = params;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
