package priv.zxw.ratel.landlords.client.javafx.listener;

import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;

import java.util.Objects;


public abstract class AbstractClientListener implements ClientListener {
    protected ClientEventCode code;

    protected UIService uiService;

    public AbstractClientListener(ClientEventCode code) {
        Objects.requireNonNull(code, "子事件监听器需要自定义专属的事件编码");
        this.code = code;
    }

    @Override
    public ClientEventCode getCode() {
        return code;
    }

    @Override
    public void setUIService(UIService uiService) {
        this.uiService = uiService;
    }
}
