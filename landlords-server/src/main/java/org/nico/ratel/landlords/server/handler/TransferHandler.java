package org.nico.ratel.landlords.server.handler;

import java.net.InetSocketAddress;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ServerTransferData.ServerTransferDataProtoc;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		
		//init client info
		ClientSide clientSide = new ClientSide(getId(ctx.channel()), ClientStatus.TO_CHOOSE, ch);
		clientSide.setNickname(String.valueOf(clientSide.getId()));
		clientSide.setRole(ClientRole.PLAYER);
		
		ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
		SimplePrinter.serverLog("Has client connect to the server：" + clientSide.getId());
		
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_CONNECT, String.valueOf(clientSide.getId()));
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CLIENT_NICKNAME_SET, null);
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof ServerTransferDataProtoc) {
			ServerTransferDataProtoc serverTransferData = (ServerTransferDataProtoc) msg;
			ServerEventCode code = ServerEventCode.valueOf(serverTransferData.getCode());
			if(code != null && code != ServerEventCode.CODE_CLIENT_HEAD_BEAT) {
				ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(getId(ctx.channel()));
				SimplePrinter.serverLog(client.getId() + " | " + client.getNickname() + " do:" + code.getMsg());
				ServerEventListener.get(code).call(client, serverTransferData.getData());
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof java.io.IOException) {
			clientOfflineEvent(ctx.channel());
		}else {
			SimplePrinter.serverLog("ERROR：" + cause.getMessage());
			cause.printStackTrace();
		}
	}
	
    @Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
        if (evt instanceof IdleStateEvent) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            if (event.state() == IdleState.READER_IDLE) {  
                try{
                	clientOfflineEvent(ctx.channel());
                	ctx.channel().close();
                }catch(Exception e){
                }
            }  
        } else {  
            super.userEventTriggered(ctx, evt);  
        }  
    }  
	
    private int getId(Channel channel){
    	return ((InetSocketAddress)channel.remoteAddress()).getPort();
    }
    
    private void clientOfflineEvent(Channel channel){
    	int clientId = getId(channel);
    	ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(clientId);
    	if(client != null) {
			SimplePrinter.serverLog("Has client exit to the server：" + clientId + " | " + client.getNickname());
			ServerEventListener.get(ServerEventCode.CODE_CLIENT_OFFLINE).call(client, null);
		}
    }
}
