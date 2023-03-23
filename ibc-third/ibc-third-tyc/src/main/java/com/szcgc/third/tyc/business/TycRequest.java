package com.szcgc.third.tyc.business;


public abstract class TycRequest {

	protected int conId;
	protected String url;

	protected TycRequest(int conId, String url) {
		this.conId = conId;
		this.url = url;
	}

	public int getConId() {
		return conId;
	}

	public String getUrl() {
		return url;
	}
	
}
