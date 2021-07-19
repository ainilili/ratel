package org.nico.ratel.landlords.server.handler;

import java.util.List;

import org.nico.ratel.landlords.entity.ServerTransferData.ServerTransferDataProtoc;

import com.google.protobuf.MessageLite;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class SecondProtobufCodec extends MessageToMessageCodec<ServerTransferDataProtoc, MessageLite> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) {
		out.add(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ServerTransferDataProtoc msg, List<Object> out) {
		out.add(msg);
	}


}
