package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientSelectLandlordListener extends AbstractClientListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSelectLandlordListener.class);

    public ClientSelectLandlordListener() {
        super(ClientEventCode.CODE_GAME_LANDLORD_ELECT);
    }

    @Override
    public void handle(Channel channel, String json) {
        // 计算出玩家的顺序
        JSONObject jsonObject = JSONObject.parseObject(json);
        String nextClientNickname = jsonObject.getString("nextClientNickname");

        // 决定接下来谁抢地主
        int turnClientId = jsonObject.getIntValue("nextClientId");
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");

        if (turnClientId == nettyClient.getId()) {
            RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
            Platform.runLater(() -> method.showRobButtons());
        }

        LOGGER.info("接下来由 {} 抢地主", nextClientNickname);
    }
}
