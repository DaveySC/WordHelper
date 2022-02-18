package lehska.newwordhelper.ui.loginwindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lehska.newwordhelper.database.DataBaseHandler;
import lehska.newwordhelper.database.UserInfo;
import lehska.newwordhelper.helper.Switcher;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private DataBaseHandler dataBaseHandler;
    private UserInfo userInfo;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    void signIn(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        password = DigestUtils.md5Hex(password);
        String query = "SELECT * FROM users WHERE login = '" + login + "';";
        ResultSet resultSet = this.dataBaseHandler.executeQuery(query);
        try {
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String passFromDataBase = resultSet.getString("password");
                if (passFromDataBase.equals(password)) {
                    this.userInfo.setAll(login, password, "NAME", "EMAIL", id);
                    String path = "/lehska/newwordhelper/ui/testwindow/test-view.fxml";
                    Switcher.switchWindow((Stage) loginField.getScene().getWindow(),path, this);
                    return;
                }
            }
        } catch (SQLException exception) {
            System.out.println("error in signIn method");
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Wrong password or login");
        alert.setHeaderText(null);
        alert.showAndWait();


    }

    @FXML
    void signUp(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        password = DigestUtils.md5Hex(password);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);

        String query = "SELECT * FROM users WHERE login = '" + login + "';";
        ResultSet resultSet = this.dataBaseHandler.executeQuery(query);
        try {
            if (resultSet.next()) {
                 alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("User with this login already exists");
                return;
            } else {
                query = "INSERT INTO users (login, password) VALUES (\'" + login +"\', " + "\'" + password + "\');";
                if (this.dataBaseHandler.executeAction(query)) {
                     alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Complete!");
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Something was wrong");
                }
            }
        } catch (SQLException exception) {
            System.out.println("error in signIn method");
        }
        alert.showAndWait();

    }


    @FXML
    void initialize() {
        this.dataBaseHandler = DataBaseHandler.getInstance();
        this.userInfo = UserInfo.getInstance();
    }




}
