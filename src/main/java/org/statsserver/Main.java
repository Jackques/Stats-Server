package org.statsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        System.out.println("Note: Main application class (in this case called Main) should always be at parentfolder of other java files (because Springs starts looking for Java annotations from this folder)");

        SpringApplication.run(Main.class, args);
    }

}