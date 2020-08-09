package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IRoomEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.io.IOException;
import java.util.List;

public class RoomController extends UIObject implements RoomMethod {
    public static final String METHOD_NAME = "room";

    private static final String RESOURCE_NAME = "view/room.fxml";

    private IRoomEvent roomEvent;
    private RoomEventRegister roomEventDefine;

    public RoomController(IRoomEvent roomEvent) throws IOException {
        super();

        root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        setScene(new Scene(root));

        this.roomEvent = roomEvent;

        registerEvent();
    }

    @Override
    public void startGame(List<Poker> pokers) {
        // 1，组件状态改变（遮蔽罩隐藏，游戏面板可用状态改变）
        // 2，组件内容填充
        // 3，元素隐藏
        // 4，牌初始化（己方和对方）
        $("waitingPane", Pane.class).setVisible(false);
        $("playingPane", Pane.class).setDisable(false);

        Button robButton = $("robButton", Button.class);
        robButton.setText("抢地主");
        robButton.setVisible(false);

        Button notRobButton = $("notRobButton", Button.class);
        notRobButton.setText("不抢");
        notRobButton.setVisible(false);

        $("prevPlayerPane", Pane.class).lookup(".tips").setVisible(false);
        $("nextPlayerPane", Pane.class).lookup(".tips").setVisible(false);
        $("quitButton", Button.class).setText("退出");

        initPokers(pokers);
    }

    private static final int PER_PLAYER_DEFAULT_POKER_COUNT = 17;
    private static final int SURPLUS_POKER_COUNT = 3;

    private void initPokers(List<Poker> pokers) {
        // 己方牌pane
        Pane pokersPane = $("pokersPane", Pane.class);

        // 可能之前有牌，先清理再填充
        pokersPane.getChildren().clear();
        for (int i = 0, size = pokers.size(); i < size; i++) {
            pokersPane.getChildren().add(new PokerPane(i, pokers.get(i)).getPane());
        }

        // 上下游牌pane
        Pane prevPokersPane = $("prevPokersPane", Pane.class);
        Pane nextPokersPane = $("nextPokersPane", Pane.class);

        prevPokersPane.getChildren().clear();
        nextPokersPane.getChildren().clear();
        for (int j = 0; j < PER_PLAYER_DEFAULT_POKER_COUNT; j++) {
            prevPokersPane.getChildren().add(new RearPokerPane(j).getPane());
            nextPokersPane.getChildren().add(new RearPokerPane(j).getPane());
        }

        // 底牌
        Pane surplusPokersPane = $("surplusPokersPane", Pane.class);

        surplusPokersPane.getChildren().clear();
        for (int n = 0; n < SURPLUS_POKER_COUNT; n++) {
            surplusPokersPane.getChildren().add(new SurplusPokerPane(n).getPane());
        }
    }

    @Override
    public void showRobButtons() {
        $("robButton", Button.class).setVisible(true);
        $("notRobButton", Button.class).setVisible(true);
    }

    @Override
    public void hideRobButtons() {
        $("robButton", Button.class).setVisible(false);
        $("notRobButton", Button.class).setVisible(false);
    }

    @Override
    public void showSurplusPokers(List<Poker> surplusPokers) {
        Pane surplusPokersPane = $("surplusPokersPane", Pane.class);

        surplusPokersPane.getChildren().clear();
        for (int n = 0, size = surplusPokers.size(); n < size; n++) {
            Pane surplusPokerPane = new PokerPane(n, surplusPokers.get(n)).getPane();
            surplusPokerPane.setLayoutX(45 + n * (SurplusPokerPane.MARGIN_LEFT + 110));
            surplusPokerPane.setLayoutY(0);

            surplusPokersPane.getChildren().add(surplusPokerPane);
        }
    }

