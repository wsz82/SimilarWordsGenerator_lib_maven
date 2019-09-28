package io.github.wsz82;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    private static ProgramParameters.Builder parametersBuilder;
    private static ProgramParameters parameters;
    private static Generator generator;
    private static String testDir;

/*  default parameters:

    analyser = null;
    input = Collections.emptyList();
    path = null;
    sorted = true;
    firstCharAsInInput = true;
    lastCharAsInInput = true;
    compressed = false;
    numberOfWords = 10;
    minWordLength = 0;  //number 0 is a flag for default word length
    maxWordLength = 0;  //number 0 is a flag for default word length
    levelOfCompression = 0; //number 0 is a flag for non-compression*/

    @BeforeAll
    static void init() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL pathURL = classLoader.getResource("files");
        testDir = Paths.get(pathURL.toURI()).toAbsolutePath().toString();
    }

    @BeforeEach
    void setParameters() {
        parametersBuilder = new ProgramParameters.Builder();
        generator = new Generator();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenInputAndPathAreNull() {
        parameters = parametersBuilder.build();
        assertThrows(NullPointerException.class, () -> new Generator().generate(parameters, Controller.GenerateSource.NEW_ANALYSER));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenAnalyserIsNull() {
        parameters = parametersBuilder.build();
        assertThrows(NullPointerException.class, () -> new Generator().generate(parameters, Controller.GenerateSource.CURRENT_ANALYSER));
    }

    @Nested
    class InputTests {
        List<String> input = Arrays.asList("John", "Nancy", "Stacy");
        Set<String> output;

        @BeforeEach
        void provideInput() {
            parametersBuilder.setInput(input);
            parameters = parametersBuilder.build();
        }

        @Test
        void maximumWordsAreGenerated() {
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            assertEquals(5, output.size());
        }

        @Test
        void outputIsSorted() {
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            String prevWord = "";

            for (String word : output) {
                if (word.compareTo(prevWord) < 0) {
                    fail();
                }
                prevWord = word;
            }
            assertTrue(true);
        }

        @Test
        void outputIsNotSorted() {
            parametersBuilder.setSorted(false);
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            String prevWord = "";
            boolean isNotSorted = false;

            for (String word : output) {
                if (isNotSorted) {
                    break;
                }
                if (word.compareTo(prevWord) < 0) {
                    isNotSorted = true;
                }
                prevWord = word;
            }
            assertTrue(isNotSorted);
        }

        @Test
        void firstCharIsAsInInput() {
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            for (String word : output) {
                assertTrue(generator.getAnalyser().getFirstChars().contains(word.charAt(0)));
            }
        }

        @Test
        void ifExistsWordWithFirstCharNotExistingInListOfFirstChars() {
            parametersBuilder.setFirstCharAsInInput(false);
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            boolean ifExistsWordWithFirstCharNotExistingInListOfFirstChars = false;

            for (String word : output) {
                if (!generator.getAnalyser().getFirstChars().contains(word.charAt(0))) {
                    ifExistsWordWithFirstCharNotExistingInListOfFirstChars = true;
                    break;
                }
            }
            assertTrue(ifExistsWordWithFirstCharNotExistingInListOfFirstChars);
        }

        @Test
        void lastCharIsAsInInput() {
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            for (String word : output) {
                assertTrue(generator.getAnalyser().getLastChars().contains(word.charAt(word.length() - 1)));
            }
        }

        @Test
        void ifExistsWordWithLastCharNotExistingInListOfLastChars() {
            parametersBuilder.setLastCharAsInInput(false);
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            boolean ifExistsWordWithLastCharNotExistingInListOfLastChars = false;

            for (String word : output) {
                if (!generator.getAnalyser().getLastChars().contains(word.charAt(0))) {
                    ifExistsWordWithLastCharNotExistingInListOfLastChars = true;
                    break;
                }
            }
            assertTrue(ifExistsWordWithLastCharNotExistingInListOfLastChars);
        }

        @Test
        void wordsNotShorterThanMinWordLengthAreGenerated() {
            parametersBuilder.setMinWordLength(5);
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);

            for (String word : output) {
                if (word.length() < parameters.getMinWordLength()) {
                    fail();
                }
            }
        }

        @Test
        void wordsNotLongerThanMaxWordLengthAreGenerated() {
            parametersBuilder.setMaxWordLength(4);
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);

            for (String word : output) {
                if (word.length() > parameters.getMaxWordLength()) {
                    fail();
                }
            }
        }

        @Test
        void outputFromTXTfileIsGenerated() {
            parametersBuilder.setInput(Collections.emptyList());
            parametersBuilder.setPath(testDir + File.separator + "input.txt");
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            assertEquals(5, output.size());
        }

        @Test
        void outputFromSeedFileIsGenerated() {
            parametersBuilder.setInput(Collections.emptyList());
            SaverSeed saverSeed = new SaverSeed();
            Analyser analyser = new Analyser();
            analyser.analyze(input);
            saverSeed.save(analyser, testDir + File.separator + "input.bin");
            parametersBuilder.setPath(testDir + File.separator + "input.bin");
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            assertEquals(5, output.size());
        }

        @Test
        void outputFromCurrentAnalyserIsGenerated() {
            parameters = parametersBuilder.build();
            output = generator.generate(parameters, Controller.GenerateSource.NEW_ANALYSER);
            parametersBuilder.setInput(Collections.emptyList());
            output = generator.generate(parameters, Controller.GenerateSource.CURRENT_ANALYSER);
            assertEquals(5, output.size());
        }
    }
}
