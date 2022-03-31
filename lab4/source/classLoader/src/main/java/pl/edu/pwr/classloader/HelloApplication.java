package pl.edu.pwr.classloader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.pwr.loader.CustomClassLoader;
import pl.edu.pwr.loader.SomeStatusListener;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

//    public static ModuleLayer createLayer(String modulePath, Set<String> rootModules) throws IllegalAccessException {
//        Path path = Paths.get(modulePath);
//
//        // Define the module finders to be used in creating a
//        // configuration for the custom layer
//        ModuleFinder beforFinder = ModuleFinder.of(path);
//        ModuleFinder afterFinder = ModuleFinder.of();
//
//        // Create a configuration for the custom layer
//        Configuration parentConfig = ModuleLayer.boot().configuration();
//        Configuration config =
//                parentConfig.resolve(beforFinder, afterFinder, rootModules);
//
//        /* Create a custom layer with one class loader. The parent for
//           the class loader is the system class loader. The boot layer is
//           the parent layer of this custom layer.
//         */
//        ClassLoader sysClassLoader = new CustomClassLoader(path);
//        ModuleLayer parentLayer = ModuleLayer.boot();
//        ModuleLayer layer = parentLayer.defineModulesWithOneLoader(config, sysClassLoader);
//
//        // Check if we loaded the module in this layer
//        if (layer.modules().isEmpty()) {
//            System.out.println("\nCould not find the module " + rootModules
//                    + " at " + modulePath + ". "
//                    + "Please make sure that the com.jdojo.layer.v2.jar exists "
//                    + "at this location." + "\n");
//        }
//
//        return layer;
//    }


    public static void main(String[] args) {

        launch();

//        try {
//            final String CUSTOM_MODULE_LOCATION = "C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab4\\source\\processors\\out\\production";
//
//            // Define the set of root modules to be resolved in the custom layer
//            Set<String> rootModules = Set.of("processors");
//
//            // Create a custom layer
//            ModuleLayer customLayer = createLayer(CUSTOM_MODULE_LOCATION, rootModules);
//
//            // Test the class in the boot layer
//            ModuleLayer bootLayer = ModuleLayer.boot();
//
//
//            Path path = Paths.get("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab4\\source\\processors\\out\\production\\processors");
//
//
//            Class<?> loadedToUpperClass =customLayer.findLoader("processors").loadClass("pl.edu.pwr.lib.ToUpperCase"),
//             loadedStatusListener =  customLayer.findLoader("processors").loadClass("pl.edu.pwr.lib.SomeStatusListener"),
//            loadedStatusListenerInterface = customLayer.findLoader("processors").loadClass("pl.edu.pwr.processing.StatusListener");
//
//            Object classInstance = loadedToUpperClass.getConstructor().newInstance();
//
//
//            Method getInfoMethod = loadedToUpperClass.getDeclaredMethod("getInfo");
//            System.out.println((String) getInfoMethod.invoke(classInstance));
//            Method submitTaskMethod = loadedToUpperClass.getDeclaredMethod("submitTask", String.class, loadedStatusListenerInterface);
//            boolean b = (boolean) submitTaskMethod.invoke(classInstance, "Tekst na wejście", loadedStatusListener.getConstructor().newInstance());
//            if (b)
//                System.out.println("Processing started correctly");
//            else
//                System.out.println("Processing ended with an error");
//            Method getResultMethod = loadedToUpperClass.getDeclaredMethod("getResult");
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//
//            // uruchom zadanie, które skończy się, gdy result!=null
//            executor.submit(() -> {
//                String result = null;
//                while (true) {
//                    // System.out.println(scheduleFuture.isDone());
//
//                    try {
//                        Thread.sleep(800);
//
//                        // String result = (String) getResultMethod.invoke(o,new Object[] {});
//                        result = (String) getResultMethod.invoke(classInstance);
//                    } catch (InterruptedException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                    if (result != null) {
//                        System.out.println("Result: " + result);
//                        executor.shutdown();
//                        break;
//                    }
//                }
//            });
//
//            System.out.println("main FINISHED");
//        } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
//            e.printStackTrace();
//        }
//    }
    }
}