package com.szcgc.third.tyc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * @Author liaohong
 * @create 2022/9/19 10:11
 */
@Configuration
@PropertySource("classpath:tyc.properties")
@ConfigurationProperties(prefix = "tyc")
public class TycConfig {

    /**
     * 静态实例
     */
    public static TycConfig self;

    @PostConstruct
    public void initSelf() {
        self = this;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 企业基本信息(工商信息)
     */
    private int gsBaseId;
    private String gsBaseUrl;

    /**
     * 股东信息
     */
    private int holderId;
    private String holderUrl;

    /**
     * 模糊搜索
     */
    private int searchId;
    private String searchUrl;

    public int getGsBaseId() {
        return gsBaseId;
    }

    public void setGsBaseId(int gsBaseId) {
        this.gsBaseId = gsBaseId;
    }

    public String getGsBaseUrl() {
        return gsBaseUrl;
    }

    public void setGsBaseUrl(String gsBaseUrl) {
        this.gsBaseUrl = gsBaseUrl;
    }

    public int getHolderId() {
        return holderId;
    }

    public void setHolderId(int holderId) {
        this.holderId = holderId;
    }

    public String getHolderUrl() {
        return holderUrl;
    }

    public void setHolderUrl(String holderUrl) {
        this.holderUrl = holderUrl;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }
}
