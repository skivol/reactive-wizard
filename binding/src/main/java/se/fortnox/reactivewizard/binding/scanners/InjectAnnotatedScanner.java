package se.fortnox.reactivewizard.binding.scanners;

import javax.inject.Singleton;
import java.util.stream.Stream;

/**
 * Finds all classes with @Inject annotated constructors.
 */
@Singleton
public class InjectAnnotatedScanner extends AbstractClassScanner {
    @Override
    public void visit(ClassScanner classScanner) {
        classScanner.findClassesWithMethodAnnotation(javax.inject.Inject.class).forEach(this::classFound);
        classScanner.findClassesWithMethodAnnotation(com.google.inject.Inject.class).forEach(this::classFound);
    }

    private void classFound(Class cls) {
        if (Stream.of(cls.getConstructors())
                .anyMatch(constructor -> constructor.isAnnotationPresent(javax.inject.Inject.class)
                        || constructor.isAnnotationPresent(com.google.inject.Inject.class))) {
            add(cls);
        }
    }
}
