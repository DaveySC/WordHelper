package lehska.newwordhelper.ui.calendarwindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lehska.newwordhelper.helper.Switcher;

public class CalendarController {
    @FXML
    private BorderPane rootPane;

    @FXML
    void openWordsView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/wordswindow/wods-view.fxml");
    }

    @FXML
    void openTestView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/testwindow/test-view.fxml");

    }

    private void loadNewScene(String path) {
        Switcher.switchScene((Stage) rootPane.getScene().getWindow(), path, this);

    }
}
