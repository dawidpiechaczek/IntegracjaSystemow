package com.example.dawid.visitwroclove.dagger;

/**
 * Created by Dawid on 23.07.2017.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface NonConfigurationScope {
}
