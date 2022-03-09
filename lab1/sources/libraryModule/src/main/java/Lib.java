import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Simple library to version control of files
 */
public class Lib {
    /**
     * Internal class to save state od files and their names
     */
    static class FilesSS {
        String fileName;
        Status status;//0 - dodano, 1 - zmieniono, 2 - nie zmieniono, 3 - usunięto;

        public FilesSS(String fileName, Status status) {
            this.fileName = fileName;
            this.status = status;
        }
    }

    private static Path pathToDirectory;
    private static final String baseFile = ".md5";
    private final ArrayList<FilesSS> files = new ArrayList<>();

    public static void main(String[] args) {
        Lib l = new Lib(args[0]);
        //new Lib("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab1\\sources\\libraryModule\\target\\classes\\test");
        for (FilesSS file : l.files) {
            System.out.println(file.fileName + " " + file.status);
        }
    }

    /**
     * Constructor of class
     * if lib file isn't generated then file is generated, all files get add status
     * if it's generated then new lib file if generated and then old and new version of lib file is compared files gets
     * status dependently of their change
     *
     * @param path to directory which need version control
     */
    Lib(String path) {
        pathToDirectory = Paths.get(path);
        if (isFileRequireInitialization()) {
            ///there isn't lib file
            try {
                generateMD5File();
                ArrayList<String[]> MD5 = readFromFile();
                MD5.forEach(strings -> files.add(new FilesSS(strings[0], Status.DODANO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ///there's lib file
            try {
                ArrayList<String[]> oldMD5 = readFromFile();
                generateMD5File();
                ArrayList<String[]> newMd5 = readFromFile();
                /// compare versions of lib files
                for (String[] oldIteratorValue : oldMD5) {
                    boolean endSearching = false;
                    for (String[] newIteratorValue : newMd5) {
                        if (oldIteratorValue[0].equals(newIteratorValue[0])) {
                            {
                                ///if file is in both versions then given file could be change or nor
                                if (oldIteratorValue[1].equals(newIteratorValue[1]))
                                    files.add(new FilesSS(oldIteratorValue[0], Status.NIEZMIENIONO));
                                else
                                    files.add(new FilesSS(oldIteratorValue[0], Status.ZMIENIONO));
                            }

                            newMd5.remove(newIteratorValue);
                            endSearching = true;
                            break;
                        }
                    }
                    /// if file is only in old version then file was deleted
                    if (!endSearching)
                        files.add(new FilesSS(oldIteratorValue[0], Status.USUNIETO));
                }
                /// files that remain in new version then file was added
                newMd5.forEach(strings -> files.add(new FilesSS(strings[0], Status.DODANO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method check if there is lib file
     *
     * @return false if there is
     */
    private boolean isFileRequireInitialization() {
        File folder = pathToDirectory.toFile();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory())
                if (fileEntry.getName().equals(".md5"))
                    return false;
        }
        return true;
    }

    /**
     * Method count md5 hash of file
     *
     * @param pathToFile direct path to file
     * @return hash of file
     * @throws IOException if there is something wrong with path
     */
    private String countMD5ForFile(Path pathToFile) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(pathToFile));
            return Arrays.toString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method save lib file
     *
     * @param text to save structure: filename:md5hash
     * @throws IOException if there is problem with saving for example access rights
     */
    private void saveToFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(pathToDirectory.toString() + "\\" + baseFile));
        writer.write(text);
        writer.close();
    }

    /**
     * Method read lib file
     *
     * @return array of Strings where at 0 index is filename and 1 md5 hash
     * @throws IOException if there is problem with saving for example access rights or file don't exist
     */
    private ArrayList<String[]> readFromFile() throws IOException {
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

    /**
     * Method generate lib file
     *
     * @throws IOException if there is problem with saving for example access rights
     */
    private void generateMD5File() throws IOException {
        File folder = pathToDirectory.toFile();
        StringBuilder textToSave = new StringBuilder();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory() && !fileEntry.getName().equals(".md5")) {
                textToSave.append(fileEntry).append(";")
                        .append(countMD5ForFile(fileEntry.toPath())).append("\n");
            }
        }
        saveToFile(textToSave.toString());
    }
}
