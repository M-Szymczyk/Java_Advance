package pl.pwr.edu.lab.kubik.financesmanager.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.pwr.edu.lab.kubik.financesmanager.Main;

public class Gui extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(Main.class)
                .run(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(GuiController.class);


//        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("gui-view.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Client App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}