package priv.zxw.ratel.landlords.client.javafx.ui.view;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void info(String headerText, String contentText) {
        createAndShowAlert(Alert.AlertType.INFORMATION, headerText, contentText);
    }

    private static void createAndShowAlert(Alert.AlertType alertType,
                                           String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Alert.AlertType.INFORMATION.equals(alertType) ? "信息" :
                        Alert.AlertType.WARNING.equals(alertType) ? "警告" : "错误");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static void warn(String headerText, String contentText) {
        createAndShowAlert(Alert.AlertType.WARNING, headerText, contentText);
    }

    public static void error(String headerText, String contentText) {
        createAndShowAlert(Alert.AlertType.ERROR, headerText, contentText);
    }
}
