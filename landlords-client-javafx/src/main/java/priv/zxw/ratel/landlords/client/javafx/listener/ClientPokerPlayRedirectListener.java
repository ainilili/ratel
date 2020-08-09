package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;

public class ClientPokerPlayRedirectListener extends AbstractClientListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPokerPlayRedirectListener.class);

    public ClientPokerPlayRedirectListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        int sellClientId = jsonObject.getIntValue("sellClientId");

        if (sellClientId == nettyClient.getId()) {
            ClientListenerUtils.getListener(ClientEventCode.CODE_GAME_POKER_PLAY).handle(channel, json);
        }

        LOGGER.info("下一个出牌玩家为 {} ", jsonObject.getString("sellClinetNickname"));
    }
}
