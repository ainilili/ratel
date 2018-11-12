package org.nico.ratel.landlords.client.handler;

import org.nico.ratel.landlords.client.event.ClientEventListener;
import org.nico.ratel.landlords.entity.ClientTransferData.ClientTransferDataProtoc;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof ClientTransferDataProtoc) {
			ClientTransferDataProtoc clientTransferData = (ClientTransferDataProtoc) msg;
			
			if(clientTransferData.getInfo() != null && ! clientTransferData.getInfo().isEmpty()) {
				SimplePrinter.printNotice(clientTransferData.getInfo());
			}
			
			ClientEventCode code = ClientEventCode.valueOf(clientTransferData.getCode());
			
			if(code != null) {
				ClientEventListener.get(code).call(ctx.channel(), clientTransferData.getData());
			}
		}
	}
	
}
