package org.nico.ratel.landlords.entity;

import org.nico.ratel.landlords.enums.ServerEventCode;

public class ServerTransferData {

	private ServerEventCode code;
	
	private String data;

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

	@Override
	public String toString() {
		return "ServerTransferData [code=" + code + ", data=" + data + "]";
	}
	
}
