package org.hxzzz.calendar;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Accountings accountings = new Accountings();
        GUI gui = new GUI(accountings);
    }
}