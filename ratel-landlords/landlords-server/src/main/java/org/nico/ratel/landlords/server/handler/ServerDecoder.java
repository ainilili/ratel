package org.nico.trap.landlords.handler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ServerDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= 4) {
			byte[] bs = new byte[4];
			in.getBytes(0, bs);
			for(byte b: bs) {
				System.out.print(b + " ");
			}
			System.out.println();
			int i = in.readInt();
			out.add(i);
		}
	}

}
