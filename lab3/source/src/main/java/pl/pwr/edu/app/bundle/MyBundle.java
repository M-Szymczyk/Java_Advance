package pl.pwr.edu.app.bundle;

import java.util.ListResourceBundle;

/**
 * Class contain text for default language
 */
public class MyBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "How many administrative regions exist in the country {0}?"},
                {"correct", "Yes, you are right. In the country {0} exist {1} administrative regions."},
                {"incorrect", "No, you are wrong. In the country {0} {1} ."},
                {"noAdv", "dont exist administrative regions"},
                {"oneAdv", "is one administrative region"},
                {"multipleAdv", "are {2} administrative regions"},
                {"btnRefresh", "refresh"},
                {"btnLanguage", "change language"}};
    }
}
