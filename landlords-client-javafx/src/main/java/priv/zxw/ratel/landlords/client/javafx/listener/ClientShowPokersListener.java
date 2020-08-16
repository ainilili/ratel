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
        RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");

        // 找到上一个玩家的倒计时定时器将其关闭
        String clientNickname = jsonObject.getString("clientNickname");
        CountDownTask.CountDownFuture future = BeanUtil.getBean(clientNickname);
        if (future != null && !future.isDone()) {
            future.cancel();
        }

        // 把当前玩家的出牌和不出牌按钮隐藏掉
        User user = BeanUtil.getBean("user");
        List<Poker> sellPokerList = jsonObject.getJSONArray("pokers").toJavaList(Poker.class);
        user.removePokers(sellPokerList);

        // 更新玩家手中的牌
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

        // 显示上一把牌，下一个玩家最近出的牌隐藏
        String nextPlayerName = jsonObject.getString("sellClinetNickname");
        currentRoomInfo.setRecentPlayerName(clientNickname);
        currentRoomInfo.setRecentPokers(sellPokerList);

        Platform.runLater(() -> {
            // 当前玩家以前出的牌隐藏，然后再渲染成最新出的牌
            method.hidePlayerRecentPokers(currentRoomInfo.getRecentPlayerName());
            method.showRecentPokers(currentRoomInfo.getRecentPlayerName(), currentRoomInfo.getRecentPokers());
            method.hidePlayerRecentPokers(nextPlayerName);

            // 下一个出牌玩家设置其定时器
            if (nextPlayerName != null && !user.getNickname().equals(nextPlayerName)) {
                Label timer = (Label) method.getTimer(nextPlayerName);
                CountDownTask task = new CountDownTask(timer, 30, n -> {}, i -> Platform.runLater(() -> timer.setText(i.toString())));
                BeanUtil.addBean(nextPlayerName, task.start());
            }
        });
    }

    private void updatePrevPlayerView() {}

    private void updatePlayerView() {}

    private void updateNextPlayerView() {}
}
