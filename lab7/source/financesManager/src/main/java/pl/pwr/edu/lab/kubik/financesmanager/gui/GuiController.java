package pl.pwr.edu.lab.kubik.financesmanager.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import java.util.Calendar;
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
    private final EventService eventService;
    private final PersonService personService;
    private final DepositService depositService;
    private final InstalmentService instalmentService;

    public GuiController(EventService eventService, PersonService personService, DepositService depositService, InstalmentService instalmentService) {
        this.eventService = eventService;
        this.personService = personService;
        this.depositService = depositService;
        this.instalmentService = instalmentService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPersonTable();
        initEventTable();
        initInstallmentTable();
        initDepositTable();
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
        TableColumn<DepositTable, String> depositIdCol = new TableColumn<>("depositId");
        depositIdCol.setMinWidth(100);
        depositIdCol.setCellValueFactory(new PropertyValueFactory<>("depositId"));
        TableColumn<DepositTable, String> dateCol = new TableColumn<>("date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<DepositTable, String> amountCol = new TableColumn<>("amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<DepositTable, String> personIdCol = new TableColumn<>("personId");
        personIdCol.setMinWidth(100);
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        TableColumn<DepositTable, String> eventIdCol = new TableColumn<>("eventId");
        eventIdCol.setMinWidth(100);
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn<DepositTable, String> installmentIdCol = new TableColumn<>("installmentId");
        installmentIdCol.setMinWidth(100);
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
        personService.getAll().forEach(person -> personTables.add(new PersonTable(person.getPersonId().toString(),
                person.getName(), person.getSurname())));
    }

    public void readPersonCsvBtnOnAction() {
        personService.loadCsv(runFileChooser());
        personService.getAll().forEach(person -> personTables.add(new PersonTable(person.getPersonId().toString(),
                person.getName(), person.getSurname())));
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

    private void updateEventTable() {
        var cal = Calendar.getInstance();
        eventService.getAll().forEach(event -> {
            cal.setTimeInMillis(event.getDate());
            eventTables.add(new EventTable(event.getEventId().toString(),
                    event.getName(), event.getPlace(), cal.getTime().toString()));
        });
    }

    private void updateInstalmentTable() {
        var cal = Calendar.getInstance();
        instalmentService.getAll().forEach(instalment -> {
            cal.setTimeInMillis(instalment.getDate());
            installmentTables.add(
                    new InstallmentTable(instalment.getInstalmentId().toString(), instalment.getEvent().getEventId().toString(),
                            instalment.getNoInstalment().toString(), cal.getTime().toString(), instalment.getAmount().toString()));
            ;
        });
    }

    private void updateDepositTable() {
        var cal = Calendar.getInstance();
        depositService.getAll().forEach(deposit -> {
            cal.setTimeInMillis(deposit.getDate());
            depositTables.add(
                    new DepositTable(deposit.getDepositId().toString(),cal.getTime().toString() , deposit.getAmount().toString(),
                            deposit.getPerson().getPersonId().toString(), deposit.getEvent().getEventId().toString(),
                            deposit.getInstalment().getInstalmentId().toString(),
                            "bd"/*deposit.getNoPayment().toString()*/));
        });
    }

    public void deleteAllPersonBtnOnAction() {

    }

    public void deleteAllEventsBtnOnAction() {

    }

    public void deleteAllInstalmentBtnOnActionBtn() {

    }

    public void deleteAllDepositBtnOnActionBtn() {

    }
}
