package pl.pwr.edu.lab.kubik.financesmanager.db;


import localhost.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import pl.pwr.edu.lab.kubik.financesmanager.db.model.Deposit;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Event;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Instalment;
import pl.pwr.edu.lab.kubik.financesmanager.db.model.Person;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.DepositService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.EventService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.InstalmentService;
import pl.pwr.edu.lab.kubik.financesmanager.db.service.PersonService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Endpoint
public class DatabaseEndpoint {
    private static final String NAMESPACE_URI = "localhost";

    private final EventService eventService;
    private final PersonService personService;
    private final InstalmentService repaymentService;
    private final DepositService depositService;

    @Autowired
    public DatabaseEndpoint(EventService eventService,
                            PersonService personService,
                            InstalmentService repaymentService,
                            DepositService depositService) {
        this.eventService = eventService;
        this.personService = personService;
        this.repaymentService = repaymentService;
        this.depositService = depositService;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEventRequest")
    @ResponsePayload
    public GetEventResponse getEventResponse(@RequestPayload GetEventRequest request) {
        GetEventResponse response = new GetEventResponse();

        Event event = eventService.getEventByEventId(request.getEventID());
        response.setEvent(convertEventToSoapEvent(event));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setEventRequest")
    @ResponsePayload
    public SetEventResponse setEventResponse(@RequestPayload SetEventRequest request) {
        SetEventResponse response = new SetEventResponse();

        Date date = convertStringToDate(request.getDate());
        eventService.addEvent(new Event(request.getName(), request.getPlace(), date.getTime()));
        response.setEventID(eventService.getEventByNameAndPlaceAndDate(request.getName(), request.getPlace(), date.getTime()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPersonResponse(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();

        Person person = personService.getByPersonId(request.getPersonID());
        response.setPerson(convertPersonToSoapPerson(person));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setPersonRequest")
    @ResponsePayload
    public SetPersonResponse setPersonResponse(@RequestPayload SetPersonRequest request) {
        SetPersonResponse response = new SetPersonResponse();

        personService.addPerson(new Person(request.getName(), request.getSurname()));
        response.setPersonID(personService.getByNameAndSurname(request.getName(), request.getSurname()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInstalmentRequest")
    @ResponsePayload
    public GetInstalmentResponse getInstalmentResponse(@RequestPayload GetInstalmentRequest request) {
        GetInstalmentResponse response = new GetInstalmentResponse();

        Instalment instalment = repaymentService.getInstallmentById(request.getInstalmentId());
        response.setInstalment(convertInstalmentToSoapInstalment(instalment));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setInstalmentRequest")
    @ResponsePayload
    public SetInstalmentResponse setInstalmentResponse(@RequestPayload SetInstalmentRequest request) {
        SetInstalmentResponse response = new SetInstalmentResponse();

        Date date = convertStringToDate(request.getDepositTime());
        Event event = eventService.getEventByEventId(request.getEventID());
        repaymentService.addInstalment(new Instalment(
                event,
                request.getNumber(),
                date.getTime(),
                request.getValue()));
        response.setInstalmentID(repaymentService.getInstalmentIdByEventAndNoInstalmentAndDateAAndAmount(
                event,
                request.getNumber(),
                date.getTime(),
                request.getValue()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInstalmentRequest")
    @ResponsePayload
    public CreateInstalmentResponse CreateInstalmentResponse(@RequestPayload CreateInstalmentRequest request) {
        CreateInstalmentResponse response = new CreateInstalmentResponse();

        Date date = convertStringToDate(request.getDepositTime());
        Event event = convertSoapEventToEvent(request.getEventID());
        eventService.addEvent(event);
        repaymentService.addInstalment(new Instalment(
                event,
                request.getNumber(),
                date.getTime(),
                request.getValue()));
        response.setInstalmentId(repaymentService.getInstalmentIdByEventAndNoInstalmentAndDateAAndAmount(
                event,
                request.getNumber(),
                date.getTime(),
                request.getValue()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDepositRequest")
    @ResponsePayload
    public GetDepositResponse GetDepositResponse(@RequestPayload GetDepositResponse request) {
        GetDepositResponse response = new GetDepositResponse();

        Deposit deposit = depositService.getDepositByDepositId(request.getDeposit().getDepositID());
        response.setDeposit(convertDepositToSoapDeposit(deposit));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setDepositRequest")
    @ResponsePayload
    public SetDepositResponse SetDepositResponse(@RequestPayload SetDepositRequest request) {
        SetDepositResponse response = new SetDepositResponse();

        Event event = eventService.getEventByEventId(request.getEventID());
        Person person = personService.getByPersonId(request.getPersonID());
        Instalment instalment = repaymentService.getInstallmentById(request.getInstalmentID());
        depositService.addDeposit(new Deposit(
                person,
                event,
                instalment,
                convertStringToDate(request.getDepositDate()).getTime(),
                request.getCashValue()));
        response.setDepositId(depositService.getDepositByDateAndAmountAndPersonAndEventAndInstalment(
                convertStringToDate(request.getDepositDate()).getTime(),
                request.getCashValue(),
                person,
                event,
                instalment
        ));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createDepositRequest")
    @ResponsePayload
    public CreateDepositResponse CreateDepositResponse(@RequestPayload CreateDepositRequest request) {
        CreateDepositResponse response = new CreateDepositResponse();

        Event event = convertSoapEventToEvent(request.getEventID());
        eventService.addEvent(event);
        Person person = convertSoapPersonToPerson(request.getPersonID());
        Instalment instalment = convertSoapInstalmentToInstalment(
                request.getInstalmentID(),
                event);
        personService.addPerson(person);
        repaymentService.addInstalment(instalment);

        depositService.addDeposit(new Deposit(
                person,
                event,
                instalment,
                convertStringToDate(request.getDepositDate()).getTime(),
                request.getCashValue()));
        response.setDepositID(depositService.getDepositByDateAndAmountAndPersonAndEventAndInstalment(
                convertStringToDate(request.getDepositDate()).getTime(),
                request.getCashValue(),
                person,
                event,
                instalment
        ));

        return response;
    }

    private static Date convertStringToDate(String date) {
        return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    private static localhost.Event convertEventToSoapEvent(Event event) {
        localhost.Event eventSoap = new localhost.Event();
        eventSoap.setEventID(event.getEventId());
        eventSoap.setName(event.getName());
        eventSoap.setPlace(event.getPlace());
        eventSoap.setDate(event.getDate().toString());
        return eventSoap;
    }

    private static Event convertSoapEventToEvent(localhost.Event eventSoap) {
        Event event = new Event();
        event.setName(eventSoap.getName());
        event.setPlace(eventSoap.getPlace());
        event.setDate(convertStringToDate(eventSoap.getDate()).getTime());
        return event;
    }

    private static localhost.Person convertPersonToSoapPerson(Person person) {
        localhost.Person personSoap = new localhost.Person();
        personSoap.setPersonID(person.getPersonId());
        personSoap.setName(person.getName());
        personSoap.setSurname(person.getSurname());
        return personSoap;
    }

    private static Person convertSoapPersonToPerson(localhost.Person personSoap) {
        Person person = new Person();
        person.setName(personSoap.getName());
        person.setSurname(personSoap.getSurname());
        return person;
    }

    private static localhost.Instalment convertInstalmentToSoapInstalment(Instalment instalment) {
        localhost.Instalment repaymentSoap = new localhost.Instalment();
        repaymentSoap.setInstalmentID(instalment.getInstalmentId());
        repaymentSoap.setEventID(convertEventToSoapEvent(instalment.getEvent()));
        repaymentSoap.setNumber(instalment.getNoInstalment());
        repaymentSoap.setValue(instalment.getAmount());
        repaymentSoap.setDepositTime(instalment.getDate().toString());
        return repaymentSoap;
    }

    private static Instalment convertSoapInstalmentToInstalment(localhost.Instalment repaymentSoap, Event event) {
        Instalment instalment = new Instalment();
        instalment.setEvent(event);
        instalment.setNoInstalment(repaymentSoap.getNumber());
        instalment.setAmount(repaymentSoap.getValue());
        instalment.setDate(convertStringToDate(repaymentSoap.getDepositTime()).getTime());
        return instalment;
    }

    private localhost.Deposit convertDepositToSoapDeposit(Deposit deposit) {
        localhost.Deposit paymentSoap = new localhost.Deposit();
        paymentSoap.setDepositID(deposit.getDepositId());
        paymentSoap.setDepositDate(deposit.getDate().toString());
        paymentSoap.setEventID(convertEventToSoapEvent(deposit.getEvent()));
        paymentSoap.setPersonID(convertPersonToSoapPerson(deposit.getPerson()));
        paymentSoap.setInstalmentID(convertInstalmentToSoapInstalment(deposit.getInstalment()));
        paymentSoap.setCashValue(deposit.getAmount());
        return paymentSoap;
    }
}