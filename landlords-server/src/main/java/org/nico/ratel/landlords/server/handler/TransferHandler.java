package org.nico.ratel.landlords.server.handler;

import java.net.InetSocketAddress;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ServerTransferData.ServerTransferDataProtoc;
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
		SimplePrinter.printNotice("Has client connect to the server：" + clientSide.getId());
		
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_CONNECT, String.valueOf(clientSide.getId()));
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_NICKNAME_SET, null);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof ServerTransferDataProtoc) {
			ServerTransferDataProtoc serverTransferData = (ServerTransferDataProtoc) msg;
			
			ServerEventCode code = ServerEventCode.valueOf(serverTransferData.getCode());
			
			if(code != null) {
				ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(((InetSocketAddress)ctx.channel().remoteAddress()).getPort());
				
				ServerEventListener.get(code).call(client, serverTransferData.getData());
			}
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof java.io.IOException) {
			int clientId = ((InetSocketAddress)ctx.channel().remoteAddress()).getPort();
			ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(clientId);
			if(client != null) {
				SimplePrinter.printNotice("Has client exit to the server：" + clientId + " | " + client.getNickname());
				ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(client, null);
			}
		}else {
			SimplePrinter.printNotice("ERROR：" + cause.getMessage());
		}
	}
	
	
	
}
