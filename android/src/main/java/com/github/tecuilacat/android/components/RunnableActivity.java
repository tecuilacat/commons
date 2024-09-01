package com.github.tecuilacat.android.components;

public interface RunnableActivity {

    void initViewComponents();

    void initServices();

    default void initFunctionalities() {
        // ignore if there are no functionalities
    }

    default void initSources() {
        // ignore if the activity does not have any sources
    }

}
