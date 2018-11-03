package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;

import io.netty.channel.Channel;

public interface ClientEventListener<T> {

	public void call(Channel channel, ClientTransferData<T> clientTransferData);

}
