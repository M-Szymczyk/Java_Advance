package pl.pwr.edu.lab.kubik.financesmanager.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.DepositService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.EventService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.InstalmentService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.PersonService;
import pl.pwr.edu.lab.kubik.financesmanager.gui.tables.DepositTable;
import pl.pwr.edu.lab.kubik.financesmanager.gui.tables.EventTable;
import pl.pwr.edu.lab.kubik.financesmanager.gui.tables.InstallmentTable;
import pl.pwr.edu.lab.kubik.financesmanager.gui.tables.PersonTable;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("gui-view.fxml")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GuiController implements Initializable {
    @FXML
    public TableView<PersonTable> peronTable;
    @FXML
    public TableView<InstallmentTable> instalmentTable;
    @FXML
    public TableView<DepositTable> depositTable;
    @FXML
    public TableView<EventTable> eventTable;
    private final ObservableList<PersonTable> personTables = FXCollections.observableArrayList();
    private final ObservableList<InstallmentTable> installmentTables = FXCollections.observableArrayList();
    private final ObservableList<DepositTable> depositTables = FXCollections.observableArrayList();
    private final ObservableList<EventTable> eventTables = FXCollections.observableArrayList();
    private final ObservableList<String> logList = FXCollections.observableArrayList();
    private final EventService eventService;
    private final PersonService personService;
    private final DepositService depositService;
    private final InstalmentService instalmentService;
    public ListView<String> logsListView;
    public Label applicationDateLabel;
    private Date applicationDate;
    private Calendar cal;

    public GuiController(EventService eventService, PersonService personService, DepositService depositService,
                         InstalmentService instalmentService) {
        this.eventService = eventService;
        this.personService = personService;
        this.depositService = depositService;
        this.instalmentService = instalmentService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cal = Calendar.getInstance();
        initPersonTable();
        initEventTable();
        initInstallmentTable();
        initDepositTable();
        logsListView.setItems(logList);
        applicationDate = Date.valueOf(LocalDate.now());
        applicationDateLabel.setText(applicationDate.toString());
        remindAboutMoney();
        checkNoPay();
    }

    private void remindAboutMoney() {
        cal.setTime(Date.valueOf(applicationDate.toLocalDate().plusDays(7)));
        logList.add("Notification week before payment");
        instalmentService.getAllByPaymentTimeEquals( cal.getTimeInMillis())
                .forEach(repayment -> personService.getAll().forEach(person -> {
                    if (!depositService.existsByRepaymentAndAndPersonID(repayment, person))
                        logList.add("Log from: "
                                + applicationDate.toString()
                                + ": " + person.getName()
                                + " " + person.getSurname()
                                + " " + repayment.getEvent().getName()
                                + " " + repayment.getAmount()
                                + " payment time: " + getDate(repayment.getDate())
                                + "\n");
                }));
    }

    @FXML
    public void updateDate() {
        applicationDate = Date.valueOf(applicationDate.toLocalDate().plusDays(1));
        applicationDateLabel.setText(applicationDate.toString());
        remindAboutMoney();
        checkNoPay();
    }

    private void checkNoPay() {
        cal.setTime(applicationDate);
        logList.add("Checking who hasn't paid");
        instalmentService.getByDateBefore( cal.getTimeInMillis())
                .forEach(repayment -> personService.getAll().forEach(person -> {
                    if (!depositService.existsByRepaymentAndAndPersonID(repayment, person))
                       logList.add("Log from: "
                            + applicationDate.toString()
                            + ": " + person.getName()
                            + " " + person.getSurname()
                            + " " + repayment.getEvent().getName()
                            + " " + repayment.getNoInstalment()
                            + " Payment time: " +getDate(repayment.getDate())
                            + "\n");
                }));
    }

    private File runFileChooser() {
        FileChooser chooser = new FileChooser();
        File chosen = new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab7\\release");
        if (chosen.exists())
            chooser.setInitialDirectory(chosen);
        return chooser.showOpenDialog(null);
    }

    private void initEventTable() {
        TableColumn<EventTable, String> eventIdCol = new TableColumn<>("eventId");
        eventIdCol.setMinWidth(100);
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn<EventTable, String> nameCol = new TableColumn<>("name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<EventTable, String> placeCol = new TableColumn<>("place");
        placeCol.setMinWidth(100);
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        TableColumn<EventTable, String> dateCol = new TableColumn<>("date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        eventTable.getColumns().addAll(eventIdCol, nameCol, placeCol, dateCol);
        eventTable.setItems(eventTables);
        updateEventTable();
    }

    private void initDepositTable() {
        TableColumn<DepositTable, String> depositIdCol = new TableColumn<>("Id");
        depositIdCol.setMinWidth(30);
        depositIdCol.setCellValueFactory(new PropertyValueFactory<>("depositId"));
        TableColumn<DepositTable, String> dateCol = new TableColumn<>("date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<DepositTable, String> amountCol = new TableColumn<>("amount");
        amountCol.setMinWidth(80);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<DepositTable, String> personIdCol = new TableColumn<>("personId");
        personIdCol.setMinWidth(80);
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        TableColumn<DepositTable, String> eventIdCol = new TableColumn<>("eventId");
        eventIdCol.setMinWidth(80);
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn<DepositTable, String> installmentIdCol = new TableColumn<>("installmentId");
        installmentIdCol.setMinWidth(80);
        installmentIdCol.setCellValueFactory(new PropertyValueFactory<>("installmentId"));
        TableColumn<DepositTable, String> noPaymentCol = new TableColumn<>("noPayment");
        noPaymentCol.setMinWidth(100);
        noPaymentCol.setCellValueFactory(new PropertyValueFactory<>("noPayment"));
        depositTable.getColumns().addAll(depositIdCol, dateCol, amountCol, personIdCol, eventIdCol,
                installmentIdCol, noPaymentCol);
        depositTable.setItems(depositTables);
        updateDepositTable();
    }

    private void initInstallmentTable() {
        TableColumn<InstallmentTable, String> installmentIdCol = new TableColumn<>("installment id");
        installmentIdCol.setMinWidth(100);
        installmentIdCol.setCellValueFactory(new PropertyValueFactory<>("installmentId"));
        TableColumn<InstallmentTable, String> eventIdCol = new TableColumn<>("event Id");
        eventIdCol.setMinWidth(100);
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn<InstallmentTable, String> numberCol = new TableColumn<>("number");
        numberCol.setMinWidth(100);
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<InstallmentTable, String> dateCol = new TableColumn<>("date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<InstallmentTable, String> amountCol = new TableColumn<>("amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        instalmentTable.getColumns().addAll(installmentIdCol, eventIdCol, numberCol, dateCol, amountCol);
        instalmentTable.setItems(installmentTables);
        updateInstalmentTable();
    }

    private void initPersonTable() {
        TableColumn<PersonTable, String> personIdCol = new TableColumn<>("person id");
        personIdCol.setMinWidth(100);
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        TableColumn<PersonTable, String> nameCol = new TableColumn<>("name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<PersonTable, String> surnameCol = new TableColumn<>("surname");
        surnameCol.setMinWidth(100);
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        peronTable.getColumns().addAll(personIdCol, nameCol, surnameCol);
        peronTable.setItems(personTables);
        updatePesosTable();
    }

    public void readPersonCsvBtnOnAction() {
        personService.loadCsv(runFileChooser());
        updatePesosTable();
    }


    public void readEventCsvBtnOnAction() {
        eventService.loadCsv(runFileChooser());
        updateEventTable();
    }

    public void readInstalmentCsvBtnOnAction() {
        instalmentService.loadCsv(runFileChooser());
        updateInstalmentTable();
    }

    public void readDepositCsvBtnOnAction() {
        depositService.loadCsv(runFileChooser());
        updateDepositTable();
    }

    private void updatePesosTable() {
        personTables.clear();
        personService.getAll().forEach(person -> personTables.add(new PersonTable(person.getPersonId().toString(),
                person.getName(), person.getSurname())));
    }



    private void updateEventTable() {
        eventTables.clear();
        eventService.getAll().forEach(event -> {
            eventTables.add(new EventTable(event.getEventId().toString(),
                    event.getName(), event.getPlace(), getDate(event.getDate())));
        });
    }

    private void updateInstalmentTable() {
        installmentTables.clear();
        instalmentService.getAll().forEach(instalment -> installmentTables.add(
                new InstallmentTable(instalment.getInstalmentId().toString(), instalment.getEvent().getEventId().toString(),
                        instalment.getNoInstalment().toString(),getDate(instalment.getDate()), instalment.getAmount().toString())));
    }

    private void updateDepositTable() {
        depositTables.clear();
        depositService.getAll().forEach(deposit -> {
            depositTables.add(
                    new DepositTable(deposit.getDepositId().toString(), getDate(deposit.getDate()), deposit.getAmount().toString(),
                            deposit.getPerson().getPersonId().toString(), deposit.getEvent().getEventId().toString(),
                            deposit.getInstalment().getInstalmentId().toString(),
                            "bd"/*deposit.getNoPayment().toString()*/));
        });
    }

    public void deleteAllPersonBtnOnAction() {
        personService.deleteAll();
        personTables.clear();
        updatePesosTable();
    }

    public void deleteAllEventsBtnOnAction() {
        eventService.deleteAll();
        eventTables.clear();
        updateEventTable();
    }

    public void deleteAllInstalmentBtnOnActionBtn() {
        instalmentService.deleteAll();
        installmentTables.clear();
        updateInstalmentTable();
    }

    public void deleteAllDepositBtnOnActionBtn() {
        depositService.deleteAll();
        depositTables.clear();
        updateDepositTable();
    }

    private String getDate(Long date){
        cal.setTimeInMillis(date);
        return cal.get(Calendar.DAY_OF_MONTH)+"."+cal.get(Calendar.MONTH)+"."+cal.get(Calendar.YEAR);
    }

    public void addDepositBtnOnAction() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create deposit");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("person id:");
        TextField event = new TextField();
        event.setPromptText("event id:");
        TextField installment = new TextField();
        installment.setPromptText("installment id:");
        TextField date = new TextField();
        date.setPromptText("date");
        TextField amount = new TextField();
        date.setPromptText("amount");

        grid.add(new Label("Person id:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Event id:"), 0, 1);
        grid.add(event, 1, 1);
        grid.add(new Label("Installment id:"), 0, 2);
        grid.add(installment, 1, 2);
        grid.add(new Label("Date:"),0,3);
        grid.add(date,1,3);
        grid.add(new Label("Amount:"),0,4);
        grid.add(amount,1,4);
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new String[]{username.getText(), event.getText(),date.getText(),amount.getText(),
                        installment.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                var dateVal = LocalDate.parse(usernamePassword[2],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                cal.set(dateVal.getYear(),dateVal.getMonthValue(),dateVal.getDayOfMonth());
                depositService.addDeposit(new Deposit(personService.getByPersonId(Integer.parseInt(usernamePassword[0])),
                        eventService.getEventByEventId(Integer.parseInt(usernamePassword[1])),
                        instalmentService.getInstallmentById(Integer.parseInt(usernamePassword[4])),
                        cal.getTimeInMillis(),Integer.parseInt(usernamePassword[3])));
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateDepositTable();
        });
    }

    public void addInstallmentBtnOnAction() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create installment");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("event id:");
        TextField password = new TextField();
        password.setPromptText("number of Installment:");
        TextField date = new TextField();
        date.setPromptText("date");
        TextField amount = new TextField();
        date.setPromptText("amount");

        grid.add(new Label("Event id:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("number of Installment:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("Date:"),0,2);
        grid.add(date,1,2);
        grid.add(new Label("Amount:"),0,3);
        grid.add(amount,1,3);
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new String[]{username.getText(), password.getText(),date.getText(),amount.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                var dateVal = LocalDate.parse(usernamePassword[2],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                cal.set(dateVal.getYear(),dateVal.getMonthValue(),dateVal.getDayOfMonth());
                instalmentService.addInstalment(new Instalment(eventService.getEventByEventId(Integer.parseInt(usernamePassword[0]))
                        ,Integer.parseInt(usernamePassword[1]),cal.getTimeInMillis(),Integer.parseInt(usernamePassword[3])));
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateInstalmentTable();
        });
    }

    public void addEventBtnOnAction() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create event");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("event name");
        TextField password = new TextField();
        password.setPromptText("place");
        TextField date = new TextField();
        date.setPromptText("date");

        grid.add(new Label("Event name:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Place:"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("Date:"),0,2);
        grid.add(date,1,2);
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new String[]{username.getText(), password.getText(),date.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                var dateVal = LocalDate.parse(usernamePassword[2],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                cal.set(dateVal.getYear(),dateVal.getMonthValue(),dateVal.getDayOfMonth());
                eventService.addEvent(new Event(usernamePassword[0] ,usernamePassword[1],cal.getTimeInMillis()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateEventTable();
        });
    }

    public void addPersonBtnOnAction() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create person");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("name");
        TextField password = new TextField();
        password.setPromptText("surname");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                personService.addPerson(new Person(usernamePassword.getKey() ,usernamePassword.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            updatePesosTable();
        });
    }
}
