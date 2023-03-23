package com.szcgc.agreement.model;

import javax.persistence.*;

@Entity
@Table(name = "params")
public class ParamEntity implements EntitySupport{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(nullable = false, length = VAR_CHAR_50)
	private String name;                    // 合同字段参数名称--对应模板标签名称tag

	@Column(length = VAR_CHAR_200)
	private String value;                  // 合同字段参数值, 用于替换标签

	private int index;

	@ManyToOne(targetEntity = ContractEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	private ContractEntity contract;


	@ManyToOne(targetEntity = UnionEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "union_id", referencedColumnName = "id")
	private UnionEntity union;

	@ManyToOne(targetEntity = KeywordEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id", referencedColumnName = "id")
	private KeywordEntity keyword;

	public KeywordEntity getKeyword() {
		return keyword;
	}

	public void setKeyword(KeywordEntity keyword) {
		this.keyword = keyword;
	}

	public UnionEntity getUnion() {
		return union;
	}

	public void setUnion(UnionEntity union) {
		this.union = union;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ContractEntity getContract() {
		return contract;
	}

	public void setContract(ContractEntity contract) {
		this.contract = contract;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
