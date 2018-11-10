package org.nico.ratel.landlords.entity;

import java.io.Serializable;

import org.nico.ratel.landlords.enums.ClientEventCode;

public class ClientTransferData implements Serializable{

	private static final long serialVersionUID = -5144173747228946445L;

	private ClientEventCode code;
	
	private String data;
	
	private String info;

	public ClientTransferData() {
	}

	public ClientTransferData(ClientEventCode code, String data) {
		this.code = code;
		this.data = data;
	}

	public ClientTransferData(ClientEventCode code, String data, String info) {
		this.code = code;
		this.data = data;
		this.info = info;
	}

	public final ClientEventCode getCode() {
		return code;
	}

	public final void setCode(ClientEventCode code) {
		this.code = code;
	}

	public final String getData() {
		return data;
	}

	public final void setData(String data) {
		this.data = data;
	}

	public final String getInfo() {
		return info;
	}

	public final void setInfo(String info) {
		this.info = info;
	}

}
