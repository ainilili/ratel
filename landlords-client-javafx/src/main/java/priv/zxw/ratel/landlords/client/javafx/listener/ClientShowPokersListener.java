package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientShowPokersListener extends AbstractClientListener {

    public ClientShowPokersListener() {
        super(ClientEventCode.CODE_SHOW_POKERS);
    }

    @Override
    public void handle(Channel channel, String json) {
        // 显示上一把牌和上一个出牌玩家
        JSONObject jsonObject = JSONObject.parseObject(json);
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");
        currentRoomInfo.setRecentPlayerName(jsonObject.getString("clientNickname"));
        currentRoomInfo.setRecentPokers(jsonObject.getJSONArray("pokers").toJavaList(Poker.class));

        RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);

        Platform.runLater(() -> {
            method.showRecentPokers(currentRoomInfo.getRecentPokers());
            method.showPlayerMessage(currentRoomInfo.getRecentPlayerName(), "出牌");
        });

        // 显示下一个出牌玩家
        if (jsonObject.containsKey("sellClinetNickname")) {
            Platform.runLater(() ->
                method.showTimer(jsonObject.getString("sellClinetNickname"), 30)
            );
        }
    }
}
