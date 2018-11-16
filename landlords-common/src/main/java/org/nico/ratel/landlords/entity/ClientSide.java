package org.nico.ratel.landlords.entity;

import java.util.List;

import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.ClientType;

import io.netty.channel.Channel;

public class ClientSide{

	private int id;
	
	private int roomId;
	
	private String nickname;
	
	private List<Poker> pokers;
	
	private ClientStatus status;
	
	private ClientRole role;
	
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
	
	public void init() {
		roomId = 0;
		pokers = null;
		status = ClientStatus.TO_CHOOSE;
		type = null;
		next = null;
		pre = null;
	}

	public final ClientRole getRole() {
		return role;
	}

	public final void setRole(ClientRole role) {
		this.role = role;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientSide other = (ClientSide) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
