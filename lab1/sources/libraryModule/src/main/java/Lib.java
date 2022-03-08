import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Lib {
    static class FilesSS {
        String fileName;
        Status status;//0 - dodano, 1 - zmieniono, 2 - niezmieniono, 3 - usunieto;

        public FilesSS(String fileName, Status status) {
            this.fileName = fileName;
            this.status = status;
        }
    }

    private static Path pathToDirectory;
    private static final String baseFile = ".md5";
    private ArrayList<FilesSS> files = new ArrayList<>();

    public static void main(String[] args) {
        //String path = "C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab1\\sources\\libraryModule\\target\\classes\\123.txt";
        // new Lib(args[0]);
        new Lib("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab1\\sources\\libraryModule\\target\\classes\\test");
//        try {
//            System.out.println(countMD5ForFile(Paths.get(args[0])));
//        } catch (NoSuchAlgorithmException | IOException e) {
//            e.printStackTrace();
//        }
    }

    Lib(String path) {
        pathToDirectory = Paths.get(path);
        //jezeli nie ma pliku md5 to inicjalizujemy go i tyle
        //jezeli jest
        //  odczytujesz stare md5
        //  liczysz nowe md5 i zastępujesz stare
        //  odczytujesz nowe md5
        //  porównujesz nowe md5 z starym md5
        //      dla tych co są w nowym ale nie ma w starym to dodano
        //      są w starym ale nie w nowym usunieto
        //      są tu i tu to:
        //          jezeli hash sie zmienil to zmieniono
        //          jezeli nie to niezmieniono
        // jak to zapisac dalej??
        if (isFileRequireInitialization()) {
            try {
                generateMD5File();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ArrayList<String[]> oldMD5 = readFromFile();
                generateMD5File();
                ArrayList<String[]> newMd5 = readFromFile();
                for (Iterator<String[]> i = oldMD5.iterator(); i.hasNext(); ) {
                    String[] oldIteratorValue = i.next(); // wartość danego iteratora
                    boolean endSearching = false;
                    for (Iterator<String[]> i2 = newMd5.iterator(); i2.hasNext(); ) {
                        String[] newIteratorValue = i2.next();
                        if (oldIteratorValue[0].equals(newIteratorValue[0])) {
                            {
                                if (oldIteratorValue[1].equals(newIteratorValue[1]))
                                    files.add(new FilesSS(oldIteratorValue[0], Status.NIEZMIENIONO));
                                else
                                    files.add(new FilesSS(oldIteratorValue[0], Status.ZMIENIONO));
                            }
                            i2.remove();
                            endSearching = true;
                            break;
                        }
                    }
                    if (!endSearching)
                        files.add(new FilesSS(oldIteratorValue[0], Status.USUNIETO));
                }
                newMd5.forEach(strings -> files.add(new FilesSS(strings[0], Status.DODANO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (FilesSS file: files) {
            System.out.println(file.fileName + " " + file.status);
        }
    }

    boolean isFileRequireInitialization() {
        File folder = pathToDirectory.toFile();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory())
                if (fileEntry.getName().equals(".md5"))
                    return false;
        }
        return true;
    }

    String countMD5ForFile(Path pathToFile) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(pathToFile));
        return Arrays.toString(md.digest());
    }

    void saveToFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(pathToDirectory.toString() + "\\" + baseFile));
        writer.write(text);
        writer.close();
    }


    ArrayList<String[]> readFromFile() throws IOException {
        File file = new File(pathToDirectory.toFile() + "//" + baseFile);
        ArrayList<String[]> text = new ArrayList<>();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String[] data = myReader.nextLine().split(";");
            text.add(data);
        }
        myReader.close();
        return text;
    }

    void generateMD5File() throws IOException {
        File folder = pathToDirectory.toFile();
        StringBuilder textToSave = new StringBuilder();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory() && !fileEntry.getName().equals(".md5")) {
                try {
                    textToSave.append(fileEntry).append(";")
                            .append(countMD5ForFile(fileEntry.toPath())).append("\n");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
        saveToFile(textToSave.toString());
    }

//    static String run2(String path) throws NoSuchAlgorithmException, IOException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        try (InputStream is = Files.newInputStream(Paths.get(path));
//            DigestInputStream dis = new DigestInputStream(is, md)) {
//            return Arrays.toString(md.digest());
//        }
//    }
}
