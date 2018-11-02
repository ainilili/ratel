package org.nico.ratel.landlords.entity;

import org.nico.ratel.landlords.enums.ClientEventCode;

public class ClientTransferData {

	private int serverId;
	
	private ClientEventCode code;
	
	private String data;
	
	public ClientTransferData() {}

	public ClientTransferData(int serverId, ClientEventCode code, String data) {
		this.serverId = serverId;
		this.code = code;
		this.data = data;
	}

	public final int getServerId() {
		return serverId;
	}

	public final void setServerId(int serverId) {
		this.serverId = serverId;
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

	@Override
	public String toString() {
		return "ClientTransferData [serverId=" + serverId + ", code=" + code + ", data=" + data + "]";
	}
	
}
