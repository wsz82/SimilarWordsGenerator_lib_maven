package io.github.wsz82;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*  Default parameters:
    sorted = true;
    firstCharAsInInput = true;
    lastCharAsInInput = true;
    compressed = false;
    numberOfWords = 10;
    minWordLength = 0;  //number 0 is a flag for default word length
    maxWordLength = 0;  //number 0 is a flag for default word length
    levelOfCompression = 0; //number 0 is a flag for non-compression
*/
class GeneratorTest {
    private final List<String> input = Arrays.asList("John", "Nancy", "Stacy");
    private final Seed seed = new Seed(input);
    private final WordsGenerator generator = new StandardWordsGenerator(seed);

    @Test
    void maximumWordsAreGenerated() {
        GenerateParameters parameters = new GenerateParameters.Builder().build();
        Set<String> output = generator.generate(parameters);
        assertEquals(5, output.size());
    }

    @Test
    void outputIsSorted() {
        GenerateParameters parameters = new GenerateParameters.Builder().build();
        Set<String> output = generator.generate(parameters);
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
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setSorted(false)
                .build();
        Set<String> output = generator.generate(parameters);
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
        GenerateParameters parameters = new GenerateParameters.Builder().build();
        Set<String> output = generator.generate(parameters);
        for (String word : output) {
            assertTrue(seed.getFirstChars().contains(word.charAt(0)));
        }
    }

    @Test
    void ifExistsWordWithFirstCharNotExistingInListOfFirstChars() {
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setFirstCharAsInInput(false)
                .build();
        Set<String> output = generator.generate(parameters);
        boolean ifExistsWordWithFirstCharNotExistingInListOfFirstChars = false;

        for (String word : output) {
            if (!seed.getFirstChars().contains(word.charAt(0))) {
                ifExistsWordWithFirstCharNotExistingInListOfFirstChars = true;
                break;
            }
        }
        assertTrue(ifExistsWordWithFirstCharNotExistingInListOfFirstChars);
    }

    @Test
    void lastCharIsAsInInput() {
        GenerateParameters parameters = new GenerateParameters.Builder().build();
        Set<String> output = generator.generate(parameters);
        for (String word : output) {
            assertTrue(seed.getLastChars().contains(word.charAt(word.length() - 1)));
        }
    }

    @Test
    void ifExistsWordWithLastCharNotExistingInListOfLastChars() {
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setLastCharAsInInput(false)
                .build();
        Set<String> output = generator.generate(parameters);
        boolean ifExistsWordWithLastCharNotExistingInListOfLastChars = false;

        for (String word : output) {
            if (!seed.getLastChars().contains(word.charAt(0))) {
                ifExistsWordWithLastCharNotExistingInListOfLastChars = true;
                break;
            }
        }
        assertTrue(ifExistsWordWithLastCharNotExistingInListOfLastChars);
    }

    @Test
    void wordsNotShorterThanMinWordLengthAreGenerated() {
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setMinWordLength(5)
                .build();
        Set<String> output = generator.generate(parameters);

        for (String word : output) {
            if (word.length() < parameters.getMinWordLength()) {
                fail();
            }
        }
    }

    @Test
    void wordsNotLongerThanMaxWordLengthAreGenerated() {
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setMaxWordLength(4)
                .build();
        Set<String> output = generator.generate(parameters);

        for (String word : output) {
            if (word.length() > parameters.getMaxWordLength()) {
                fail();
            }
        }
    }

    @Test
    void twoWordsAreGenerated() {
        int numberOfWords = 2;
        GenerateParameters parameters = new GenerateParameters.Builder()
                .setNumberOfWords(numberOfWords)
                .build();
        Set<String> output = generator.generate(parameters);
        assertEquals(numberOfWords, output.size());
    }
}
