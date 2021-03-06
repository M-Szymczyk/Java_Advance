package pl.pwr.edu.app.bundle;

import java.text.*;
import java.util.*;


public class Language {

    String language;
    String country;
    Locale l;

    public Language(String language, String country) {
        this.language = language;
        this.country = country;
        l = new Locale(language, country);
    }

    /**
     * Metod get text for question from bundle
     *
     * @param countryFullName requires to filling question
     * @return full question with selected language
     */
    public String getQuestion(String countryFullName) {
        MessageFormat mf = new MessageFormat("");

        Object[] arg = {countryFullName};
        mf.applyPattern(ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("question"));
        mf.setLocale(l);
        return mf.format(arg);
    }

    /**
     * Method get text for checking answer
     *
     * @param countryFullName requires to filling question
     * @param correctN        answer was correct or not
     * @param isCorrect       correct answer
     * @return full result
     */
    public String getAnswer(String countryFullName, int correctN, boolean isCorrect) {
        MessageFormat platform;
        ChoiceFormat fileForm;
        double[] fileLimits;
        String[] filePart;
        if (language.equals("pl")) {
            fileLimits = new double[]{0, 1, 2, 3};
            filePart = new String[]{countryFullName + " " +
                    ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("noAdv"),
                    countryFullName + " " + ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("oneAdv"),
                    countryFullName + " " + ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("twoToFour"),
                    countryFullName + " " + ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("fourTo101")};
        } else {
            fileLimits = new double[]{0, 1, 2};
            filePart = new String[]{countryFullName + " " +
                    ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("noAdv"),
                    countryFullName + " " +
                            ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("oneAdv"),
                    countryFullName + " " +
                            ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("multipleAdv")};

        }
        fileForm = new ChoiceFormat(fileLimits, filePart);
        Format[] testFormats = {fileForm, null, NumberFormat.getInstance()};
        platform = new MessageFormat(ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l)
                .getString(isCorrect ? "correct" : "incorrect"));
        platform.setLocale(l);
        platform.setFormats(testFormats);
        Object[] testArgs = {null, "", null};
        for (int i = 0; i < 5; ++i) {
            testArgs[0] = correctN;
            testArgs[2] = testArgs[0];
        }
        return platform.format(testArgs);
    }

    /**
     * @return text for refresh button
     */
    public String getBtnRefreshText() {
        return ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("btnRefresh");
    }

    /**
     * @return text for change language button
     */
    public String getBtnLanguageText() {
        return ResourceBundle.getBundle("pl.pwr.edu.app.bundle.MyBundle", l).getString("btnLanguage");
    }
}
