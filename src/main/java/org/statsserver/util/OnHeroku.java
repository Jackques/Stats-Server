package org.statsserver.util;

public class OnHeroku {
    public static boolean isHeroku() {
        String herokuEnv = System.getenv("DYNO");
        return (herokuEnv != null && !herokuEnv.isEmpty());

//        return true;
    }
}
