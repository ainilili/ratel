package org.nico.ratel.landlords.client.handler;

import org.nico.ratel.landlords.client.event.ClientEventListener;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.transfer.TransferProtocolUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof ClientTransferData) {
			ClientTransferData clientTransferData = (ClientTransferData) msg;
			
			if(clientTransferData.getInfo() != null) {
				SimplePrinter.println(clientTransferData.getInfo());
			}
			
			ClientEventCode code = clientTransferData.getCode();
			
			if(code != null) {
				ClientEventListener.get(code).call(ctx.channel(), clientTransferData);
			}
		}
	}
	
}
