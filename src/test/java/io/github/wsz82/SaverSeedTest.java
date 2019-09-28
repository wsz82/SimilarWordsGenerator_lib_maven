package io.github.wsz82;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaverSeedTest {
    private static String testDir;

    @BeforeAll
    static void init() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL pathURL = classLoader.getResource("files");
        testDir = Paths.get(pathURL.toURI()).toAbsolutePath().toString();
    }

    @Test
    void analyserAreTheSameBeforeAndAfterSavingSeed() {
        List<String> input = Arrays.asList("John", "Nancy", "Stacy");
        Analyser analyser = new Analyser();
        SaverSeed saverSeed = new SaverSeed();
        LoaderSeed loaderSeed = new LoaderSeed();
        analyser.analyze(input);
        saverSeed.save(analyser, testDir + File.separator + "seed.bin");
        Analyser loadedAnalyser = loaderSeed.load(testDir + File.separator + "seed.bin");
        assertEquals(analyser.getHashOfInput(), loadedAnalyser.getHashOfInput());
    }
}