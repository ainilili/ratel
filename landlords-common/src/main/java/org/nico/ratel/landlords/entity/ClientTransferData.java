package org.nico.ratel.landlords.entity;

import java.io.Serializable;

import org.nico.ratel.landlords.enums.ClientEventCode;

public class ClientTransferData<T> implements Serializable{

	private static final long serialVersionUID = -5144173747228946445L;

	private ClientEventCode code;
	
	private T data;
	
	private String msg;

	public ClientTransferData() {
	}

	public ClientTransferData(ClientEventCode code, T data) {
		this.code = code;
		this.data = data;
	}
	
	public ClientTransferData(ClientEventCode code, T data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
	}
	
	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

	public final ClientEventCode getCode() {
		return code;
	}

	public final void setCode(ClientEventCode code) {
		this.code = code;
	}

	public final T getData() {
		return data;
	}

	public final void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ClientTransferData [code=" + code + ", data=" + data + ", msg=" + msg + "]";
	}

}
