package priv.zxw.ratel.landlords.client.javafx.ui.view;


import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public abstract class UIObject extends Stage {

    protected Parent root;

    private ExecutorService animationExecutorService = Executors.newFixedThreadPool(2);

    public UIObject() {
        setTitle("ratel javafx客户端");

        // 退出程序
        setOnCloseRequest(e -> closeApplication());
    }

    private void closeApplication() {
        // 关闭视图
        close();
        Platform.exit();

        // 关闭用于动画执行的线程池
        animationExecutorService.shutdownNow();

        // 关闭netty
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        if (nettyClient != null) {
            nettyClient.destroy();
        }
    }

    public <T> T $(String id, Class<T> clazz) {
        return (T) root.lookup("#" + id);
    }

    public void delayShow(Node node, int secondDelay) {
        animationExecutorService.execute(new DelayRunnable(node, n -> n.setVisible(true), secondDelay));
    }

    public void delayHidden(Node node, int secondDelay) {
        animationExecutorService.execute(new DelayRunnable(node, n -> {
            // 动画过程中的元素是无效的，不能被操作的
            n.setDisable(true);
            useTransition(n);
            n.setDisable(false);
            n.setVisible(false);
        }, secondDelay));
    }

    private class DelayRunnable implements Runnable {
        private Object monitor = new Object();
        private Node node;
        private Consumer<Node> operate;
        private int delayTimes;

        DelayRunnable(Node node, Consumer<Node> operate, int delayTimes) {
            this.node = node;
            this.operate = operate;
            this.delayTimes = delayTimes;
        }

        @Override
        public void run() {
            try {
                synchronized (monitor) {
                    monitor.wait(delayTimes * 1000L);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }

            operate.accept(node);
        }
    }

    private void useTransition(Node node) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(1);
        fade.setToValue(0.1);
        fade.setCycleCount(1000);
        fade.setAutoReverse(true);
        fade.setNode(node);
        fade.play();
    }

    public abstract void registerEvent();
}