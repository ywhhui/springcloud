package com.szcgc.agreement.model;

import com.szcgc.agreement.enums.KeywordType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "keywords")
public class KeywordEntity implements EntitySupport{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String tag;                        // word模板中的关键字标签名称{{xxx}}

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String description;                // 返回前端展示页面的字段描述

	@Column(nullable = false, updatable = false, length = VAR_ENUM)
	private KeywordType type;

	@Column(nullable = false)
	private boolean required;

	@ManyToMany(targetEntity = UnionEntity.class, cascade = CascadeType.ALL)
	@JoinTable(name = "keyword_union",
			joinColumns = {@JoinColumn(name = "keyword_id", referencedColumnName = "id", nullable = false)},
			inverseJoinColumns = {@JoinColumn(name = "union_id", referencedColumnName = "id", nullable = false)})
	private Set<UnionEntity> unions = new HashSet<>();

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
	private Set<ParamEntity> params = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public KeywordType getType() {
		return type;
	}

	public void setType(KeywordType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Set<UnionEntity> getUnions() {
		return unions;
	}

	public void setUnions(Set<UnionEntity> unions) {
		this.unions = unions;
	}

	public Set<ParamEntity> getParams() {
		return params;
	}

	public void setParams(Set<ParamEntity> params) {
		this.params = params;
	}
}
