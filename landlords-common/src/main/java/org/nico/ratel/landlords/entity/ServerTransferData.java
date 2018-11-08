package org.nico.ratel.landlords.entity;

import java.io.Serializable;

import org.nico.ratel.landlords.enums.ServerEventCode;

public class ServerTransferData implements Serializable{

	private static final long serialVersionUID = -5056177734642583364L;

	private ServerEventCode code;
	
	private String data;

	public ServerTransferData() {
	}

	public ServerTransferData(ServerEventCode code, String data) {
		this.code = code;
		this.data = data;
	}

	public ServerTransferData(ServerEventCode code) {
		this.code = code;
	}

	public final ServerEventCode getCode() {
		return code;
	}

	public final void setCode(ServerEventCode code) {
		this.code = code;
	}

	public final String getData() {
		return data;
	}

	public final void setData(String data) {
		this.data = data;
	}

}
