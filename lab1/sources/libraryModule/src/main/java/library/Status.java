package library;

public enum Status {
    DODANO(0), ZMIENIONO(1), NIEZMIENIONO(2), USUNIETO(3);
    final Integer status;

    Status(Integer s) {
        status = s;
    }

    @Override
    public String toString() {
        //return status.toString();
        switch (status) {
            case 0:
                return "Dodano";
            case 1:
                return "Zmieniono";
            case 2:
                return "Nie zmieniono";
            case 3:
                return "Usunięto";
            default:
                return "---";
        }
    }
}
