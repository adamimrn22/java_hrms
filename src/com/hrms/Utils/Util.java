package com.hrms.Utils;

import java.io.File;
import java.io.IOException;

public interface Util {
    public static void clearMenu() {
        System.out.print("\033\143");
    }

    public static void deleteAndReplaceFile(File sourceFile, File destinationFile) {
        try {
            if (destinationFile.exists()) {
                if (!destinationFile.delete()) {
                    throw new IOException("Unable to delete existing file");
                }
            }

            if (!sourceFile.renameTo(destinationFile)) {
                throw new IOException("Unable to rename file");
            }

            System.out.println("file updated successfully.");

        } catch (IOException e) {
            System.err.println("Error replacing file: " + e.getMessage());
        }
    }

}
