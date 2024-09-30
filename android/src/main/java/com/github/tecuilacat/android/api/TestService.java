package com.github.tecuilacat.android.api;

@Singleton
public class TestService {

    private int i = 0;

    public void printAndInc() {
        i++;
        System.out.println(i);
    }

}
