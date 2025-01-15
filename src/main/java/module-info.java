module org.example.kviskoproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens org.example.kviskoproject to javafx.fxml;
    exports org.example.kviskoproject;
}