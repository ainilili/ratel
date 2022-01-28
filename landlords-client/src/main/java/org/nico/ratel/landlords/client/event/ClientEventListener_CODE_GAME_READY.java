package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import java.util.Map;

public class ClientEventListener_CODE_GAME_READY extends ClientEventListener {
    @Override
    public void call(Channel channel, String data) {
        Map<String, Object> map = MapHelper.parser(data);
        if (SimpleClient.id == (int) map.get("clientId")) {
            SimplePrinter.printNotice("you are ready to play game.");
            return;
        }
        SimplePrinter.printNotice(map.get("clientNickName").toString() + " is ready to play game.");
    }

    static void gameReady(Channel channel) {
        SimplePrinter.printNotice("\nDo you want to continue the game? [Y/N]");
        String line = SimpleWriter.write(User.INSTANCE.getNickname(), "notReady");
        if (line.equals("Y") || line.equals("y")) {
            ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_READY, "");
            return;
        }
        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT, "");
    }
}
