package Predator.System.Prototype;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JasperFertilizer {
    boolean value() default true;
}
