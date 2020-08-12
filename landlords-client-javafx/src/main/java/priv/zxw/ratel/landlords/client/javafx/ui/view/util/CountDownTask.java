package priv.zxw.ratel.landlords.client.javafx.ui.view.util;


import javafx.scene.Node;

import java.util.Objects;
import java.util.function.Consumer;

public class CountDownTask {
    public static final int DEFAULT_TIME_OUT = 30;

    private Consumer<Node> finallyExecuteConsumer;
    private Consumer<Integer> preSecondExecuteConsumer;
    private Node targetElement;
    private int duration;

    public CountDownTask(Node targetElement,
                         Consumer<Node> finallyExecuteConsumer, Consumer<Integer> preSecondExecuteConsumer) {
        this.finallyExecuteConsumer = finallyExecuteConsumer;
        this.preSecondExecuteConsumer = preSecondExecuteConsumer;
        this.targetElement = targetElement;
    }

    public CountDownTask(Node targetElement, int duration,
                         Consumer<Node> finallyExecuteConsumer, Consumer<Integer> preSecondExecuteConsumer) {
        Objects.requireNonNull(finallyExecuteConsumer);

        this.finallyExecuteConsumer = finallyExecuteConsumer;
        this.preSecondExecuteConsumer = preSecondExecuteConsumer;
        this.duration = duration < 0 ? DEFAULT_TIME_OUT : duration;
        this.targetElement = targetElement;
    }

    public CountDownFuture start() {
        targetElement.setVisible(true);

        CountDownFuture future = new CountDownFuture();
        future.start();

        return future;
    }

    public class CountDownFuture extends Thread {

        private volatile boolean done = false;

        private CountDownFuture() {
            setDaemon(true);
        }

        @Override
        public void run() {
            long startTimeMillis = System.currentTimeMillis();

            long interval;
            try {
                while (true) {
                    long currentTimeMillis = System.currentTimeMillis();
                    interval = (currentTimeMillis - startTimeMillis) / 1000;

                    if (interval > duration) {
                        break;
                    }

                    preSecondExecuteConsumer.accept((int) (duration - interval));

                    sleep(1000L);
                }

                finallyExecuteConsumer.accept(targetElement);
            } catch (InterruptedException cancelFlag) {
                // exit
                return;
            } finally {
                done = true;
                targetElement.setVisible(false);
            }
        }

        public void cancel() {
            if (done) {
                return;
            }

            done = true;
            interrupt();
        }

        public boolean isDone() {
            return done;
        }
    }
}
