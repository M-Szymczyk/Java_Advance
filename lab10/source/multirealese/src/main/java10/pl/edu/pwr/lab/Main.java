package pl.edu.pwr.lab;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Main {
    public static void main(String[] args)  throws  Exception{
        System.out.println("Uruchomiono z java 10.0");
        var key = generateKey();
        var key2 = encryptFile(key.getPrivate(),
                new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\text.txt"),
                new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\encryptedFile.txt"));
        decryptFile(key.getPublic(), key2,
                new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\encryptedFile.txt"),
                new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\decryptedFile.txt"));
    }

    public static KeyPair generateKey() throws NoSuchAlgorithmException {
        var keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        return keyGenerator.generateKeyPair();
    }

    public static void saveKeyToFile(PublicKey publicKey) {
        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PublicKey readKeyFromFile(File publicKeyFile) {
        try {
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptFile(PrivateKey key,
                                     File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        var generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        var secKey = generator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        var buffer = new byte[64];
        var bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            var output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        var outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();

        var cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher2.init(Cipher.PUBLIC_KEY, key);
        return cipher2.doFinal(secKey.getEncoded());
    }

    public static void decryptFile(PublicKey key, byte[] encryptedKey,
                                   File encryptedFile, File decryptedFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PRIVATE_KEY, key);
        var decryptedKey = cipher.doFinal(encryptedKey);

        var originalKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
        var aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        var inputStream = new FileInputStream(encryptedFile);
        var outputStream = new FileOutputStream(decryptedFile);
        var buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = aesCipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        var output = aesCipher.doFinal();
        if (output != null) {
            outputStream.write(output);
        }
        inputStream.close();
        outputStream.close();
    }
}
