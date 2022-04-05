import pl.edu.pwr.modular.ex.api.AnalysisService;

module log.client {
    uses AnalysisService;
    requires log.api;
    requires javafx.controls;
    requires javafx.fxml;
    opens pl.edu.pwr.guiModular to javafx.fxml;
    exports pl.edu.pwr.guiModular;
}