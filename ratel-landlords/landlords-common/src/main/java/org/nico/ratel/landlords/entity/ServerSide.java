package org.nico.ratel.landlords.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.nico.ratel.landlords.enums.ServerStatus;

public class ServerSide {

	private int id;
	
	private ServerStatus status;
	
	private Map<Integer, Poker> pokers;

	public ServerSide(int id) {
		this.id = id;
		this.pokers = new LinkedHashMap<>(3);
		this.status = ServerStatus.BLANK;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final ServerStatus getStatus() {
		return status;
	}

	public final void setStatus(ServerStatus status) {
		this.status = status;
	}

	public final Map<Integer, Poker> getPokers() {
		return pokers;
	}

	public final void setPokers(Map<Integer, Poker> pokers) {
		this.pokers = pokers;
	}
	
}
