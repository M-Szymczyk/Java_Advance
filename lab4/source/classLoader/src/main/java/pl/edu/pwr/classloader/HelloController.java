package pl.edu.pwr.classloader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import pl.edu.pwr.loader.CustomClassLoader;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloController implements Initializable {
    @FXML
    public Button chooseFirBtn;
    Path chosenPath = Paths.get("C:\\Users\\Hyperbook\\Desktop\\JavaZaw\\mszymczyk_248881_java\\lab4\\source\\processors\\out\\production\\processors");
    @FXML
    ListView<String> PossibleToLoadList;

    @FXML
    protected void chooseDir() {
        chosenPath = new DirectoryChooser().showDialog(null).toPath();
        load();
    }

    private void loadFiles() {
        ObservableList<String> items = FXCollections.observableArrayList(
                listFilesUsingJavaIO());
        PossibleToLoadList.setItems(items);
    }

    public List<String> listFilesUsingJavaIO() {

        try (Stream<Path> stream = Files.walk(chosenPath)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().contains("lib"))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void load() {
        try {
            // Define the set of root modules to be resolved in the custom layer
            String ModuleName = "processors";
            Set<String> rootModules = Set.of(ModuleName);
            // Create a custom layer
            ModuleLayer customLayer = createLayer(chosenPath.toString(), rootModules);


            Class<?> loadedToUpperClass = customLayer.findLoader(ModuleName).loadClass("pl.edu.pwr.lib.ToUpperCase"),
                    loadedStatusListener = customLayer.findLoader(ModuleName).loadClass("pl.edu.pwr.lib.SomeStatusListener"),
                    loadedStatusListenerInterface = customLayer.findLoader(ModuleName).loadClass("pl.edu.pwr.processing.StatusListener");

            Object classInstance = loadedToUpperClass.getConstructor().newInstance();


            Method getInfoMethod = loadedToUpperClass.getDeclaredMethod("getInfo");
            System.out.println((String) getInfoMethod.invoke(classInstance));
            Method submitTaskMethod = loadedToUpperClass.getDeclaredMethod("submitTask", String.class, loadedStatusListenerInterface);
            boolean b = (boolean) submitTaskMethod.invoke(classInstance, "Tekst na wejście", loadedStatusListener.getConstructor().newInstance());
            if (b)
                System.out.println("Processing started correctly");
            else
                System.out.println("Processing ended with an error");
            Method getResultMethod = loadedToUpperClass.getDeclaredMethod("getResult");
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

    public static ModuleLayer createLayer(String modulePath, Set<String> rootModules) throws IllegalAccessException {
        Path path = Paths.get(modulePath);

        // Define the module finders to be used in creating a
        // configuration for the custom layer
        ModuleFinder beforeFinder = ModuleFinder.of(path);
        ModuleFinder afterFinder = ModuleFinder.of();

        // Create a configuration for the custom layer
        Configuration parentConfig = ModuleLayer.boot().configuration();
        Configuration config =
                parentConfig.resolve(beforeFinder, afterFinder, rootModules);

        /* Create a custom layer with one class loader. The parent for
           the class loader is the system class loader. The boot layer is
           the parent layer of this custom layer.
         */
        ClassLoader sysClassLoader = new CustomClassLoader(path);
        ModuleLayer parentLayer = ModuleLayer.boot();
        ModuleLayer layer = parentLayer.defineModulesWithOneLoader(config, sysClassLoader);

        // Check if we loaded the module in this layer
        if (layer.modules().isEmpty()) {
            System.out.println("\nCould not find the module " + rootModules
                    + " at " + modulePath + ". "
                    + "\n");
        }
        return layer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFiles();
    }
}