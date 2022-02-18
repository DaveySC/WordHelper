module lehska.newwordhelper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires org.apache.commons.codec;


    opens lehska.newwordhelper.ui.testwindow to javafx.fxml;
    exports lehska.newwordhelper.ui.testwindow;

    opens lehska.newwordhelper.ui.calendarwindow to javafx.fxml;
    exports lehska.newwordhelper.ui.calendarwindow;

    opens lehska.newwordhelper.ui.settingswindow to javafx.fxml;
    exports lehska.newwordhelper.ui.settingswindow;

    opens lehska.newwordhelper.ui.wordswindow to javafx.fxml;
    exports lehska.newwordhelper.ui.wordswindow;

    opens lehska.newwordhelper.ui.loginwindow to javafx.fxml;
    exports lehska.newwordhelper.ui.loginwindow;


}