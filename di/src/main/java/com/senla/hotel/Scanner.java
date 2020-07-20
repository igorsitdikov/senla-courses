package com.senla.hotel;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner {
    private static final String CLASS_EXTENSION = ".class";
    private static final String CLASS_POSTFIX = "Impl";
    private static final String DOT = ".";
    private static final String SLASH = "/";
    private Set<Class<?>> allClasses = new HashSet<>();
    private Map<Class<?>, Set<Class<?>>> infsToimpls;
    private Set<Class<?>> interfaces;
    private Set<Class<?>> classes;

    public Scanner(final String packageName) {
        try {
            this.allClasses = getClasses(packageName);
        } catch (final IOException e) {
            System.err.println("General I/O exception: " + e.getMessage());
        }
        this.interfaces = findInterfaces();
        this.classes = findClasses();
        this.infsToimpls = findInterfacesAndTheirImpl(classes);
    }

    public Set<Class<?>> findInterfaces() {
        return this.allClasses.stream().filter(Class::isInterface).collect(Collectors.toSet());
    }

    public Set<Class<?>> findClasses() {
        return this.allClasses.stream().filter(clazz -> !clazz.isInterface()).collect(Collectors.toSet());
    }

    private Map<Class<?>, Set<Class<?>>> findInterfacesAndTheirImpl(final Set<Class<?>> classes) {
        final Map<Class<?>, Set<Class<?>>> inf2impls = new LinkedHashMap<>();
        classes.forEach(aClass -> {
            Arrays.asList(aClass.getInterfaces())
                .forEach(el -> {
                    final Set<Class<?>> impls;
                    if (inf2impls.containsKey(el)) {
                        impls = inf2impls.get(el);
                    } else {
                        impls = new HashSet<>();
                    }
                    impls.add(aClass);
                    inf2impls.put(el, impls);
                });
        });
        return inf2impls;
    }

    public Set<Class<?>> getSubTypesOf(final Class<?> clazz) {
        return this.infsToimpls.get(clazz);
    }

    public Set<Class<?>> getClasses(final String packageName) throws IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String path = packageName.replace(DOT, SLASH);
        final Enumeration<URL> resources = classLoader.getResources(path);
        final List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        final Set<Class<?>> classes = new HashSet<>();
        for (final File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private String replaceSlashToDot(final String str) {
        return str.replaceAll(SLASH, DOT);
    }

    private Set<Class<?>> findClasses(final File directory, final String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        final Path start = Paths.get(directory.getPath());
        try (final Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            classes = stream
                .map(String::valueOf)
                .filter(path -> Files.isRegularFile(Paths.get(path)) &&
                                path.toLowerCase().endsWith(CLASS_EXTENSION) &&
                                replaceSlashToDot(path).contains(packageName))
                .map(clazz -> packageName +
                              replaceSlashToDot(clazz.substring(start.toString().length(),
                                                                clazz.length() - CLASS_EXTENSION.length())))
                .sorted()
                .map(clazz -> {
                    try {
                        return Class.forName(clazz);
                    } catch (final ClassNotFoundException e) {
                        System.err.println("Class was not found");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        } catch (final IOException e) {
            System.err.println("General I/O exception: " + e.getMessage());
        }

        return classes;
    }

    public Map<Class<?>, Class<?>> findInfImpl(final Set<Class<?>> classes,
                                               final Class<? extends Annotation> annotation) {
        final Map<Class<?>, Class<?>> infImpl = new ConcurrentHashMap<>();
        for (final Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                final Class<?>[] interfaces = clazz.getInterfaces();
                for (final Class<?> anInterface : interfaces) {
                    final String interfaceSimpleName = anInterface.getSimpleName();
                    final String clazzSimpleName = clazz.getSimpleName();
                    if ((interfaceSimpleName + CLASS_POSTFIX).equals(clazzSimpleName)) {
                        infImpl.put(anInterface, clazz);
                        break;
                    } else {
                        infImpl.put(clazz, clazz);
                    }
                }
            }
        }
        return infImpl;
    }

    public Map<Class<?>, Class<?>> findInfImplInPackage(final String packageName,
                                                        final Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = null;
        try {
            classes = getClasses(packageName);
        } catch (final IOException e) {
            System.err.println("General I/O exception: " + e.getMessage());
        }
        return findInfImpl(classes, annotation);
    }

    public Set<Class<?>> getAllClasses() {
        return new HashSet<>(allClasses);
    }

    public Map<Class<?>, Set<Class<?>>> getInfsToimpls() {
        return new HashMap<>(infsToimpls);
    }

    public Set<Class<?>> getInterfaces() {
        return new HashSet<>(interfaces);
    }

    public Set<Class<?>> getClasses() {
        return new HashSet<>(classes);
    }
}
