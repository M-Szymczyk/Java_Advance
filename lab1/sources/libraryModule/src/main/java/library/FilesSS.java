package library;

/**
 * Internal class to save state od files and their names
 */
public class FilesSS {
    public String fileName;
    public Status status;//0 - dodano, 1 - zmieniono, 2 - nie zmieniono, 3 - usunięto;

    public FilesSS(String fileName, Status status) {
        this.fileName = fileName;
        this.status = status;
    }
}
