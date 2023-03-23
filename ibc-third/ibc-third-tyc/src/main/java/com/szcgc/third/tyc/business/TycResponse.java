package com.szcgc.third.tyc.business;

public class TycResponse<T> {
	public int error_code;
	public String reason; 
	public T result;
}
