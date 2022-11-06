package io.github.wsz82;


import java.util.*;

public class SeedCompressor {
    private final int compressionLevel;


    /**
     * @param compressionLevel level to which Seed collections would shrink
     */
    public SeedCompressor(int compressionLevel) {
        if (compressionLevel < 1) {
            throw new IllegalArgumentException("Compression level must be greater than 0 and actual is: " + compressionLevel);
        }
        this.compressionLevel = compressionLevel;
    }

    /**
     * @param source source Seed
     * @return new Seed
     */
    public Seed compress(Seed source) {
        Seed compressed = new Seed();
        compressWordsLengths(source.getWordsLengths(), compressed.getWordsLengths());
        compressCharacters(source.getFirstChars(), compressed.getFirstChars());
        compressCharacters(source.getLastChars(), compressed.getLastChars());
        compressCharsCount(source.getCharToChars(), compressed.getCharToChars());
        return compressed;
    }

    private void compressWordsLengths(List<Integer> source, List<Integer> result) {
        if (source.size() > compressionLevel) {
            Set<Integer> uniqueWordLengths = new HashSet<>(source);

            for (Integer length : uniqueWordLengths) {
                int requiredCount = calculateRequiredCountFrom(source, length);

                for (int j = 0; j < requiredCount; j++) {
                    result.add(length);
                }
            }
            fulfillWordLengthsToCompressionLevel(result, source);
        } else {
            result.addAll(source);
        }

    }

    private void compressCharsCount(Map<Character, List<Character>> source,
                                    Map<Character, List<Character>> result) {
        for (Character key : source.keySet()) {
            List<Character> nextCharacters = source.get(key);

            if (nextCharacters.size() > compressionLevel) {
                List<Character> nextCharactersResult = makeNextCharacters(nextCharacters);
                fulfillCharactersToCompressionLevel(nextCharacters, nextCharactersResult);
                result.put(key, nextCharactersResult);
            } else {
                result.put(key, new ArrayList<>(nextCharacters));
            }
        }
    }

    private List<Character> makeNextCharacters(List<Character> nextCharacters) {
        Set<Character> uniqueCharacters = new HashSet<>(nextCharacters);
        List<Character> nextCharactersResult = new ArrayList<>();

        for (Character character : uniqueCharacters) {
            int requiredCount = calculateRequiredCountFrom(nextCharacters, character);

            for (int i = 0; i < requiredCount; i++) {
                nextCharactersResult.add(character);
            }
        }
        return nextCharactersResult;
    }

    private void compressCharacters(List<Character> source, List<Character> result) {
        if (source.size() > compressionLevel) {
            Set<Character> uniqueCharacters = new HashSet<>(source);

            for (Character character : uniqueCharacters) {
                int requiredCount = calculateRequiredCountFrom(source, character);
                for (int j = 0; j < requiredCount; j++) {
                    result.add(character);
                }
            }
            fulfillCharactersToCompressionLevel(source, result);
        } else {
            result.addAll(source);
        }
    }

    private void fulfillCharactersToCompressionLevel(List<Character> source, List<Character> result) {
        while (result.size() < compressionLevel) {
            List<Character> characters = new ArrayList<>(source);
            characters.removeAll(result);

            char character = makeCharacter(characters);
            result.add(character);
        }
    }

    private char makeCharacter(List<Character> characters) {
        char resultCharacter = 0;
        int maxSum = 0;
        for (Character character : characters) {
            int sum = countIn(characters, character);
            if (sum > maxSum) {
                maxSum = sum;
                resultCharacter = character;
            }
        }
        return resultCharacter;
    }

    private void fulfillWordLengthsToCompressionLevel(List<Integer> result, List<Integer> source) {
        while (result.size() < compressionLevel) {
            List<Integer> wordLengths = new ArrayList<>(source);
            wordLengths.removeAll(result);

            int lengthResult = makeWordLength(wordLengths);
            result.add(lengthResult);
        }
    }

    private int makeWordLength(List<Integer> wordLengths) {
        int lengthResult = 0;
        int maxSum = 0;
        for (Integer length : wordLengths) {
            int sum = countIn(wordLengths, length);
            if (sum > maxSum) {
                maxSum = sum;
                lengthResult = length;
            }
        }
        return lengthResult;
    }

    private int calculateRequiredCountFrom(List<?> source, Object toCount) {
        int sum = countIn(source, toCount);
        return sum * compressionLevel / source.size();
    }

    private int countIn(List<?> source, Object toCount) {
        return (int) source.stream()
                .filter(g -> g.equals(toCount))
                .count();
    }
}
