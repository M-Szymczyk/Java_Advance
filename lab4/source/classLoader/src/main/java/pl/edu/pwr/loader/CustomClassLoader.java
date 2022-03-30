package pl.edu.pwr.loader;

import java.io.IOException;
import java.nio.file.*;


public class CustomClassLoader extends ClassLoader {
    private final Path searchPath;

    public CustomClassLoader(Path path) throws IllegalAccessException {
        if (!Files.isDirectory(path)) throw new IllegalAccessException("Path must be a directory");
        searchPath = path;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Path classfile = Paths.get(searchPath + FileSystems.getDefault().getSeparator() +
                name.replace(".", FileSystems.getDefault().getSeparator()) + ".class");
        byte[] buf;
        try {
            buf = Files.readAllBytes(classfile);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error in defining " + name + " in " + searchPath, e);
        }
        return defineClass(name, buf, 0, buf.length);
    }
}
