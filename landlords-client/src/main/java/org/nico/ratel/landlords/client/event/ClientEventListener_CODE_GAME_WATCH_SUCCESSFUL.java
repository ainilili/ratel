package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import java.util.Map;

public class ClientEventListener_CODE_GAME_WATCH_SUCCESSFUL extends ClientEventListener {

    private static final String WATCH_SUCCESSFUL_TIPS = "                                                 \n" +
                                                        "+------------------------------------------------\n" +
                                                        "|You are already watching the game.      \n"         +
                                                        "|Room owner: %s. Room current status: %s.\n"         +
                                                        "+------------------------------------------------\n" +
                                                        "                                                   ";

    @Override
    public void call(Channel channel, String data) {
        // 修改User.isWatching状态
        // Edit User.isWatching
        User.INSTANCE.setWatching(true);

        // 进入观战提示
        // Enter spectator mode 
        Map<String, Object> map = MapHelper.parser(data);
        SimplePrinter.printNotice(String.format(WATCH_SUCCESSFUL_TIPS, map.get("owner"), map.get("status")));
    }
}
