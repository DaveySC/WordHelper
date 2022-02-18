package lehska.newwordhelper.database;

import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfo {
    private static ArrayList<MyPair> wordsWithAnswers;
    private  String login = null;
    private  String password = null;
    private  String name = null;
    private  String email = null;
    private Integer id = null;
    private static UserInfo userInfo = null;
    DataBaseHandler dataBaseHandler;

    public String getLogin() {
        return this.login;
    }

    public  String getPassword() {
        return this.password;
    }

    public  String getName() {
        return this.name;
    }

    public  String getEmail() {
        return this.email;
    }

    public  void setLogin(String login) {
        this.login = login;
    }

    public  void setPassword(String password) {
        this.password = password;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  void setEmail(String email) {
        this.email = email;
    }

    public void setAll(String login, String password, String name, String email, Integer id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.login = login;
    }

    private UserInfo() {
        this.dataBaseHandler = DataBaseHandler.getInstance();
        this.wordsWithAnswers = new ArrayList<>();
        loadWords();
    }

    public static UserInfo getInstance() {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }

        return userInfo;
    }

    public Integer getId() {
        return id;
    }

    public void addWordWithAnswer(String word, String answer) {
        wordsWithAnswers.add(new MyPair(word, answer));
    }

    public void loadWords() {
        wordsWithAnswers.clear();
        String query = "select * from word where userID = " + this.id  + ";";
        ResultSet resultSet = this.dataBaseHandler.executeQuery(query);
        try{
            while(resultSet.next()) {
                String word = resultSet.getString("word");
                String answer = resultSet.getString("answer");
                addWordWithAnswer(word, answer);
            }
        } catch (SQLException exc) {
            System.out.println("error in loadWords method");
        }
    }


    public ArrayList<MyPair> getWordsWithAnswers() {
        return this.wordsWithAnswers;
    }

    public class MyPair<T> {
        private T first;
        private T second;

        public MyPair() {
            this.first = null;
            this.second = null;
        }

        public MyPair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return this.first;
        }

        public T getSecond() {
            return this.second;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(T second) {
            this.second = second;
        }
    }

}


