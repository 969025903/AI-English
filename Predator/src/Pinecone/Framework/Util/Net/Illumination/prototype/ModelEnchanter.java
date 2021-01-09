package Pinecone.Framework.Util.Net.Illumination.prototype;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelEnchanter {
    boolean value() default true;
}
