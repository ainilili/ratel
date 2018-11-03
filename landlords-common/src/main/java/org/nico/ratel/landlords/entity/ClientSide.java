package org.nico.ratel.landlords.entity;

import java.io.Serializable;
import java.util.List;

import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.ClientType;

import io.netty.channel.Channel;

public class ClientSide implements Serializable{

	private static final long serialVersionUID = -1208782543528394339L;

	private int id;
	
	private int roomId;
	
	private String nickname;
	
	private List<Poker> pokers;
	
	private ClientStatus status;
	
	private ClientType type;
	
	private ClientSide next;
	
	private ClientSide pre;
	
	private transient Channel channel;
	
	public ClientSide() {}

	public ClientSide(int id, ClientStatus status, Channel channel) {
		this.id = id;
		this.status = status;
		this.channel = channel;
	}

	public final String getNickname() {
		return nickname;
	}

	public final void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public final Channel getChannel() {
		return channel;
	}

	public final void setChannel(Channel channel) {
		this.channel = channel;
	}

	public final int getRoomId() {
		return roomId;
	}

	public final void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public final List<Poker> getPokers() {
		return pokers;
	}

	public final void setPokers(List<Poker> pokers) {
		this.pokers = pokers;
	}

	public final ClientStatus getStatus() {
		return status;
	}

	public final void setStatus(ClientStatus status) {
		this.status = status;
	}

	public final ClientType getType() {
		return type;
	}

	public final void setType(ClientType type) {
		this.type = type;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final ClientSide getNext() {
		return next;
	}

	public final void setNext(ClientSide next) {
		this.next = next;
	}

	public final ClientSide getPre() {
		return pre;
	}

	public final void setPre(ClientSide pre) {
		this.pre = pre;
	}

}
