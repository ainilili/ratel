package priv.zxw.ratel.landlords.client.javafx.event;


import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListener;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListenerUtils;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILobbyEvent;


public class LobbyEvent implements ILobbyEvent {

    @Override
    public void selectPVPModal() {
        ClientListener listener = ClientListenerUtils.getListener(ClientEventCode.CODE_SHOW_OPTIONS_PVP);

        listener.handle(BeanUtil.getBean("channel"), ".");
    }

    @Override
    public void selectPVEModal() {
        ClientListener listener = ClientListenerUtils.getListener(ClientEventCode.CODE_SHOW_OPTIONS_PVE);

        listener.handle(BeanUtil.getBean("channel"), ".");
    }

    @Override
    public void createPVPRoom() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE, null);
    }

    @Override
    public void createPVERoom(int modal) {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE_PVE, String.valueOf(modal));
    }

    @Override
    public void showRooms() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
    }

    @Override
    public void joinRoom(int roomId) {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_ROOM_JOIN, String.valueOf(roomId));
    }
}
