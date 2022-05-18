package pl.edu.pwr.kubik.encrypter.rsa;

import javax.crypto.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import static pl.edu.pwr.kubik.encrypter.FileService.loadFileBytes;
import static pl.edu.pwr.kubik.encrypter.FileService.writeToFile;


public class EncoderRSA {
    private final Cipher cipher;
    public PrivateKey privateKey = null;
    private PublicKey publicKey;

    public EncoderRSA( String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(algorithm);
    }

    public void getPrivate(String privateKeyFile) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(privateKeyFile).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);
    }

    public void encryptFile(String encryptedFile, Path pathToFileToEncrypt)
            throws InvalidKeyException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        try {
            var bytesFile = loadFileBytes(pathToFileToEncrypt);
            int filePart = 1;
            while (filePart * 116 < bytesFile.length) {
                writeToFile(encryptedFile, cipher.doFinal(Arrays.copyOfRange(bytesFile, (filePart - 1) * 116, filePart * 116)));
                filePart++;
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public void getPublic(Path publicKeyPath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(publicKeyPath);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);
    }

    public void decryptFile(String decryptedFile, Path pathToFileToDecrypt)
            throws IOException, GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        var bytesFile = loadFileBytes(pathToFileToDecrypt);
        int filePart = 1,packageSize = 128;
        while (filePart * packageSize < bytesFile.length) {
            writeToFile(decryptedFile, cipher.doFinal(Arrays.copyOfRange(bytesFile, (filePart-1) * packageSize,
                    filePart*packageSize)));
            filePart++;
        }
    }

//    public static void main(String[] args) throws Exception {
//        //"RSA"
//        EncoderRSA encoderRSA = new EncoderRSA("AES");
//        encoderRSA.getPublic(Path.of("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\publicKey"));
//        if(encoderRSA.publicKey != null){
//            encoderRSA.decryptFile(
//                    "C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\decryptedFile.txt",
//                    Path.of("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\encryptedFile.txt"));
//        }
//    }


    public static void main(String[] args) throws Exception {
        EncoderRSA encoderRSA = new EncoderRSA("RSA");
        encoderRSA.getPrivate("privateKey");
        if (encoderRSA.privateKey != null) {
            encoderRSA.encryptFile(
                    "C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\encryptedFile.txt",
                    Path.of("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\source\\lib\\text.txt"));
        }
    }
}
