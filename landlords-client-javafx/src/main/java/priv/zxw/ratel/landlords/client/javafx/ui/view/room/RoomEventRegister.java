package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import javafx.scene.control.Button;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IRoomEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.EventRegister;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.awt.*;

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
}
