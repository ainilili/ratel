package priv.zxw.ratel.landlords.client.javafx.listener;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientType;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientGameOverListener extends AbstractClientListener {

    public ClientGameOverListener() {
        super(ClientEventCode.CODE_GAME_OVER);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String winnerName = jsonObject.getString("winnerNickname");
        ClientType clientType = jsonObject.getObject("winnerType", ClientType.class);

        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        Platform.runLater(() -> roomMethod.gameOver(winnerName, clientType));
    }
}
