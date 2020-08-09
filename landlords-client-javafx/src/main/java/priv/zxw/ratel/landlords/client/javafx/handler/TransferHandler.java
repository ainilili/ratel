package priv.zxw.ratel.landlords.client.javafx.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientTransferData.ClientTransferDataProtoc;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListener;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListenerUtils;


public class TransferHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ClientTransferDataProtoc clientTransferData = (ClientTransferDataProtoc) msg;

            if (clientTransferData.getInfo() != null && !clientTransferData.getInfo().isEmpty()) {
                SimplePrinter.printNotice(clientTransferData.getInfo());
            }

            ClientEventCode code = ClientEventCode.valueOf(clientTransferData.getCode());

            LOGGER.info("接受服务端信息, 编码：{},数据：{}.", code, clientTransferData.getData());

            if (code != null) {
                ClientListener listener = ClientListenerUtils.getListener(code);

                if (listener != null) {
                    listener.handle(ctx.channel(), clientTransferData.getData());
                } else {
                    LOGGER.warn("未知的消息编码 {}，忽略该条消息： {}", code, clientTransferData.getData());
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                ChannelUtils.pushToServer(ctx.channel(), ServerEventCode.CODE_CLIENT_HEAD_BEAT, "heartbeat");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
