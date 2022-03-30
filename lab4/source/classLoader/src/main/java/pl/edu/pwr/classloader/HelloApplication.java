package pl.edu.pwr.classloader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.pwr.loader.CustomClassLoader;
import pl.edu.pwr.processing.Status;
import pl.edu.pwr.processing.StatusListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

//        launch();
        try {
            Path path = Paths.get("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\" +
                    "mszymczyk_248881_java\\lab4\\source\\processors\\out\\production\\processors");
            CustomClassLoader classLoader = new CustomClassLoader(path);
            Class<?> loadedClass = classLoader.findClass("pl.edu.pwr.lib.ToUpperClass");
            Object classInstance = loadedClass.getConstructor().newInstance();
            Method getInfoMethod = loadedClass.getDeclaredMethod("getInfo");
            System.out.println((String) getInfoMethod.invoke(classInstance));
            Method submitTaskMethod = loadedClass.getDeclaredMethod("submitTask", String.class, StatusListener.class);
            boolean b = (boolean) submitTaskMethod.invoke(classInstance, "Tekst na wejście", new StatusListener() {
                @Override
                public void statusChanged(Status s) {
                    System.out.println("Progress:" + s.getProgress() + " TaskId:" + s.getTaskId());
                }
            });
            if (b)
                System.out.println("Processing started correctly");
            else
                System.out.println("Processing ended with an error");
            Method getResultMethod = loadedClass.getDeclaredMethod("getResult");
            ExecutorService executor = Executors.newSingleThreadExecutor();

            // uruchom zadanie, które skończy się, gdy result!=null
            executor.submit(() -> {
                String result = null;
                while (true) {
                    // System.out.println(scheduleFuture.isDone());

                    try {
                        Thread.sleep(800);

                        // String result = (String) getResultMethod.invoke(o,new Object[] {});
                        result = (String) getResultMethod.invoke(classInstance);
                    } catch (InterruptedException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (result != null) {
                        System.out.println("Result: " + result);
                        executor.shutdown();
                        break;
                    }
                }
            });

            System.out.println("main FINISHED");
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}