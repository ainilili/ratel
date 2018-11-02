package org.nico.ratel.landlords.server.handler;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.transfer.TransferProtocolUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TransferHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		byte[] bs = (byte[]) msg;
		System.out.println(new String(bs));
		ClientTransferData clientTransferData = TransferProtocolUtils.unserialize(bs, ClientTransferData.class);
		System.out.println(clientTransferData.getData());
		System.out.println(clientTransferData.getServerId());
		System.out.println(clientTransferData.getCode());
		
		ServerTransferData serverTransferData = new ServerTransferData();
		serverTransferData.setCode(ServerEventCode.CODE_INTO);
		serverTransferData.setData("ok");
		
		byte[] to = TransferProtocolUtils.serialize(serverTransferData);
		ctx.writeAndFlush(to);
	}
	
}
