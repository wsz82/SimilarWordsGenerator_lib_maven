package io.github.wsz82;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoaderWordsTest {
    private static LoaderWords loaderWords;
    private static String testDir;

    @BeforeAll
    static void init() throws Exception {
        loaderWords = new LoaderWords();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL pathURL = classLoader.getResource("files");
        testDir = Paths.get(pathURL.toURI()).toAbsolutePath().toString();
    }

    @Test
    void analyserIsReturnedFromTXTfile() {
        assertNotNull(loaderWords.load(testDir + File.separator + "input.txt"));
    }
}