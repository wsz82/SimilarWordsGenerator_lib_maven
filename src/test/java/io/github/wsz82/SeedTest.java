package io.github.wsz82;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeedTest {
    @Nested
    class TestPersistSeed {
        private final String json = "{\"wordsLengths\":[4,5,5],\"firstChars\":[\"J\",\"N\",\"S\"],\"lastChars\":[\"n\",\"y\",\"y\"],\"charToChars\":{\"a\":[\"c\",\"n\"],\"c\":[\"y\",\"y\"],\"S\":[\"t\"],\"t\":[\"a\"],\"h\":[\"n\"],\"J\":[\"o\"],\"N\":[\"a\"],\"n\":[\"c\"],\"o\":[\"h\"]}}";

        @Test
        void seedIsLoaded() {
            Seed seed = Seed.fromJson(json);
            assertEquals(seed.toJson(), json);
        }

        @Test
        void seedIsSaved() {
            Seed seed = new Seed(Arrays.asList("John", "Nancy", "Stacy"));
            String checkedJson = seed.toJson();
            assertEquals(checkedJson, json);
        }
    }

    @Nested
    class TestEmptyListInSeed {
        private Seed seed;

        @BeforeEach
        void fulfillAnalyser() {
            seed = new Seed(Collections.singletonList(""));
        }

        @Test
        void wordLengthShouldBeEmpty() {
            assertTrue(seed.getWordsLengths().isEmpty());
        }

        @Test
        void firstCharShouldBeEmpty() {
            assertTrue(seed.getFirstChars().isEmpty());
        }

        @Test
        void lastCharShouldBeEmpty() {
            assertTrue(seed.getLastChars().isEmpty());
        }

        @Test
        void charsCountShouldBeEmpty() {
            assertTrue(seed.getCharToChars().isEmpty());
        }
    }

    @Nested
    class TestAnalyserWithOneChar {
        private Seed seed;

        @BeforeEach
        void fulfillAnalyser() {
            seed = new Seed(Collections.singletonList("A"));
        }

        @Test
        void shouldContainOneWordLength() {
            assertEquals(1, seed.getWordsLengths().size());
        }

        @Test
        void wordLengthShouldBe_1() {
            assertTrue(seed.getWordsLengths().contains(1));
        }

        @Test
        void shouldContainOneFirstChar() {
            assertEquals(1, seed.getFirstChars().size());
        }

        @Test
        void firstCharShouldBe_A() {
            assertTrue(seed.getLastChars().contains('A'));
        }

        @Test
        void shouldContainOneLastChar() {
            assertEquals(1, seed.getLastChars().size());
        }

        @Test
        void lastCharShouldBe_A() {
            assertTrue(seed.getLastChars().contains('A'));
        }

        @Test
        void charsCountMapShouldBeEmpty() {
            assertTrue(seed.getCharToChars().isEmpty());
        }

        @Test
        void charInMapShouldBe_A() {
            assertTrue(seed.getLastChars().contains('A'));
        }

        @Test
        void shouldBeEmptyInListInCharsCount() {
            assertTrue(seed.getCharToChars().values().isEmpty());
        }
    }

    @Nested
    class TestSeedWithOneWord {
        private Seed seed;

        @BeforeEach
        void fulfillAnalyser() {
            seed = new Seed(Collections.singletonList("Analyser"));
        }

        @Test
        void wordLengthShouldBe_8() {
            assertTrue(seed.getWordsLengths().contains(8));
        }

        @Test
        void firstCharShouldBe_A() {
            assertTrue(seed.getFirstChars().contains('A'));
        }

        @Test
        void lastCharShouldBe_r() {
            assertTrue(seed.getLastChars().contains('r'));
        }

        @Test
        void shouldContain7charsInMap() {
            assertEquals(7, seed.getCharToChars().size());
        }

        @Test
        void mapShouldContain7Lists() {
            assertEquals(7, seed.getCharToChars().values().size());
        }
    }

    @Nested
    class TestSeedWithMultipleWords {
        private Seed seed;

        @BeforeEach
        void fulfillAnalyser() {
            seed = new Seed(Arrays.asList("John", "Nancy", "Stacy"));
        }

        @Test
        void wordLengthShouldContain4() {
            assertTrue(seed.getWordsLengths().contains(4));
        }

        @Test
        void wordLengthShouldContain5() {
            assertTrue(seed.getWordsLengths().contains(5));
        }

        @Test
        void firstCharShouldContain_J() {
            assertTrue(seed.getFirstChars().contains('J'));
        }

        @Test
        void firstCharShouldContain_N() {
            assertTrue(seed.getFirstChars().contains('N'));
        }

        @Test
        void firstCharShouldContain_S() {
            assertTrue(seed.getFirstChars().contains('S'));
        }

        @Test
        void lastCharShouldContain_n() {
            assertTrue(seed.getLastChars().contains('n'));
        }

        @Test
        void lastCharShouldContain_y() {
            assertTrue(seed.getLastChars().contains('y'));
        }

        @Test
        void shouldContain9charsInMap() {
            assertEquals(9, seed.getCharToChars().size());
        }

        @Test
        void mapShouldContain9Lists() {
            assertEquals(9, seed.getCharToChars().values().size());
        }
    }

    @Nested
    class TestCompress {
        private final Seed basicSeed = new Seed(Arrays.asList("John", "Nancy", "Stacy"));

        @Test
        void wordsLengthsSizeShouldBe_2() {
            SeedCompressor compressor = new SeedCompressor(2);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(2, seed.getWordsLengths().size());
        }

        @Test
        void wordsLengthsSizeShouldBe_3() {
            SeedCompressor compressor = new SeedCompressor(4);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(3, seed.getWordsLengths().size());
        }

        @Test
        void firstCharsSizeShouldBe_2() {
            SeedCompressor compressor = new SeedCompressor(2);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(2, seed.getFirstChars().size());
        }

        @Test
        void firstCharsSizeShouldBe_3() {
            SeedCompressor compressor = new SeedCompressor(4);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(3, seed.getFirstChars().size());
        }

        @Test
        void lastCharsSizeShouldBe_2() {
            SeedCompressor compressor = new SeedCompressor(2);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(2, seed.getLastChars().size());
        }

        @Test
        void lastCharsSizeShouldBe_3() {
            SeedCompressor compressor = new SeedCompressor(4);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(3, seed.getLastChars().size());
        }

        @Test
        void charsCountListsSizeShouldBe_1() {
            SeedCompressor compressor = new SeedCompressor(1);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(1, seed.getCharToChars().get('c').size());
        }

        @Test
        void charsCountListsSizeShouldBe_2() {
            SeedCompressor compressor = new SeedCompressor(3);
            Seed seed = compressor.compress(basicSeed);
            assertEquals(2, seed.getCharToChars().get('c').size());
        }
    }
}
