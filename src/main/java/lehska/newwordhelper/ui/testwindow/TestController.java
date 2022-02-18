package lehska.newwordhelper.ui.testwindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lehska.newwordhelper.database.UserInfo;
import lehska.newwordhelper.helper.Switcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

enum TestMode{
    END_OF_TEST,
    TEST,
    PREPARE_TO_TEST
}

public class TestController {
    UserInfo userInfo;
    private ArrayList<UserInfo.MyPair> wordsWithAnswers;
    private int posInArray = 0;
    private TestMode testMode = TestMode.PREPARE_TO_TEST;


    @FXML
    private TableColumn<Word, String> answerColumn;

    @FXML
    private TableColumn<Word, String> resultColumn;

    @FXML
    private TableColumn<Word, String> rightAnswerColumn;

    @FXML
    private TableView<Word> tableView;

    @FXML
    private TableColumn<Word, String> wordColumn;

    @FXML
    private TextField centerTextField;

    @FXML
    private Label centerLabel;

    @FXML
    private Label labelInMenuItem1;

    @FXML
    private Label labelInMenuItem2;

    @FXML
    private MenuButton comboVomboID;

    @FXML
    private Pane rootPane;

    private Button edit;
    private Button logOut;
    private final String pathToRightImage = "";
    private final String pathToWrongImage = "";


    @FXML
    void openWordsView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/wordswindow/wods-view.fxml");
    }

    @FXML
    void openCalendarView(ActionEvent event) {
        loadNewScene("/lehska/newwordhelper/ui/calendarwindow/calendar-view.fxml");
    }

    private void loadNewScene(String path) {
        Switcher.switchScene((Stage) rootPane.getScene().getWindow(), path, this);

    }

    private String answer = "";
    private String word = "";
    @FXML
    void answerAction(ActionEvent event) {
        String userAnswer = centerTextField.getText().toLowerCase();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (userAnswer.isEmpty()) {
            alert.setContentText("Заполните поле, пожалуйста");
            alert.showAndWait();
            return;
        }

        if (testMode == TestMode.PREPARE_TO_TEST) {
            if (this.wordsWithAnswers.size() == 0) {
                alert.setContentText("Ваша словарь пуст");
                alert.showAndWait();
                return;
            }
            word = (String) wordsWithAnswers.get(posInArray).getFirst();
            answer = (String) wordsWithAnswers.get(posInArray).getSecond();
            answer = answer.toLowerCase();
            posInArray++;
            centerLabel.setText(word);
            if (posInArray == wordsWithAnswers.size()) {
                testMode = TestMode.END_OF_TEST;
            }
            return;
        }
        if (!word.isEmpty()) {
            if (answer.equals(userAnswer)) {
                tableView.getItems().add(new Word(word, userAnswer, answer, "правильно"));

            } else {
                tableView.getItems().add(new Word(word, userAnswer, answer, "не правильно"));

            }
        }

        if (word.isEmpty()) {
            tableView.getItems().clear();
        }

        if (testMode == TestMode.END_OF_TEST) {
            centerLabel.setText("Wanna try again?");
            centerTextField.clear();
            testMode = TestMode.TEST;
            posInArray = 0;
            Collections.shuffle(wordsWithAnswers);
            word = "";
            answer = "";
            return;
        }
        word = (String) wordsWithAnswers.get(posInArray).getFirst();
        answer = (String) wordsWithAnswers.get(posInArray).getSecond();
        answer = answer.toLowerCase();
        posInArray++;
        centerLabel.setText(word);
        if (posInArray == wordsWithAnswers.size()) {
            testMode = TestMode.END_OF_TEST;
        }

    }


    @FXML
    void initialize() {
        this.userInfo = UserInfo.getInstance();
        this.userInfo.loadWords();
        this.comboVomboID.setText(userInfo.getName());
        this.wordsWithAnswers = userInfo.getWordsWithAnswers();
        Collections.shuffle(this.wordsWithAnswers);
        initColumns();
    }




    private void initColumns() {
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        rightAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("rightAnswer"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    public static class Word {
        private final SimpleStringProperty word;
        private final SimpleStringProperty answer;
        private final SimpleStringProperty rightAnswer;
        private final SimpleStringProperty result;


        public Word(String word, String translate, String rightAnswer, String result) {
            this.word = new SimpleStringProperty(word);
            this.answer = new SimpleStringProperty(translate);
            this.rightAnswer = new SimpleStringProperty(rightAnswer);
            this.result = new SimpleStringProperty(result);
        }

        public String getWord() {
            return word.get();
        }

        public SimpleStringProperty wordProperty() {
            return word;
        }

        public String getAnswer() {
            return answer.get();
        }

        public SimpleStringProperty answerProperty() {
            return answer;
        }

        public void setWord(String word) {
            this.word.set(word);
        }

        public void setAnswer(String translate) {
            this.answer.set(translate);
        }

        public String getRightAnswer() {
            return rightAnswer.get();
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer.set(rightAnswer);
        }

        public SimpleStringProperty rightAnswerProperty() {
            return rightAnswer;
        }

        public String getResult() {
            return result.get();
        }

        public SimpleStringProperty resultProperty() {
            return result;
        }

        public void setResult(String result) {
            this.result.set(result);
        }
    }

}
