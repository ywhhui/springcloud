package com.szcgc.agreement.model;

import com.szcgc.agreement.enums.UnionType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "unions")
public class UnionEntity implements EntitySupport{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String tag;                   // word模板中对应表格/区块对标签名称{{?xxx}} {{/xxx}} {{xxx}}

	@Column(nullable = false, updatable = false, length = VAR_CHAR_50)
	private String description;          // 返回前端展示页面的集合字段描述

	@Column(nullable = false, updatable = false, length = VAR_ENUM)
	private UnionType type;

	@ManyToMany(mappedBy = "unions")
	private Set<KeywordEntity> keywords = new HashSet<>();

	@ManyToMany(targetEntity = TemplateEntity.class, cascade = CascadeType.ALL)
	@JoinTable(name = "union_template",
			joinColumns = {@JoinColumn(name = "union_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "template_id", referencedColumnName = "id")})
	private Set<TemplateEntity> templates = new HashSet<>();

	@OneToMany(mappedBy = "union", cascade = CascadeType.ALL)
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

	public UnionType getType() {
		return type;
	}

	public void setType(UnionType type) {
		this.type = type;
	}

	public Set<KeywordEntity> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<KeywordEntity> keywords) {
		this.keywords = keywords;
	}

	public Set<TemplateEntity> getTemplates() {
		return templates;
	}

	public void setTemplates(Set<TemplateEntity> templates) {
		this.templates = templates;
	}

	public Set<ParamEntity> getParams() {
		return params;
	}

	public void setParams(Set<ParamEntity> params) {
		this.params = params;
	}
}
