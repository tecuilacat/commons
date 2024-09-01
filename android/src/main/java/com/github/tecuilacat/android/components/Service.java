package com.github.tecuilacat.android.components;

import android.content.Context;

/**
 * Service that has one specific job
 * @implNote Usage like the following<br>
 *      <code>TestService testService = new TestService(this);</code>
 */
public abstract class Service {
    protected final Context context;

    /**
     * Make this constructor public in the child class
     * @param context Context of the activity or subservice
     */
    protected Service(Context context) {
        this.context = context;
        init();
    }

    /**
     * Initializes the service and every service that is used within this service
     */
    protected abstract void init();

}
