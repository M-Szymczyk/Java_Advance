package pl.edu.pwr.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import library.Lib;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Application extends javafx.application.Application {
    ///data of TableView
    private final ObservableList<FileString> data =
            FXCollections.observableArrayList();
    ///Path to chosen file
    private String filePath;

    @Override
    public void start(Stage stage){

        DirectoryChooser fileChooser = new DirectoryChooser();
        /// Elements of view
        Pane pane = new Pane();
        pane.setPrefWidth(460);
        pane.setPrefHeight(400);

        Button button = new Button();
        button.setText("Wybierz katalog");
        button.setLayoutX(20);
        button.setLayoutY(14);
        button.setPrefWidth(120);

        Button refresh = new Button();
        refresh.setText("Odśwież");
        refresh.setLayoutX(140);
        refresh.setLayoutY(14);
        refresh.setPrefWidth(100);
        refresh.setVisible(false);

        TableView<FileString> table = new TableView<>();
        table.setLayoutX(20);
        table.setLayoutY(50);
        table.setPrefWidth(420);
        table.setPrefHeight(300);

        TableColumn<FileString, String> fileNameColumn = new TableColumn<>("Nazwa pliku");
        fileNameColumn.setPrefWidth(330);
        fileNameColumn.setResizable(false);
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        TableColumn<FileString, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setPrefWidth(88);
        statusColumn.setResizable(false);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.setItems(data);
        table.getColumns().addAll(fileNameColumn, statusColumn);
        table.setVisible(false);

        //button handlers
        button.setOnAction(event -> {
            data.clear();
            File selectedFile = fileChooser.showDialog(stage);
            filePath = selectedFile.toPath().toString();
            Lib lib = new Lib(filePath);
            lib.getFiles().forEach(filesSS ->
                data.add(new FileString(Paths.get(filesSS.fileName).getFileName().toString(),
                        filesSS.status.toString()))
            );
            table.setVisible(true);
            button.setText("Zmień katalog");
            refresh.setVisible(true);
        });

        refresh.setOnAction(event -> {
            Lib lib = new Lib(filePath);
            data.clear();
            lib.getFiles().forEach(filesSS ->
                    data.add(new FileString(Paths.get(filesSS.fileName).getFileName().toString(),
                            filesSS.status.toString()))
            );
        });

        pane.getChildren().addAll(button, table,refresh);

        stage.setScene(new Scene(pane));
        stage.setTitle("Application");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}