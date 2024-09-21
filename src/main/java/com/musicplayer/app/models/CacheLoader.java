package com.musicplayer.app.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CacheLoader {

    private static final File CACHE_DIRECTORY = new File(System.getProperty("user.home") + "/.cache", "simpleMp_cache");

    public static String loadJsonCache(String cacheFileName) {

        String fileNameCache = String.format("%s/%s", CACHE_DIRECTORY, cacheFileName);
        File fileCache = new File(fileNameCache);

        if(CACHE_DIRECTORY.mkdirs() || !fileCache.exists()) {
            try
            {
                Files.write(Path.of(fileCache.toURI()), "[]".getBytes());
            }catch (IOException e) {
                System.out.println("Could not create cache file: \n " + e.getMessage());
            }
        }

        return fileNameCache;
    }

}
