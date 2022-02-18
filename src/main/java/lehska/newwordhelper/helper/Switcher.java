package lehska.newwordhelper.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lehska.newwordhelper.ui.wordswindow.EditWordController;

import java.io.IOException;

public class Switcher {

    public static void switchScene(Stage stage,String path, Object obj) {
        try {
            FXMLLoader loader = new FXMLLoader(obj.getClass().getResource(path));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchWindow(Stage primaryStage,String path, Object obj) {
        try {
            FXMLLoader loader = new FXMLLoader(obj.getClass().getResource(path));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            primaryStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EditWordController openNewWindow(String path, Object obj) {
        try {
            FXMLLoader loader = new FXMLLoader(obj.getClass().getResource(path));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
