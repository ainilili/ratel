package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

import java.util.List;

public class ClientStartGameListener extends AbstractClientListener {

    public ClientStartGameListener() {
        super(ClientEventCode.CODE_GAME_STARTING);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        // 设置用户信息和当前对局房间信息
        User user = BeanUtil.getBean("user");
        user.addPokers(jsonObject.getJSONArray("pokers").toJavaList(Poker.class));
        user.joinRoom(jsonObject.getIntValue("roomId"));

        CurrentRoomInfo currentRoomInfo = new CurrentRoomInfo(jsonObject.getIntValue("roomId"),
                                                              jsonObject.getString("roomOwner"));
        currentRoomInfo.setPlayer(user);
        currentRoomInfo.setPrevPlayerSurplusPokerCount(17);
        currentRoomInfo.setNextPlayerSurplusPokerCount(17);
        BeanUtil.addBean("currentRoomInfo", currentRoomInfo);

        // 计算出玩家的顺序
        List<ClientSide> clientOrderList = jsonObject.getJSONArray("clientOrderList").toJavaList(ClientSide.class);
        ClientSide clientSide = clientOrderList.stream().filter(c -> user.getNickname().equals(c.getNickname())).findFirst().get();
        currentRoomInfo.setPrevPlayerName(clientSide.getPre().getNickname());
        currentRoomInfo.setNextPlayerName(clientSide.getNext().getNickname());

        // 更新试图
        Method lobbyMethod = uiService.getMethod(LobbyController.METHOD_NAME);
        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);

        Platform.runLater(() -> {
            // 客户端加入房间时，可能没有触发 joinRoomSuccessful 的事件
            // 导致视图未正常切换，判断视图是否切换，否则进行试图切换
            if (!roomMethod.isShow()) {
                lobbyMethod.doClose();
                roomMethod.joinRoom();
            }

            roomMethod.startGame(user.getPokers());
        });

        // 触发抢地主(CODE_GAME_LANDLORD_ELECT)事件
        ClientListenerUtils.getListener(ClientEventCode.CODE_GAME_LANDLORD_ELECT).handle(channel, json);
    }
}
