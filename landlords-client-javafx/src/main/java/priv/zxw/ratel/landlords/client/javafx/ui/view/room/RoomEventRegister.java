package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import javafx.scene.control.Button;
import org.nico.ratel.landlords.entity.Poker;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IRoomEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.EventRegister;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.util.List;

public class RoomEventRegister implements EventRegister {

    private UIObject uiObject;
    private IRoomEvent roomEvent;

    public RoomEventRegister(UIObject uiObject, IRoomEvent roomEvent) {
        this.uiObject = uiObject;
        this.roomEvent = roomEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        robLandlord();
        notRobLandlord();
        submitPokers();
        passRound();
    }

    private void robLandlord() {
        uiObject.$("robButton", Button.class).setOnAction(e -> {
            RoomController roomController = (RoomController) uiObject;
            roomController.hideRobButtons();

            roomEvent.robLandlord();
        });
    }

    private void notRobLandlord() {
        uiObject.$("notRobButton", Button.class).setOnAction(e -> {
            RoomController roomController = (RoomController) uiObject;
            roomController.hideRobButtons();

            roomEvent.notRobLandlord();
        });
    }

    private void submitPokers() {
        uiObject.$("submitButton", Button.class).setOnAction(e -> {
            CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");
            List<Poker> checkedPokers = currentRoomInfo.pollCheckedPokers();

            if (checkedPokers.isEmpty()) {
                return;
            }

            // 执行对应的事件
            roomEvent.submitPokers(checkedPokers);
        });
    }

    private void hidePlayButtonsAndCancelCountDownTask() {
        // 取消CountDownFuture，如果CountDownFuture已经结束（代表用户已经操作超时）则什么都不做
        User user = BeanUtil.getBean("user");
        CountDownTask.CountDownFuture future = BeanUtil.getBean(user.getNickname());
        if (future.isDone()) {
            return;
        }

        future.cancel();

        // 隐藏对应的 buttons 和 timer
        RoomController roomController = (RoomController) uiObject;
        roomController.hidePokerPlayButtons();
    }

    private void passRound() {
        uiObject.$("passButton", Button.class).setOnAction(e -> {
            roomEvent.passRound();
        });
    }
}
