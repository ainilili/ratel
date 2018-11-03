package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.entity.ServerTransferData;

import io.netty.channel.Channel;

public interface ServerEventListener<T> {

	public void call(Channel channel, ServerTransferData<T> serverTransferData);

}
