package org.nico.ratel.landlords.server.handler;

import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.server.event.ServerEventListener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	private final static String LISTENER_PREFIX = "org.nico.ratel.landlords.server.event.ServerEventListener_";
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof ServerTransferData) {
			ServerTransferData serverTransferData = (ServerTransferData) msg;
			
			ServerEventCode code = serverTransferData.getCode();
			
			if(code != null) {
				String eventListener = LISTENER_PREFIX + code.name();
				
				try {
					Class<ServerEventListener> listenerClass = (Class<ServerEventListener>) Class.forName(eventListener);
					ServerEventListener listener = listenerClass.newInstance();
					listener.call(ctx.channel(), serverTransferData);
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
