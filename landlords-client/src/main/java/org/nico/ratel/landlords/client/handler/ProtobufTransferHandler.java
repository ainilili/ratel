package org.nico.ratel.landlords.client.handler;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.client.event.ClientEventListener;
import org.nico.ratel.landlords.entity.ClientTransferData.ClientTransferDataProtoc;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.HashMap;
import java.util.Map;

public class ProtobufTransferHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		if (msg instanceof ClientTransferDataProtoc) {
			ClientTransferDataProtoc clientTransferData = (ClientTransferDataProtoc) msg;
			if (!clientTransferData.getInfo().isEmpty()) {
				SimplePrinter.printNotice(clientTransferData.getInfo());
			}
			ClientEventCode code = ClientEventCode.valueOf(clientTransferData.getCode());
			if (User.INSTANCE.isWatching()) {
				Map<String, Object> wrapMap = new HashMap<>(3);
				wrapMap.put("code", code);
				wrapMap.put("data", clientTransferData.getData());

				ClientEventListener.get(ClientEventCode.CODE_GAME_WATCH).call(ctx.channel(), Noson.reversal(wrapMap));
			} else {
				ClientEventListener.get(code).call(ctx.channel(), clientTransferData.getData());
			}
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				ChannelUtils.pushToServer(ctx.channel(), ServerEventCode.CODE_CLIENT_HEAD_BEAT, "heartbeat");
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (cause instanceof java.io.IOException) {
			SimplePrinter.printNotice("The network is not good or did not operate for a long time, has been offline");
			System.exit(0);
		}
	}

}
