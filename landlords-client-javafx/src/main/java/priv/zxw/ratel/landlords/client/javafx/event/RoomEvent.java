package priv.zxw.ratel.landlords.client.javafx.event;


import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IRoomEvent;

public class RoomEvent implements IRoomEvent {

    @Override
    public void robLandlord() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "TRUE");
    }

    @Override
    public void notRobLandlord() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "FALSE");
    }
}
