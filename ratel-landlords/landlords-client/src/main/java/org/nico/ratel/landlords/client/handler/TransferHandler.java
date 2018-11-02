package org.nico.ratel.landlords.client.handler;

import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.transfer.TransferProtocolUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		byte[] bs = (byte[]) msg;
		
		ServerTransferData serverTransferData = TransferProtocolUtils.unserialize(bs, ServerTransferData.class);
		System.out.println(serverTransferData);
	}
}
