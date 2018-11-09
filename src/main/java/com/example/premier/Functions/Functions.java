package com.example.premier.Functions;

import java.util.concurrent.atomic.AtomicInteger;

public class Functions {

    private static AtomicInteger idCounter = new AtomicInteger();
    public static int genererUnId()
    {
        return idCounter.getAndIncrement();
    }

}
