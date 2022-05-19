package pl.edu.pwr.kubik.encoder.app;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import pl.edu.pwr.kubik.encrypter.RSAEncrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Label filePathToFile, destOfEncryption, filePathToEncryptedFile, publicKey, decryptionDest;
    File fileToEncrypt, fileToDecrypt, PublicKeyPath;
    File pathToSaveEncryptedFile, pathToDecryptedFile;
    byte[] AESKEy;
    PublicKey publicKeyLoaded;
    FileChooser fileChooser = new FileChooser();

    public void loadFileToEncrypt() {
        fileToEncrypt = fileChooser.showOpenDialog(null);
        filePathToFile.setText(fileToEncrypt.getAbsolutePath());
    }

    public void loadEncryptedFileLocation() {
        pathToSaveEncryptedFile = fileChooser.showOpenDialog(null);
        destOfEncryption.setText(pathToSaveEncryptedFile.getAbsolutePath());
    }

    public void encryptFile() {
        try {
            var keys = RSAEncrypt.generateKey();
            RSAEncrypt.saveKeyToFile(keys.getPublic());
            AESKEy = RSAEncrypt.encryptFile(keys.getPrivate(), fileToEncrypt, pathToSaveEncryptedFile);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    public void loadEncryptedFile() {
        fileToDecrypt = fileChooser.showOpenDialog(null);
        filePathToEncryptedFile.setText(fileToDecrypt.getAbsolutePath());
    }

    public void loadPublicKey() {
        PublicKeyPath = fileChooser.showOpenDialog(null);
        publicKey.setText(PublicKeyPath.getAbsolutePath());
        publicKeyLoaded = RSAEncrypt.readKeyFromFile(PublicKeyPath);
    }

    public void loadDecryptedFileLocation() {
        FileChooser directoryChooser = new FileChooser();
        pathToDecryptedFile = directoryChooser.showOpenDialog(null);
        decryptionDest.setText(pathToDecryptedFile.getAbsolutePath());
    }

    public void decryptFile() {
        try {
            RSAEncrypt.decryptFile(publicKeyLoaded, AESKEy, fileToDecrypt, pathToDecryptedFile);
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var initDir = new File("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab9\\release");
        if (initDir.exists())
            fileChooser.setInitialDirectory(initDir);
    }
}