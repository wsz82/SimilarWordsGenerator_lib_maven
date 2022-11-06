package io.github.wsz82;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class WordMaker {
    private final Random random = new Random();
    private final Seed seed;
    private final int minWordLength;
    private final int maxWordLength;
    private final boolean firstCharAsInInput;
    private final boolean lastCharAsInInput;

    public WordMaker(Seed seed, int minWordLength, int maxWordLength, boolean firstCharAsInInput, boolean lastCharAsInInput) {
        this.seed = seed;
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
        this.firstCharAsInInput = firstCharAsInInput;
        this.lastCharAsInInput = lastCharAsInInput;
    }

    String makeWord() throws InvalidWordException {
        StringBuilder wordBuilder = new StringBuilder();
        appendFirstCharacter(wordBuilder);
        for (int i = 0; i < makeWordLength(); i++) {
            appendNextCharacter(wordBuilder);
        }
        String word = wordBuilder.toString();
        if (isWordInvalid(word)) {
            throw new InvalidWordException();
        }
        return word;
    }

    boolean isWordInvalid(String word) {
        if (word.isEmpty()) {
            return true;
        }
        if (isLongerThanMaxWordLength(word)) {
            return true;
        }
        if (isShorterThanMinWordLength(word)) {
            return true;
        }
        if (isLastCharRequirementNotFulfilled(word)) {
            return true;
        }
        return false;
    }

    private void appendFirstCharacter(StringBuilder wordBuilder) {
        char firstChar = makeFirstChar();
        wordBuilder.append(firstChar);
    }

    private void appendNextCharacter(StringBuilder wordBuilder) {
        Character nextCharacter;
        try {
            nextCharacter = makeNextValidCharacter(wordBuilder);
        } catch (InvalidWordException e) {
            return;
        }
        wordBuilder.append(nextCharacter);
    }

    private boolean isLastCharRequirementNotFulfilled(String word) {
        char lastChar = getLastChar(word);
        return lastCharAsInInput && isCharNotInLastCharsList(lastChar);
    }

    private boolean isShorterThanMinWordLength(String word) {
        return (minWordLength != 0 && word.length() < minWordLength);
    }

    private boolean isLongerThanMaxWordLength(String word) {
        return (maxWordLength != 0 && word.length() > maxWordLength);
    }

    private char getLastChar(String word) {
        return word.charAt(word.length() - 1);
    }

    private Character makeNextValidCharacter(StringBuilder wordBuilder) throws InvalidWordException {
        char currentChar = getLastChar(wordBuilder);
        boolean noNextChars = !isCharInCharsCount(currentChar);
        if (noNextChars) {
            throw new InvalidWordException();
        }
        return makeNextCharacter(currentChar);
    }

    private Character makeNextCharacter(char currentChar) {
        List<Character> charsCountList = seed.getCharToChars().get(currentChar);
        Character[] charsCountArr = charsCountList.toArray(new Character[0]);
        return charsCountArr[random.nextInt(charsCountArr.length)];
    }

    private boolean isCharNotInLastCharsList(char character) {
        return !seed.getLastChars().contains(character);
    }

    private boolean isCharInCharsCount(char character) {
        return seed.getCharToChars().containsKey(character);
    }

    private char getLastChar(StringBuilder wordBuilder) {
        return wordBuilder.charAt(wordBuilder.length() - 1);
    }

    private char makeFirstChar() {
        if (firstCharAsInInput) {
            List<Character> firstChars = seed.getFirstChars();
            return firstChars.get(random.nextInt(firstChars.size()));
        } else {
            List<Character> characters = new ArrayList<>(seed.getCharToChars().keySet());
            return characters.get(random.nextInt(characters.size()));
        }
    }

    private int makeWordLength() {
        if (minWordLength != 0 && maxWordLength != 0) {
            return wordLengthFromManualInputs();
        } else if (minWordLength != 0) {
            return wordLengthFromMin();
        } else if (maxWordLength != 0) {
            return wordLengthFromMax();
        } else {
            return defaultWordLength();
        }
    }

    private int wordLengthFromMax() {
        TreeSet<Integer> sortedWordLengths = new TreeSet<>(seed.getWordsLengths());
        int smallestNumber = sortedWordLengths.first();

        if (maxWordLength < smallestNumber) {
            return random.nextInt(maxWordLength) + 1;
        } else {
            return defaultWordLength();
        }
    }

    private int wordLengthFromMin() {
        TreeSet<Integer> sortedWordLengths = new TreeSet<>(seed.getWordsLengths());
        int highestNumber = sortedWordLengths.last();

        if (minWordLength > highestNumber) {
            return minWordLength;
        } else {
            return defaultWordLength();
        }
    }

    private int wordLengthFromManualInputs() {
        return random.nextInt((maxWordLength - minWordLength) + 1) + minWordLength;
    }

    private int defaultWordLength() {
        return seed.getWordsLengths().get(random.nextInt(seed.getWordsLengths().size()));
    }
}
