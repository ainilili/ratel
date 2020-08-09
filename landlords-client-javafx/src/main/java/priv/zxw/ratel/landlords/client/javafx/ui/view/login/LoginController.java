package priv.zxw.ratel.landlords.client.javafx.ui.view.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILoginEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.io.IOException;

public class LoginController extends UIObject implements Method {
    public static final String METHOD_NAME = "login";

    private static final String RESOURCE_NAME = "view/login.fxml";

    private ILoginEvent loginEvent;
    private LoginEventRegister loginEventRegister;

    public LoginController(ILoginEvent loginEvent) throws IOException {
        super();

        root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        setScene(new Scene(root));

        this.loginEvent = loginEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        this.loginEventRegister = new LoginEventRegister(this, loginEvent);
    }

    @Override
    public String getName() {
        return METHOD_NAME;
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void doClose() {
        super.close();
    }
}
