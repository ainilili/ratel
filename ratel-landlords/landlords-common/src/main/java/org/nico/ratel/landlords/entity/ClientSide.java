package org.nico.ratel.landlords.entity;

import java.util.List;

import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.ClientType;

import io.netty.channel.Channel;

public class ClientSide {

	private int id;
	
	private int serverId;
	
	private List<Poker> pokers;
	
	private ClientStatus status;
	
	private ClientType type;
	
	private ClientSide next;
	
	private ClientSide pre;
	
	private Channel channel;
	
	public final Channel getChannel() {
		return channel;
	}

	public final void setChannel(Channel channel) {
		this.channel = channel;
	}

	public final int getServerId() {
		return serverId;
	}

	public final void setServerId(int serverId) {
		this.serverId = serverId;
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
