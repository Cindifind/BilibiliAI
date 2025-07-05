package online;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Random {
    public static String getRandomSongPath(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a valid directory.");
        }

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".WAV"));
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No image files found in the directory.");
        }

        List<File> imageFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                imageFiles.add(file);
            }
        }
        if (imageFiles.isEmpty()) {
            throw new IllegalArgumentException("No image files found in the directory.");
        }

        java.util.Random random = new java.util.Random();
        File randomFile = imageFiles.get(random.nextInt(imageFiles.size()));
        return randomFile.getAbsolutePath();
    }
}
