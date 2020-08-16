package priv.zxw.ratel.landlords.client.javafx.event;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IRoomEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void submitPokers(List<Poker> pokerList) {
        Channel channel = BeanUtil.getBean("channel");

        String[] chars = pokerList.stream()
                                  .sorted(Comparator.comparingInt(poker -> poker.getLevel().getLevel()))
                                  .map(p -> {
                                      String name = p.getLevel().getName();
                                      // 10 实际出牌值为 0
                                      return name.length() > 1 ? name.substring(1, 2) : name;
                                  })
                                  .collect(Collectors.toList())
                                  .toArray(new String[] {});
        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY, JSONObject.toJSONString(chars));
    }

    @Override
    public void passRound() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_PASS, null);
    }

    @Override
    public void exit() {
        Channel channel = BeanUtil.getBean("channel");

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT, null);
    }
}
