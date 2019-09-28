package io.github.wsz82;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoaderSeedTest {
    private static String testDir;

    @BeforeAll
    static void init() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL pathURL = classLoader.getResource("files");
        testDir = Paths.get(pathURL.toURI()).toAbsolutePath().toString();
    }

    @Test
    void analyserIsReturnedFromBINfile() {
        LoaderSeed loaderSeed = new LoaderSeed();
        assertNotNull(loaderSeed.load(testDir + File.separator + "input.bin"));
    }
}