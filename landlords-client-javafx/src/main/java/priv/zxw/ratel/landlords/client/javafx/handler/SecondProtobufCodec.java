package priv.zxw.ratel.landlords.client.javafx.handler;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.nico.ratel.landlords.entity.ClientTransferData;

import java.util.List;

/**
 * @ClassName SecondProtobufCodec
 * @Desc TODO
 * @Author zxw
 * @Date 2020/8/5 13:26
 * @Version 1.0
 */
public class SecondProtobufCodec extends MessageToMessageCodec<ClientTransferData.ClientTransferDataProtoc, MessageLite> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) throws Exception {
        out.add(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ClientTransferData.ClientTransferDataProtoc msg, List<Object> out) throws Exception {
        out.add(msg);
    }

}