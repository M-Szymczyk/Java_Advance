package pl.edu.pwr.classloader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import pl.edu.pwr.loader.CustomClassLoader;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.*;
import java.util.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to store class reference and class loader
 */
class LoadedClassPackage {
    Class<?> classReference;
    ModuleLayer moduleLayerReference;

    public LoadedClassPackage(Class<?> classRef, ModuleLayer moduleLayer) {
        this.classReference = classRef;
        this.moduleLayerReference = moduleLayer;
    }
}

public class HelloController{
    Path chosenPath;
    String ModuleName = "processors";
    Set<String> rootModules = Set.of(ModuleName);
    Map<String, LoadedClassPackage> loadedClass = new HashMap<>();
    @FXML
    public Button chooseFirBtn;
    @FXML
    ListView<String> PossibleToLoadList, LoadClassList;

    /**
     * Method calls for directory chooser
     */
    @FXML
    protected void chooseDir() {
        chosenPath = new DirectoryChooser().showDialog(null).toPath();
        loadFiles();
    }

    /**
     * Method add list of files to ListView
     */
    private void loadFiles() {
        ObservableList<String> items = FXCollections.observableArrayList(
                listFilesUsingJavaIO());
        PossibleToLoadList.setItems(items);
    }

    /**
     * Method searches for class in directory
     * @return list of files
     */
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

    /**
     * Method invoke method submit task, with loaded Status listener, when processing fishing display result
     * @param loadedClassPackage reference to class and, module layer
     */
    private void invokeSubmittal(LoadedClassPackage loadedClassPackage) {
        try {
            Class<?> loadedStatusListener = loadedClassPackage.moduleLayerReference.findLoader(ModuleName).loadClass("pl.edu.pwr.processing.SomeStatusListener"),
                    loadedStatusListenerInterface = loadedClassPackage.moduleLayerReference.findLoader(ModuleName).loadClass("pl.edu.pwr.processing.StatusListener");
            Method submitTaskMethod = loadedClassPackage.classReference.getMethod("submitTask", String.class, loadedStatusListenerInterface);
            Object classInstance = loadedClassPackage.classReference.getConstructor().newInstance();
            boolean b = (boolean) submitTaskMethod.invoke(classInstance, "Tekst na wejście", loadedStatusListener.getConstructor().newInstance());
            if (b)
                System.out.println("Processing started correctly");
            else
                System.out.println("Processing ended with an error");
            Method getResultMethod = loadedClassPackage.classReference.getDeclaredMethod("getResult");
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.submit(() -> {
                String result = null;
                while (true) {
                    try {
                        Thread.sleep(800);
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

    /**
     * Method create Module layer
     * @param modulePath path to module
     * @param rootModules set of modules to load
     * @return ModuleLayer for given class
     * @throws IllegalAccessException
     */
    public static ModuleLayer createLayer(String modulePath, Set<String> rootModules) throws IllegalAccessException {
        Path path = Paths.get(modulePath);

        ModuleFinder beforeFinder = ModuleFinder.of(path);
        ModuleFinder afterFinder = ModuleFinder.of();

        Configuration parentConfig = ModuleLayer.boot().configuration();
        Configuration config =
                parentConfig.resolve(beforeFinder, afterFinder, rootModules);

        ClassLoader sysClassLoader = new CustomClassLoader(path);
        ModuleLayer parentLayer = ModuleLayer.boot();
        ModuleLayer layer = parentLayer.defineModulesWithOneLoader(config, sysClassLoader);

        if (layer.modules().isEmpty()) {
            System.out.println("\nCould not find the module " + rootModules
                    + " at " + modulePath + ". "
                    + "\n");
        }
        return layer;
    }

    /**
     * Method load class and move item from list PossibleToLoad to loadClassList
     */
    public void loadClass() {
        String className = PossibleToLoadList.getSelectionModel().getSelectedItems().get(0);
        ObservableList<String> list = PossibleToLoadList.getItems();
        list.remove(className);
        PossibleToLoadList.setItems(list);
        try {

            ModuleLayer customLayer = createLayer(chosenPath.toString(), rootModules);

            Class<?> loadedToUpperClass = customLayer.findLoader(ModuleName).loadClass("pl.edu.pwr.lib." + className.replace(".class", ""));
            Object classInstance = loadedToUpperClass.getConstructor().newInstance();
            loadedClass.put(className,new LoadedClassPackage(loadedToUpperClass,customLayer) );
            Method getInfoMethod = loadedToUpperClass.getDeclaredMethod("getInfo");
            className += " | " + getInfoMethod.invoke(classInstance);
            ObservableList<String> list1 = LoadClassList.getItems();
            list1.add(className);
            LoadClassList.setItems(list1);
        } catch (IllegalAccessException | ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is listener for LoadClassList
     * if item is clicked with right mouse button, then selected class is unloaded and move to PossibleToLoadList
     * if item is clicked with left mouse button,then on selected class method invokeSubmittal is called
     *
     * @param mouseEvent require to check button
     */
    public void runClass(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            String chosen = LoadClassList.getSelectionModel().getSelectedItems().get(0);
            invokeSubmittal(loadedClass.get(chosen.split(" | ")[0]));

        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            String chosen = LoadClassList.getSelectionModel().getSelectedItems().get(0);
            ObservableList<String> list = LoadClassList.getItems();
            list.remove(chosen);
            LoadClassList.setItems(list);
            loadedClass.remove(chosen.split(" | ")[0]);
            ObservableList<String> list1 = PossibleToLoadList.getItems();
            list1.add(chosen.split(" | ")[0]);
            PossibleToLoadList.setItems(list1);
        }
    }
}