    @Override
    public void setLandLord(String landlordName) {
        // 1，为地主加底牌（重新洗牌）
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");
        if (ClientType.LANDLORD.equals(currentRoomInfo.getPrevPlayerRole())) {
            Pane prevPokersPane = $("prevPokersPane", Pane.class);

            prevPokersPane.getChildren().clear();
            for (int j = 0; j < PER_PLAYER_DEFAULT_POKER_COUNT + SURPLUS_POKER_COUNT; j++) {
                prevPokersPane.getChildren().add(new RearPokerPane(j).getPane());
            }
        } else if (ClientType.LANDLORD.equals(currentRoomInfo.getNextPlayerRole())) {
            Pane nextPokersPane = $("nextPokersPane", Pane.class);

            nextPokersPane.getChildren().clear();
            for (int j = 0; j < PER_PLAYER_DEFAULT_POKER_COUNT + SURPLUS_POKER_COUNT; j++) {
                nextPokersPane.getChildren().add(new RearPokerPane(j).getPane());
            }
        } else {
            Pane pokersPane = $("pokersPane", Pane.class);
            User user = BeanUtil.getBean("user");
            List<Poker> pokerList = user.getPokers();

            pokersPane.getChildren().clear();
            for (int i = 0, size = pokerList.size(); i < size; i++) {
                pokersPane.getChildren().add(new PokerPane(i, pokerList.get(i)).getPane());
            }
        }

        // 2，显示每个人的角色（地主|农民）和姓名
        $("prevPlayerRole", Label.class).setText(ClientType.LANDLORD.equals(currentRoomInfo.getPrevPlayerRole()) ? "地主" : "农民");
        $("nextPlayerRole", Label.class).setText(ClientType.LANDLORD.equals(currentRoomInfo.getNextPlayerRole()) ? "地主" : "农民");
        $("playerRole", Label.class).setText(ClientType.LANDLORD.equals(currentRoomInfo.getPlayer().getRole()) ? "地主" : "农民");

        $("prevPlayerNickname", Label.class).setText(currentRoomInfo.getPrevPlayerName());
        $("nextPlayerNickname", Label.class).setText(currentRoomInfo.getNextPlayerName());
        $("playerNickname", Label.class).setText(currentRoomInfo.getPlayer().getNickname());
    }

    @Override
    public void showRecentPokers(List<Poker> recentPokers) {
        Pane showPokersPane = $("showPokersPane", Pane.class);

        showPokersPane.getChildren().clear();
        for (int i = 0, size = recentPokers.size(); i < size; i++) {
            showPokersPane.getChildren().add(new PokerPane(i, recentPokers.get(i)).getPane());
        }
    }

    @Override
    public void showPlayerMessage(String playerName, String message) {
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");

        Label tips;
        if (playerName.equals(currentRoomInfo.getPrevPlayerName())) {
            tips = ((Label) $("prevPlayerPane", Pane.class).lookup(".tips"));
        } else if (playerName.equals(currentRoomInfo.getNextPlayerName())) {
            tips = ((Label) $("nextPlayerPane", Pane.class).lookup(".tips"));
        } else {
            tips = ((Label) $("playerPane", Pane.class).lookup(".tips"));
        }

        tips.setText(message);
        tips.setVisible(true);
    }

    @Override
    public void showTimer(String playerName, int secondTime) {
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");

        Label timer;
        if (playerName.equals(currentRoomInfo.getPrevPlayerName())) {
            timer = ((Label) $("prevPlayerPane", Pane.class).lookup(".timer"));
        } else if (playerName.equals(currentRoomInfo.getNextPlayerName())) {
            timer = ((Label) $("nextPlayerPane", Pane.class).lookup(".timer"));
        } else {
            timer = ((Label) $("playerPane", Pane.class).lookup(".timer"));
        }

        timer.setText(String.valueOf(secondTime));
        timer.setVisible(true);
    }

    @Override
    public boolean isShow() {
        return super.isShowing();
    }

    @Override
    public void joinRoom() {
        super.show();
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
    public void registerEvent() {
        roomEventDefine = new RoomEventRegister(this, roomEvent);
    }

    @Override
    public String getName() {
        return METHOD_NAME;
    }
}
