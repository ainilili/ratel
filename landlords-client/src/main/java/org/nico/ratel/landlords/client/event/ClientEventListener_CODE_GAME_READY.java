package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.SimpleClient;
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
        SimplePrinter.printNotice("\nPress any key to get ready(Press [exit|e] to exit the room)");
        String line = SimpleWriter.write("notReady");
        if (line.equals("")) {
            gameReady(channel);
            return;
        }
        if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("e")) {
            ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT, "");
            return;
        }
        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_READY, "");
    }
}
