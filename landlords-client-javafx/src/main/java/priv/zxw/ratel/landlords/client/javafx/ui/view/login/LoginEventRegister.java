package priv.zxw.ratel.landlords.client.javafx.ui.view.login;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILoginEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.EventRegister;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

public class LoginEventRegister implements EventRegister {

    private UIObject uiObject;
    private ILoginEvent loginEvent;

    public LoginEventRegister(UIObject uiObject, ILoginEvent loginEvent) {
        this.uiObject = uiObject;
        this.loginEvent = loginEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        submitNickname();
    }

    private void submitNickname() {
        TextField field = uiObject.$("nicknameInput", TextField.class);

        uiObject.$("submitButton", Button.class).setOnAction(e -> {
            String nickname = field.getText().trim();
            loginEvent.setNickname(nickname);
        });
    }

    private void verifyNickname() {
        uiObject.$("input", TextField.class).setOnAction(e -> {});
    }
}
