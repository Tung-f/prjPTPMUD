package Utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LogOut {
    public static void logout(Node currentNode) {
        try {
            FXMLLoader loader = new FXMLLoader(LogOut.class.getResource("/com/mycompany/quanlyxuongsuaxe/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) currentNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}