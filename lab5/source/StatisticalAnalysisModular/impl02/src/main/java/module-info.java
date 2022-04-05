import pl.edu.pwr.modular.ex.api.AnalysisService;
import pl.edu.pwr.statistical.average.Average;


module statistical.averege {
    requires log.api;
    exports pl.edu.pwr.statistical.average;
    provides AnalysisService
        with Average;
}