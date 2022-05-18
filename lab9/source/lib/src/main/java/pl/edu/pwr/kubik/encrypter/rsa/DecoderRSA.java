package pl.edu.pwr.kubik.encrypter.rsa;

import pl.edu.pwr.kubik.encrypter.FileService;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import static pl.edu.pwr.kubik.encrypter.FileService.loadFileBytes;
import static pl.edu.pwr.kubik.encrypter.FileService.writeToFile;

public class DecoderRSA {
    private final Cipher cipher;
    private final SecretKey secretKey;
    private PublicKey publicKey;

    public DecoderRSA(SecretKey secretKey, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(algorithm);
        this.secretKey = secretKey;
    }


}
