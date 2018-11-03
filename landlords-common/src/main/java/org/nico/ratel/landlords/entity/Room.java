package org.nico.ratel.landlords.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.ratel.landlords.enums.RoomStatus;

public class Room implements Serializable{

	private static final long serialVersionUID = -9182226630057841379L;

	private int id;
	
	private RoomStatus status;
	
	private Map<Integer, ClientSide> clientSideMap;

	public Room(int id) {
		this.id = id;
		this.clientSideMap = new ConcurrentSkipListMap<>();
		this.status = RoomStatus.BLANK;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final RoomStatus getStatus() {
		return status;
	}

	public final void setStatus(RoomStatus status) {
		this.status = status;
	}

	public final Map<Integer, ClientSide> getClientSideMap() {
		return clientSideMap;
	}

	public final void setClientSideMap(Map<Integer, ClientSide> clientSideMap) {
		this.clientSideMap = clientSideMap;
	}

	@Override
	public String toString() {
		return status.getMsg() + "|" + clientSideMap.size() + " online";
	}

}
