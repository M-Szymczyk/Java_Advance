package pl.pwr.edu.app.bundle;

import java.util.ListResourceBundle;

public class MyBundle_pl_PL extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"question", "Ile regionów administracyjnych istnieje w państwie {0}?"},
                {"correct", "Tak, masz rację. W państwie {0}  {1} "},
                {"incorrect", "Nie, nie masz racji.  W państwie {0}  {1} ."},
                {"noAdv", "nie istnieją regiony administracyjne"},
                {"oneAdv", "istnieje jeden region administracyjny"},
                {"twoToFour", "istnieją {2} regiony administracyjne"},
                {"fourTo101", "istnieje {2} regionów administracyjnych"},
                {"btnRefresh","odśwież"},
                {"btnLanguage","zmień język"}
        };
    }
}
//twoToFour
