package zagretdinov.implementor;

import advanced.implementor.Impler;
import advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Implementor implements Impler {
    private static final String TAB = "\t";
    private final String SPACE = " ";
    private final String LINE_SEP = System.lineSeparator();
    private final String COLLECTION_SEPARATOR = ", ";
    private final String OPER_SEP = ";";
    private final String BLOCK_BEGIN = "{";
    private final String BLOCK_END = "}";
    private final String BRACKET_OPEN = "(";
    private final String BRACKET_END = ")";

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("Invalid arguments number, expected <class.name> <output.path>");
        } else {
            for (String arg : args) {
                if (arg == null) {
                    System.err.println("All arguments should be not null");
                    return;
                }
            }
            try {
                new Implementor().implement(Class.forName(args[0]), Path.of(args[1]));
            } catch (ClassNotFoundException e) {
                System.err.printf("Invalid class name given: %s\n", e.getMessage());
            } catch (InvalidPathException e) {
                System.err.printf("Invalid path given: %s\n", e.getMessage());
            } catch (ImplerException e) {
                System.err.printf("Error while creating %s file: %s\n" + "java" ,
                        e.getMessage());
            }
        }
    }

    private <T> String elementsToString(String delimiter, T[] elements, Function<T, String> function) {
        return Arrays.stream(elements).map(function).collect(Collectors.joining(delimiter));
    }

    private <T> String elementsInCollectionToString(T[] items, Function<T, String> transform) {
        return elementsToString(COLLECTION_SEPARATOR, items, transform);
    }

    private String elementsInBrackets(String... parts) {
        return elementsLineSeparated(BRACKET_OPEN, String.join(SPACE, parts), BRACKET_END);
    }

    private String elementsSpaced(String... parts) {
        return String.join(SPACE, parts);
    }

    private String elementsLineSeparated(String... blocks) {
        return String.join(LINE_SEP, blocks) + LINE_SEP;
    }

    private String Tabs(int cnt) {
        return TAB.repeat(Math.max(0, cnt));
    }

    private String emptyOrPrefix(String prefix, String items) {
        if (!"".equals(items)) {
            return elementsSpaced(prefix, items);
        }
        return "";
    }

    private String getPackage(Class<?> token) {
        return emptyOrPrefix("package", token.getPackageName()) + OPER_SEP;
    }

    private String getFilePath(Class<?> token) {
        return token.getPackageName().replace('.', File.separatorChar);
    }

    private String getClassName(Class<?> token) {
        return token.getSimpleName() + "Impl";
    }

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        Path place;
        try {
            place = Path.of(root.toString(), getFilePath(token));
            Files.createDirectories(place);
        } catch (InvalidPathException e) {
            throw new ImplerException("Wrong path");
        } catch (IOException e) {
            throw new ImplerException("Could not create output directories");
        }
        if (token.isPrimitive() || token.isArray() ||
                Modifier.isFinal(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Unsupported token given");
        }
        String extendsOrImplements = token.isInterface() ? "implements" : "extends";
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
                Path.of(place.toString(), getClassName(token) + ".java"))) {
            bufferedWriter.write(elementsLineSeparated(getPackage(token)
                    , elementsSpaced(getClassModifiers(token),
                    "class", getClassName(token), extendsOrImplements,
                    token.getCanonicalName(), BLOCK_BEGIN)));
            allWork(token, bufferedWriter);
            bufferedWriter.write(BLOCK_END);
        } catch (IOException e) {
            throw new ImplerException("Error with writing class code");
        }
    }

    private String getDefaultValue(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return "null";
        } else if (clazz.equals(void.class)) {
            return "";
        } else if (clazz.equals(boolean.class)) {
            return "false";
        } else {
            return "0";
        }
    }

    private void methodWalker(Set<Integer> methodsHashed, Method[] methods, int modifier1, int modifier2,
                              BufferedWriter bufferedWriter) {
        Arrays.stream(methods).forEach(method -> {
            StringBuilder hashing = new StringBuilder();
            hashing.append(method.getReturnType().toString());
            for (Class<?> m : method.getParameterTypes()) {
                hashing.append(m.getCanonicalName());
            }
            int hash = hashing.hashCode();
            if (methodsHashed.add(hash)) {
                if ((method.getModifiers() & modifier1) != 0
                        && (method.getModifiers() & modifier2) != 0) {
                    methodWalk(method, bufferedWriter);
                }
            }
        });
    }

    private class Indices {
        int index = 1;
        Integer add() {
            return index++;
        }
    }

    private void allWork(Class<?> token, BufferedWriter bufferedWriter) throws ImplerException {
        if (!token.isInterface()) {
            List<Constructor<?>> constructors = Arrays.stream(token.getDeclaredConstructors())
                    .filter(c -> !Modifier.isPrivate(c.getModifiers()))
                    .collect(Collectors.toList());
            if (constructors.isEmpty()) {
                throw new ImplerException("Class with no non-private constructors can not be extended");
            }
            Arrays.stream(token.getDeclaredConstructors()).forEach(constructor -> {
                try {
                    bufferedWriter.write(getMethodBody(constructor, token));
                } catch (IOException e) {
                    System.out.println("Error happened while writing constructors code");
                }
            });
        }

        Set<Integer> methodsHashed = new HashSet<>();
        methodWalker(methodsHashed, token.getMethods(), ~Modifier.STATIC, Modifier.ABSTRACT, bufferedWriter);
        Class cur = token;
        while (cur != null) {
            if (Modifier.isPrivate(cur.getModifiers())) {
                throw new ImplerException("Private class in hierarchy");
            }
            methodWalker(methodsHashed, cur.getDeclaredMethods(),
                    Modifier.ABSTRACT, Modifier.PROTECTED, bufferedWriter);
            cur = cur.getSuperclass();
        }
    }

    private void methodWalk(Method method, BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write(getMethodBody(method));
        } catch (IOException e) {
            System.out.println("Error happened while writing methods code");
        }
    }

    private String getMethodBody(Method method) {
        return elementsSpaced(elementsLineSeparated(Tabs(1) + "@Override",
                Tabs(1) + getMethodModifiers(method),
                method.getReturnType().getCanonicalName(), method.getName(),
                elementsInBrackets(getParameters(method.getParameterTypes())),
                        getThrowable(method.getExceptionTypes()),
                        BLOCK_BEGIN, Tabs(2) + "return",
                        getDefaultValue(method.getReturnType()) +
                        OPER_SEP, Tabs(1) + BLOCK_END));
    }

    private String getMethodBody(Constructor constructor, Class<?> token) {
        return elementsSpaced(elementsLineSeparated(Tabs(1) + getClassName(token) +
                elementsInBrackets(getParameters(constructor.getParameterTypes())),
                getThrowable(constructor.getExceptionTypes()) +
                BLOCK_BEGIN, Tabs(2)  + "super" +
                        elementsInBrackets(getParametersNumbers(constructor.getParameterTypes()))
                        + OPER_SEP, BLOCK_END));
    }

    private String getThrowable(Class[] exceptionTypes) {
        return exceptionTypes.length == 0 ? "" : "throws " +
                elementsToString(COLLECTION_SEPARATOR, exceptionTypes, Class::getName);
    }

    private String getParameters(Class[] parameterTypes) {
        Indices indices = new Indices();
        return parameterTypes.length == 0 ? "" :
                elementsInCollectionToString(parameterTypes,
                        parameter -> elementsSpaced(parameter.getCanonicalName(), "_" + indices.add()));
    }

    private String getParametersNumbers(Class[] parameterTypes) {
        Indices indices = new Indices();
        return parameterTypes.length == 0 ? "" :
                elementsInCollectionToString(parameterTypes, parameter -> elementsSpaced("_" + indices.add()));
    }

    private String getClassModifiers(Class<?> token) {
        return Modifier.toString(token.getModifiers() & ~Modifier.ABSTRACT &
                ~Modifier.INTERFACE & ~Modifier.STATIC & ~Modifier.PROTECTED);
    }

    private String getMethodModifiers(Method m) {
        return Modifier.toString(m.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.TRANSIENT);
    }
}

