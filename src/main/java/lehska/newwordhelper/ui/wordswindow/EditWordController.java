package lehska.newwordhelper.ui.wordswindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lehska.newwordhelper.database.DataBaseHandler;
import lehska.newwordhelper.database.UserInfo;

public class EditWordController {
    String prevWord = "";


    WordsController wordsController;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    public void setWordsController(WordsController wordsController) {
        this.wordsController = wordsController;
    }


    public void setText(String text1, String text2) {
        textField1.setText(text1);
        textField2.setText(text2);
        prevWord = text1;
    }


    @FXML
    void actionButton(ActionEvent event) {
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        String query = "update word set word = '" + textField1.getText() + "', answer = '"+  textField2.getText() +"' where word = '"+ prevWord + "';";
        if (!dataBaseHandler.executeAction(query)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("что-то пошло не так");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.loadWords();
        this.wordsController.loadData();
        Stage stage = (Stage) textField1.getScene().getWindow();
        stage.close();
    }


}
