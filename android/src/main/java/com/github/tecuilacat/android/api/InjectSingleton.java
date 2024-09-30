package com.github.tecuilacat.android.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectSingleton { }