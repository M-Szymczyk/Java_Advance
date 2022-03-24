package pl.pwr.edu.app.api;

public class CountryData {
    private final String countryName;
    private final String countryId;

    public CountryData(String countryName, String countryId) {
        this.countryName = countryName;
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryId() {
        return countryId;
    }
}
