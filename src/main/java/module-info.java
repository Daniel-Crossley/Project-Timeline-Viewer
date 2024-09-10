module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;


    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.Timeline;
    opens com.example.project.Timeline to javafx.fxml;
    exports com.example.project.TestSuite;
    opens com.example.project.TestSuite to javafx.fxml;
}