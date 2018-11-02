package org.nico.ratel.landlords.server.handler;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.handler.DefaultDecoder;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.view.ViewPrinter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		ClientSide clientSide = new ClientSide();
		clientSide.setId(ServerContains.getClientId());
		clientSide.setStatus(ClientStatus.TO_CHOOSE);
		clientSide.setChannel(ctx.channel());
		ServerContains.CLIENT_SIDE_LIST.add(clientSide);
		
		ViewPrinter.view("One client join nico landlord world ");
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new DefaultDecoder());
        pipeline.addLast("encoder", new ByteArrayEncoder());
        pipeline.addLast("handler", new TransferHandler());
	}
}
