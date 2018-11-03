package org.nico.ratel.landlords.entity;

import java.io.Serializable;

import org.nico.ratel.landlords.enums.ServerEventCode;

public class ServerTransferData<T> implements Serializable{

	private static final long serialVersionUID = -5056177734642583364L;

	private int clientId;
	
	private int roomId;
	
	private ServerEventCode code;
	
	private T data;
	
	public ServerTransferData() {}
	
	public ServerTransferData(int clientId, int roomId, ServerEventCode code, T data) {
		this.clientId = clientId;
		this.roomId = roomId;
		this.code = code;
		this.data = data;
	}

	public final int getClientId() {
		return clientId;
	}

	public final void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public final int getRoomId() {
		return roomId;
	}

	public final void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public final ServerEventCode getCode() {
		return code;
	}

	public final void setCode(ServerEventCode code) {
		this.code = code;
	}

	public final T getData() {
		return data;
	}

	public final void setData(T data) {
		this.data = data;
	}

}
