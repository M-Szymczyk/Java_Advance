package pl.pwr.edu.app.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.json.simple.parser.ParseException;
import pl.pwr.edu.app.api.CountryData;
import pl.pwr.edu.app.api.GeoDb;
import pl.pwr.edu.app.bundle.Language;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GuiController implements Initializable {
    //wlasne pol    a
    Preferences usrPref = Preferences.userRoot().node(this.getClass().getName());
    GeoDb geoDb = new GeoDb();
    Language language = new Language(usrPref.get("language","en"),usrPref.get("contry","EN"));
    int goodAnswerIndex, goodAnswer;
    int[] answers = new int[4];
    CountryData countryData;
    boolean isPolishSet=true;
    //pola fxml
    @FXML
    public Label labelQuestion, labelTest;
    @FXML
    public Button btnAnswerA, btnAnswerC, btnAnswerB, btnAnswerD,btnRefresh,btnLanguage;
    private boolean isCorrect;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRefresh.setText(language.getBtnRefreshText());
        btnLanguage.setText(language.getBtnLanguageText());
        initViewElements();
    }

    private void initViewElements() {
        try {
            //inicjalizacja wlasnych pol
            countryData= geoDb.randCountry();
            goodAnswerIndex = new Random().nextInt(3);
            goodAnswer = geoDb.getNoRegions(countryData.getCountryId());
            //inicjalizacja odpowiedzi
            answers[goodAnswerIndex] = goodAnswer;
            for (int i = 0; i < 4; i++) {
                if (answers[i] == goodAnswer) {
                    answers[i] = goodAnswer;
                } else {
                    ArrayList<Integer> list = new ArrayList<>();
                    if(goodAnswer==0){
                        for (int ran = 1; ran < 15; ran++)
                            list.add(ran);
                    }
                 else {
                        for (int ran = goodAnswer - new Random().nextInt(goodAnswer); ran < goodAnswer * 2; ran++)
                            list.add(ran == goodAnswer ? ran - 1 : ran);
                    }
                    Collections.shuffle(list);
                    answers[i] = list.get(i);
                }
            }
            //inicjalizacja FXML
            labelQuestion.setText(language.getQuestion(countryData.getCountryName()));
            labelTest.setText("");
            //inicjalizacja przyciskow
            for (int i =0;i<4;i++) {
                switch (i) {
                    case 0:
                        btnAnswerA.setText(String.valueOf(answers[i]));
                        break;
                    case 1:
                        btnAnswerB.setText(String.valueOf(answers[i]));
                        break;
                    case 2:
                        btnAnswerC.setText(String.valueOf(answers[i]));
                        break;
                    case 3:
                        btnAnswerD.setText(String.valueOf(answers[i]));
                        break;
                }
            }

        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void isAnswerCorrect(Event event){
        if(Integer.parseInt(((Button)event.getSource()).getText())==goodAnswer) {
            labelTest.setTextFill(Color.GREEN);
            isCorrect = true;
        }
        else {
            labelTest.setTextFill(Color.RED);
            isCorrect = false;
        }
        labelTest.setText(language.getAnswer(countryData.getCountryName(),goodAnswer, isCorrect));
    }
    public void setBtnRefresh(){
        initViewElements();
    }
    public void setBtnLanguage(){
        isPolishSet = !isPolishSet;
        if(isPolishSet){
            language= new Language("pl","PL");
            usrPref.put("language","pl");
            usrPref.put("contry","PL");
        }
        else {
            language= new Language("en","EN");
            usrPref.put("language","en");
            usrPref.put("contry","EN");
        }
        //System.out.println(usrPref.get("language",null)+usrPref.get("contry",null));
        btnRefresh.setText(language.getBtnRefreshText());
        btnLanguage.setText(language.getBtnLanguageText());
        labelQuestion.setText(language.getQuestion(countryData.getCountryName()));
        if(!labelTest.getText().equals(""))
            labelTest.setText(language.getAnswer(countryData.getCountryName(),goodAnswer,isCorrect));
        //initViewElements();
    }
}