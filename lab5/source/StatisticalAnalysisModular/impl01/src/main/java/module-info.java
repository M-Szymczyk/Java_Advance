import pl.edu.pwr.modular.ex.api.AnalysisService;
import pl.edu.pwr.statistical.median.Median;


module statistical.median {
    requires log.api;
    exports pl.edu.pwr.statistical.median;
    provides AnalysisService
        with Median;
}