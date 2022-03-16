package pl.edu.pwr.reviewingfilesapplication.welcomeScene;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pl.edu.pwr.reviewingfilesapplication.person.FileLoader;
import pl.edu.pwr.reviewingfilesapplication.person.PersonData;

import java.io.File;
import java.io.IOException;
import java.util.WeakHashMap;

public class WelcomeScene extends Application {
    WeakHashMap<String, PersonData> weakHashMap = new WeakHashMap<>();
    PersonData chosenPerson;
    TextField nameTextLabel, surnameTextLabel, ageTextLabel, uniTextLabel;
    Label nameTextArea, surnameTextArea, ageTextArea, uniTextArea;
    ImageView image;
    Button nextBtn = new Button("Następny"),prefBtn = new Button("Poprzedni");
    FileLoader fileLoader;

    @Override
    public void start(Stage stage) {
        /// choose directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab2\\release"));
        File chosenDir = directoryChooser.showDialog(stage);
        fileLoader = new FileLoader(new File(chosenDir.toString() + "\\248881").toPath());
        /// add files from directory to

//        ArrayList<Path> listOfFiles = new ArrayList<>(1);
//        ObservableList<FileString> data =
//                FXCollections.observableArrayList();
//       //todo nie powinny byc to soft reference? //todo dodanie checkbox żeby można było wykonywać jakieś operacje na rekordach
//        for (final File fileEntry : Objects.requireNonNull(chosenDir.listFiles())) {
//            if (fileEntry.isDirectory()) {
//                listOfFiles.add(fileEntry.toPath());
//                data.add(new FileString(fileEntry.getName()));
//            }
//        }
        /// elements of view
        Pane pane = new Pane();
        pane.setPrefWidth(850);
        pane.setPrefHeight(400);



        TreeView<String> treeView = new TreeView<>();

        treeView.setRoot(getNodesForDirectory(chosenDir));
        treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            System.out.println("Selected Text : " + selectedItem.getValue());
        });


        initTextFields();
        setTextLabels(chosenPerson);


        pane.getChildren().addAll(treeView, image, nameTextLabel, surnameTextLabel, ageTextLabel, uniTextLabel,
                nameTextArea, surnameTextArea, ageTextArea, uniTextArea, nextBtn,prefBtn);


        stage.setScene(new Scene(pane));
        stage.setTitle("Application");
        stage.setResizable(false);
        stage.show();
    }

    private void initTextFields() {

        nextBtn.setLayoutX(330);
        nextBtn.setLayoutY(350);
        prefBtn.setLayoutX(260);
        prefBtn.setLayoutY(350);

        image = new ImageView();
        image.setX(650);
        image.setY(10);
        image.setFitWidth(150);
        image.setPreserveRatio(true);

        int labelX = 260,text = 330;
        nameTextLabel = new TextField();
        surnameTextLabel = new TextField();
        ageTextLabel = new TextField();
        uniTextLabel = new TextField();
        nameTextLabel.setEditable(false);
        surnameTextLabel.setEditable(false);
        ageTextLabel.setEditable(false);
        uniTextLabel.setEditable(false);
        nameTextLabel.setLayoutX(text);
        nameTextLabel.setLayoutY(20);
        surnameTextLabel.setLayoutX(text);
        surnameTextLabel.setLayoutY(50);
        ageTextLabel.setLayoutX(text);
        ageTextLabel.setLayoutY(80);
        uniTextLabel.setLayoutX(text);
        uniTextLabel.setLayoutY(110);

        nameTextArea = new Label();
        surnameTextArea = new Label();
        ageTextArea = new Label();
        uniTextArea = new Label();
        nameTextArea.setLayoutX(labelX);
        surnameTextArea.setLayoutX(labelX);
        ageTextArea.setLayoutX(labelX);
        uniTextArea.setLayoutX(labelX);
        nameTextArea.setLayoutY(20);
        surnameTextArea.setLayoutY(50);
        ageTextArea.setLayoutY( 80);
        uniTextArea.setLayoutY(110);
        nameTextArea.setText("Imię:");
        surnameTextArea.setText("Nazwisko:");
        ageTextArea.setText("Wiek:");
        uniTextArea.setText("Uczelnia:");
    }

    public static void main(String[] args) {
        launch();
    }

    public CheckBoxTreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>(directory.getName());

        for (File f : directory.listFiles()) {
            //System.out.println("Loading " + f.getName());
            if (f.isDirectory()) { //Then we call the function recursively
                if (chosenPerson == null) {
                    try {
                        chosenPerson = fileLoader.readFromRecordFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                root.getChildren().add(
                        new CheckBoxTreeItem<>(f.getName()));
            }

        }
        //else {
        //root.getChildren().add(new TreeItem<String>(f.getName()));
        //}
        return root;
    }
    void setTextLabels(PersonData personData){
        nameTextLabel.setText(personData.name);
        surnameTextLabel.setText(personData.surname);
        ageTextLabel.setText(String.valueOf(personData.age));
        uniTextLabel.setText(personData.university);
        this.image.setImage(personData.myPicture);
    }
}
