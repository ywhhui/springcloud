package com.szcgc.agreement.model;

import com.szcgc.agreement.enums.UnionType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "templates")
public class TemplateEntity implements EntitySupport{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String name;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String type;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_100)
	private String filePath;

	@ManyToMany(mappedBy = "templates")
	private Set<UnionEntity> unions = new HashSet<>();

	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
	private Set<ContractEntity> contracts = new HashSet<>();


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Set<UnionEntity> getUnions() {
		return unions;
	}

	public void setUnions(Set<UnionEntity> unions) {
		this.unions = unions;
	}

	public Set<ContractEntity> getContracts() {
		return contracts;
	}

	public void setContracts(Set<ContractEntity> contracts) {
		this.contracts = contracts;
	}
}
