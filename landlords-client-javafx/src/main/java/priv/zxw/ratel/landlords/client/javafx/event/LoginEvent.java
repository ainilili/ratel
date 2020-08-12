package priv.zxw.ratel.landlords.client.javafx.event;


import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILoginEvent;

public class LoginEvent implements ILoginEvent {

    @Override
    public void setNickname(String nickname) {
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        nettyClient.setUsername(nickname);

        User user = new User(nickname);
        BeanUtil.addBean("user", user);

        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CLIENT_NICKNAME_SET, nickname);
    }
}
