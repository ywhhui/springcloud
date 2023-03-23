package com.szcgc.account.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

/**
 * @Author liaohong
 * @create 2020/9/23 15:19
 */
@Entity
@Table(name = "s_bankbranchinfo", schema = "gmis_account")
public class BankBranchInfo {

    @Id
    @Column(name = "id", length = 11)
    private int id;//id非自增，取值前3位为银行编码，后3位为自增编码

    @Schema(description = " 支行名")
    @Column(length = 50)
    private String name;

    private int bankId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getKey() {
        return bankId + name;
    }

    public ArrayList<Integer> toBankBranchList(){
        ArrayList<Integer> list=new ArrayList<>();
        list.add(bankId);
        list.add(id);
        return list;
    }
}
