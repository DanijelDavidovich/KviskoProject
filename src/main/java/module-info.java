module org.example.kviskoproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;


    opens org.example.kviskoproject to javafx.fxml;
    exports org.example.kviskoproject;
}