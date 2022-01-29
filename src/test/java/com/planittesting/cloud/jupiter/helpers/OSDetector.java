package com.planittesting.cloud.jupiter.helpers;

public class OSDetector {

    public static final String WINDOWS = "windows";
    public static final String LINUX = "linux";
    public static final String MAC = "mac";

    public static String getOSFamily() {

        String rawOS = System.getProperty("os.name").toLowerCase();

        if (rawOS.contains("window")) {
            return WINDOWS;
        } else if (rawOS.contains("linux")) {
            return LINUX;
        } else if (rawOS.contains("mac")) {
            return MAC;
        }

        throw new RuntimeException("Unable to determine OS family from " + rawOS);
    }

    public static boolean isWindows() {
        return WINDOWS.equals(getOSFamily());
    }
}
