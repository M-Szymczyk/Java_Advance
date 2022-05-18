package pl.edu.pwr.kubik.encrypter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    public static void writeToFile(String fileName, byte[] key) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(fileName,true)) {
            fos.write(key);
        }
    }

    public static byte[] loadFileBytes(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }
}
