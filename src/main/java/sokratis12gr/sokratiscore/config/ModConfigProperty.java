package sokratis12gr.sokratiscore.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sokratis12gr.sokratiscore.config
 * SokratisCore created by sokratis12GR on 7/26/2016 2:13 PM.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModConfigProperty {
    public String name();

    public String category();

    public String comment() default "";
}