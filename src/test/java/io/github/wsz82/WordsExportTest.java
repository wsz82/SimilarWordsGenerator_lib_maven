package io.github.wsz82;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordsExportTest {
    private static String testDir;

    @BeforeAll
    static void init() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL pathURL = classLoader.getResource("files");
        testDir = Paths.get(pathURL.toURI()).toAbsolutePath().toString();
    }

    @Test
    void fileContainsOutputAfterExporting() {
        Generator generator = new Generator();
        WordsExport wordsExport = new WordsExport();
        ProgramParameters.Builder parametersBuilder = new ProgramParameters.Builder();
        List<String> input = Arrays.asList("John", "Nancy", "Stacy");
        parametersBuilder.setInput(input);
        ProgramParameters parameters = parametersBuilder.build();
        Set<String> output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
        wordsExport.export(new ArrayList<>(output), testDir + File.separator + "exported.txt");

        Set<String> loaded = new HashSet<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(testDir + File.separator + "exported.txt"), StandardCharsets.UTF_8))
        ){
            String temp;

            br.mark(3);
            if (br.read() != '\ufeff') br.reset();
            while ( (temp = br.readLine()) != null ) {
                if (!temp.equals("")) loaded.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(output, loaded);
    }
}