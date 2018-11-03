package org.nico.ratel.landlords.client.handler;

import org.nico.ratel.landlords.client.event.ClientEventListener;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.transfer.TransferProtocolUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	private final static String LISTENER_PREFIX = "org.nico.ratel.landlords.client.event.ClientEventListener_";
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		System.out.println(msg);
		System.out.println();
		
		if(msg instanceof ClientTransferData) {
			ClientTransferData clientTransferData = (ClientTransferData) msg;
			
			if(clientTransferData.getMsg() != null) {
				SimplePrinter.println(clientTransferData.getMsg());
			}
			
			ClientEventCode code = clientTransferData.getCode();
			
			if(code != null) {
				String eventListener = LISTENER_PREFIX + code.name();
				
				try {
					Class<ClientEventListener> listenerClass = (Class<ClientEventListener>) Class.forName(eventListener);
					ClientEventListener listener = listenerClass.newInstance();
					listener.call(ctx.channel(), clientTransferData);
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
