package priv.zxw.ratel.landlords.client.javafx.ui.view.index;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.io.IOException;

public class IndexController extends UIObject implements Method {
    public static final String METHOD_NAME = "index";

    private static final String RESOURCE_NAME = "view/index.fxml";

    public IndexController() throws IOException {
        super();

        root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        setScene(new Scene(root));

        registerEvent();
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

    @Override
    public void registerEvent() {}
}
