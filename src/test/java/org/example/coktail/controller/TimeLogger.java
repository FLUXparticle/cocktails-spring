package org.example.coktail.controller;

public class TimeLogger {

    private final long startTime = System.currentTimeMillis();

    public void log(String message) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long milli = elapsedTime % 1000;
        elapsedTime /= 1000;
        long second = elapsedTime % 60;
        elapsedTime /= 60;
        long minute = elapsedTime % 60;
        elapsedTime /= 60;
        long hour = elapsedTime;
        System.out.printf("%20s [%02d:%02d:%02d.%03d] %s\n", Thread.currentThread().getName(), hour, minute, second, milli, message);
    }

}
