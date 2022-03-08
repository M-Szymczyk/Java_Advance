//todo co jezeli plik folder zmieniu lokalizacje
public enum Status {
    DODANO(0), ZMIENIONO(1), NIEZMIENIONO(2), USUNIETO(3);
    final int status;

    private Status(int s){
        status=s;
    }
}
