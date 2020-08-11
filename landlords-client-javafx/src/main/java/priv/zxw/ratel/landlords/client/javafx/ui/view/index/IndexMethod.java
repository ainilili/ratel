package priv.zxw.ratel.landlords.client.javafx.ui.view.index;

import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.List;

public interface IndexMethod extends Method {
    void generateRemoteServerAddressOptions(List<String> remoteServerAddressList);

    void setFetchRemoteServerAddressErrorTips();

    void setConnectServerErrorTips();
}
