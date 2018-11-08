package org.nico.ratel.landlords.server.handler;

import java.net.InetSocketAddress;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		
		ClientSide clientSide = new ClientSide(((InetSocketAddress)ch.remoteAddress()).getPort(), ClientStatus.TO_CHOOSE, ch);
		clientSide.setNickname(String.valueOf(clientSide.getId()));
		ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
		SimplePrinter.println("A client connects to the server：" + clientSide.getId());
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_SET_NICKNAME, null);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof ServerTransferData) {
			ServerTransferData serverTransferData = (ServerTransferData) msg;
			
			ServerEventCode code = serverTransferData.getCode();
			
			if(code != null) {
				ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(((InetSocketAddress)ctx.channel().remoteAddress()).getPort());
				
				ServerEventListener.get(code).call(client, serverTransferData.getData());
			}
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof java.io.IOException) {
			ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(((InetSocketAddress)ctx.channel().remoteAddress()).getPort());
			if(client != null) {
				SimplePrinter.println(client.getNickname() + " exit");
				ServerEventListener.get(ServerEventCode.CODE_PLAYER_EXIT).call(client, null);
			}
		}
	}
	
	
	
}
