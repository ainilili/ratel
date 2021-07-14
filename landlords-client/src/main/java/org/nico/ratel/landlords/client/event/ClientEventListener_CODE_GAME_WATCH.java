package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.entity.User;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ClientEventListener_CODE_GAME_WATCH extends ClientEventListener {

    @Override
    public void call(Channel channel, String wrapData) {
        // 退出观战模式后不处理观战请求
        if (!User.INSTANCE.isWatching()) {
            return;
        }

        Map<String, Object> wrapMap = MapHelper.parser(wrapData);
        ClientEventCode rawCode = ClientEventCode.valueOf(wrapMap.get("code").toString());
        Object rawData = wrapMap.get("data");

        switch (rawCode) {
            case CODE_ROOM_JOIN_SUCCESS:
                printJoinPlayerInfo(rawData);
                break;

            // 游戏开始
            case CODE_GAME_STARTING:
                printGameStartInfo(rawData);
                break;

            // 抢地主
            case CODE_GAME_LANDLORD_ELECT:
                printRobLandlord(rawData);
                break;

            // 地主确认
            case CODE_GAME_LANDLORD_CONFIRM:
                printConfirmLandlord(rawData);
                break;

            // 出牌
            case CODE_SHOW_POKERS:
                printPlayPokers(rawData);
                break;

            // 不出（过）
            case CODE_GAME_POKER_PLAY_PASS:
                printPlayPass(rawData);
                break;

            // 玩家退出（此时可以退出观战，修改User.isWatching状态）
            case CODE_CLIENT_EXIT:
                printPlayerExit(rawData, channel);
                break;

            // 玩家被提出房间
            case CODE_CLIENT_KICK:
                printKickInfo(rawData);
                break;

            // 游戏结束（此时可以退出观战，修改User.isWatching状态）
            case CODE_GAME_OVER:
                printGameResult(rawData, channel);
                break;

            // 其他事件忽略
            default:
                break;
        }
    }

    private void printJoinPlayerInfo(Object rawData) {
        printNoticeWithTime("Player [" + rawData + "] joined the room");
    }

    private void printGameStartInfo(Object rawData) {
        Map<String, Object> map = MapHelper.parser(rawData.toString());

        printNoticeWithTime("Game starting");
        printNoticeWithTime("Player1 : " + map.get("player1"));
        printNoticeWithTime("Player2 : " + map.get("player2"));
        printNoticeWithTime("Player3 : " + map.get("player3"));
    }

    private void printRobLandlord(Object rawData) {
        printNoticeWithTime("Player [" + rawData + "] didn't choose to become the landlord.");
    }

    private void printConfirmLandlord(Object rawData) {
        Map<String, Object> map = MapHelper.parser(rawData.toString());

        printNoticeWithTime("Player [" + map.get("landlord") + "] has become the landlord and gotten three extra cards:");
        SimplePrinter.printPokers(Noson.convert(map.get("additionalPokers"), new NoType<List<Poker>>() {}));
    }

    private void printPlayPokers(Object rawData) {
        Map<String, Object> map = MapHelper.parser(rawData.toString());

        printNoticeWithTime("Player [" + map.get("clientNickname") + "] played:");
        SimplePrinter.printPokers(Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {}));
    }

    private void printPlayPass(Object rawData) {
        printNoticeWithTime("Player [" + rawData + "] : passed");
    }

    private void printPlayerExit(Object rawData, Channel channel) {
        printNoticeWithTime("Player [" + rawData + "] left the room");
        quitWatch(channel);
    }

    private void quitWatch(Channel channel) {
        printNoticeWithTime("This room will be closed!");
        printNoticeWithTime("Spectating ended. Bye.");
        SimplePrinter.printNotice("");
        SimplePrinter.printNotice("");

        // 修改玩家是否观战状态
        User.INSTANCE.setWatching(false);

        // 退出watch展示
        get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, "");
    }

    private void printGameResult(Object rawData, Channel channel) {
        Map<String, Object> map = MapHelper.parser(rawData.toString());

        printNoticeWithTime("Player [" + map.get("winnerNickname") + "](" + map.get("winnerType") + ") won the game.");
    }

    private void printKickInfo(Object rawData) {
        printNoticeWithTime("Player [" + rawData + "] has been kicked out for being idle.");
    }


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void printNoticeWithTime(String notice) {
        String msg = FORMATTER.format(LocalDateTime.now()) + "  " + notice;

        SimplePrinter.printNotice(msg);
    }
}
