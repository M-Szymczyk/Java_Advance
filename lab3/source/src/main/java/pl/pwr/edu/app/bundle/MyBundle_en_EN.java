package pl.pwr.edu.app.bundle;

import java.util.ListResourceBundle;

public class MyBundle_en_EN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "How many administrative regions exist in the country {0}?"},
                {"correct", "Yes, you are right. In the country {0} {1}."},
                {"incorrect", "No, you are wrong. In the country {0} {1} ."},
                {"noAdv", "dont exist administrative regions"},
                {"oneAdv", "exist one administrative region"},
                {"multipleAdv", "exist {2} administrative regions"},
                {"btnRefresh","refresh"},
                {"btnLanguage","change language"}};
    }
}
