package lehska.newwordhelper.ui.wordswindow;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lehska.newwordhelper.database.DataBaseHandler;
import lehska.newwordhelper.database.UserInfo;
import lehska.newwordhelper.helper.Switcher;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordsController {
    DataBaseHandler dataBaseHandler;
    UserInfo userInfo;
    @FXML
    private Pane rootPane;

    @FXML
    private TableView<Word> tableView;

    @FXML
    private TableColumn<Word, String> translateColumn;

    @FXML
    private TableColumn<Word, String> wordColumn;

    @FXML
    private TextField textFieldInCenter1;

    @FXML
    private TextField textFieldInCenter2;


    @FXML
    void openTestView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/testwindow/test-view.fxml");
    }

    @FXML
    void openCalendarView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/calendarwindow/calendar-view.fxml");
    }

    private void loadNewScene(String path) {
        Switcher.switchScene((Stage) rootPane.getScene().getWindow(), path, this);

    }

    ObservableList<Word> list;
    public void loadData() {
        list.clear();
        DataBaseHandler databaseHandler = DataBaseHandler.getInstance();
        String query = "select * from word where userID = " + this.userInfo.getId() + ";";
        ResultSet resultSet = databaseHandler.executeQuery(query);
        try {
            while(resultSet.next()) {
                String word = resultSet.getString("word");
                String answer = resultSet.getString("answer");
                list.add(new Word(word, answer));
            }
        } catch (SQLException exp) {
            System.out.println("exception int checkData method");
        }
        tableView.getItems().setAll(list);
    }

    @FXML
    void addNewWord(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        String newWord = textFieldInCenter1.getText();
        String newAnswer = textFieldInCenter2.getText();

        if (newAnswer.isEmpty() || newWord.isEmpty()) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Заполните оба поля");
            alert.showAndWait();
            return;
        }

        String query = "insert into word(word, answer, userID) " +
                       "values('" + newWord + "', '" + newAnswer + "', '" + this.userInfo.getId() + "');";

        if (!this.dataBaseHandler.executeAction(query)) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("что-то пошло не так");
            alert.showAndWait();
            return;
        }
        this.userInfo.addWordWithAnswer(newWord, newAnswer);
        tableView.getItems().add(new Word(newWord, newAnswer));
        textFieldInCenter1.clear();
        textFieldInCenter2.clear();

    }


    @FXML
    void deleteWordTableView(ActionEvent event) {
        Word helper = this.tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Выберите слово");
        alert.setHeaderText(null);
        if (helper == null) {
            alert.showAndWait();
            return;
        }

        String query = "delete from word where word = '"+ helper.getWord() +"';";
        if (!this.dataBaseHandler.executeAction(query)) {
            alert.setContentText("что-то пошло не так");
            alert.showAndWait();
            return;
        }

        this.userInfo.loadWords();
        this.tableView.getItems().clear();
        loadData();
    }

    @FXML
    void editTableView(ActionEvent event) {
        Word helper = this.tableView.getSelectionModel().getSelectedItem();
        String path = "/lehska/newwordhelper/ui/wordswindow/edit-view.fxml";
        EditWordController editWordController = Switcher.openNewWindow(path, this);
        assert editWordController != null;
        editWordController.setWordsController(this);
        editWordController.setText(helper.getWord(), helper.getTranslate());
    }


    @FXML
    public void initialize() {
        this.dataBaseHandler  = DataBaseHandler.getInstance();
        this.userInfo = UserInfo.getInstance();
        this.userInfo.loadWords();
        this.list = FXCollections.observableArrayList();
        initColumns();
        loadData();
    }


    private void initColumns() {
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translateColumn.setCellValueFactory(new PropertyValueFactory<>("translate"));
    }


    public static class Word {
        private final SimpleStringProperty word;
        private final SimpleStringProperty translate;

        public Word(String word, String translate) {
            this.word = new SimpleStringProperty(word);
            this.translate = new SimpleStringProperty(translate);
        }

        public String getWord() {
            return word.get();
        }

        public SimpleStringProperty wordProperty() {
            return word;
        }

        public String getTranslate() {
            return translate.get();
        }

        public SimpleStringProperty translateProperty() {
            return translate;
        }


        public void setWord(String word) {
            this.word.set(word);
        }

        public void setTranslate(String translate) {
            this.translate.set(translate);
        }
    }

}
