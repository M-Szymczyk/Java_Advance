package pl.pwr.edu.lab.kubik.financesmanager;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pwr.edu.lab.kubik.financesmanager.gui.Gui;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(Gui.class, args);
    }
}
