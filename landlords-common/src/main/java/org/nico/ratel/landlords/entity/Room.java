package org.nico.ratel.landlords.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.noson.annotations.JsonIgnore;
import org.nico.ratel.landlords.enums.RoomStatus;

public class Room implements Serializable{

	private static final long serialVersionUID = -9182226630057841379L;

	private int id;
	
	private String roomOwner;
	
	private RoomStatus status;
	
	private Map<Integer, ClientSide> clientSideMap;
	
	private LinkedList<ClientSide> clientSideList;
	
	private int landlordId;
	
	private List<Poker> landlordPokers;
	
	private PokerSell lastPokerShell;
	
	private int lastSellClient = -1;

	public Room() {
	}

	public Room(int id) {
		this.id = id;
		this.clientSideMap = new ConcurrentSkipListMap<>();
		this.clientSideList = new LinkedList<>();
		this.status = RoomStatus.BLANK;
	}

	public final PokerSell getLastPokerShell() {
		return lastPokerShell;
	}

	public final void setLastPokerShell(PokerSell lastPokerShell) {
		this.lastPokerShell = lastPokerShell;
	}

	public int getLastSellClient() {
		return lastSellClient;
	}

	public void setLastSellClient(int lastSellClient) {
		this.lastSellClient = lastSellClient;
	}

	public int getLandlordId() {
		return landlordId;
	}

	public void setLandlordId(int landlordId) {
		this.landlordId = landlordId;
	}

	public LinkedList<ClientSide> getClientSideList() {
		return clientSideList;
	}

	public void setClientSideList(LinkedList<ClientSide> clientSideList) {
		this.clientSideList = clientSideList;
	}

	public List<Poker> getLandlordPokers() {
		return landlordPokers;
	}

	public void setLandlordPokers(List<Poker> landlordPokers) {
		this.landlordPokers = landlordPokers;
	}

	public final String getRoomOwner() {
		return roomOwner;
	}

	public final void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
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

}
