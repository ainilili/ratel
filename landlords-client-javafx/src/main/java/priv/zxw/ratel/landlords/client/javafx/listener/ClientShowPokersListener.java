package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

import java.util.List;

public class ClientShowPokersListener extends AbstractClientListener {

    public ClientShowPokersListener() {
        super(ClientEventCode.CODE_SHOW_POKERS);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        // 找到上一个玩家的倒计时定时器将其关闭
        String clientNickname = jsonObject.getString("clientNickname");
        CountDownTask.CountDownFuture future = BeanUtil.getBean(clientNickname);
        if (future != null && !future.isDone()) {
            future.cancel();
        }

        User user = BeanUtil.getBean("user");
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");
        RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);

        // 把当前玩家的出牌和不出牌按钮隐藏掉
        // 更新玩家手中的牌
        List<Poker> sellPokerList = jsonObject.getJSONArray("pokers").toJavaList(Poker.class);
        user.removePokers(sellPokerList);

        if (user.getNickname().equals(clientNickname)) {
            Platform.runLater(() -> {
                method.hidePokerPlayButtons();
                method.refreshPlayPokers(user.getPokers());
            });
        } else {
            int sellPokerCount = sellPokerList.size();

            if (currentRoomInfo.getPrevPlayerName().equals(clientNickname)) {
                currentRoomInfo.setPrevPlayerSurplusPokerCount(currentRoomInfo.getPrevPlayerSurplusPokerCount() - sellPokerCount);
                Platform.runLater(() -> method.refreshPrevPlayerPokers(currentRoomInfo.getPrevPlayerSurplusPokerCount()));
            } else if (currentRoomInfo.getNextPlayerName().equals(clientNickname)) {
                currentRoomInfo.setNextPlayerSurplusPokerCount(currentRoomInfo.getNextPlayerSurplusPokerCount() - sellPokerCount);
                Platform.runLater(() -> method.refreshNextPlayerPokers(currentRoomInfo.getNextPlayerSurplusPokerCount()));
            }
        }

        // 显示上一把牌和上一个出牌玩家
        currentRoomInfo.setRecentPlayerName(clientNickname);
        currentRoomInfo.setRecentPokers(sellPokerList);

        Platform.runLater(() -> {
            method.showRecentPokers(currentRoomInfo.getRecentPokers());
            method.showPlayerMessage(currentRoomInfo.getRecentPlayerName(), "出牌");
        });

        // 找到下一个出牌玩家设置其定时器
        String nextPlayerName = jsonObject.getString("sellClinetNickname");
        if (nextPlayerName != null && !user.getNickname().equals(nextPlayerName)) {
            Label timer = (Label) method.getTimer(nextPlayerName);
            CountDownTask task = new CountDownTask(timer, 30, n -> {}, i -> Platform.runLater(() -> timer.setText(i.toString())));
            BeanUtil.addBean(nextPlayerName, task.start());
        }
    }
}
