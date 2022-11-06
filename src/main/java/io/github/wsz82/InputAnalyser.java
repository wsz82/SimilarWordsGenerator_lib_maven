package io.github.wsz82;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class InputAnalyser {
    private final List<String> input;

    InputAnalyser(List<String> input) {
        this.input = input;
    }

    Seed makeSeed() {
        Seed seed = new Seed();
        for (String word : input) {
            if (isEmpty(word)) {
                continue;
            }
            fillSeed(seed, word);
        }
        return seed;
    }

    private void fillSeed(Seed seed, String word) {
        char[] tempWord = word.toCharArray();
        seed.getWordsLengths().add(tempWord.length);
        seed.getFirstChars().add(getFirstChar(tempWord));
        seed.getLastChars().add(getLastChar(tempWord));
        addCharToCharsCount(seed.getCharToChars(), tempWord);
    }

    private char getFirstChar(char[] tempWord) {
        return tempWord[0];
    }

    private char getLastChar(char[] tempWord) {
        return tempWord[tempWord.length - 1];
    }

    private boolean isEmpty(String word) {
        return word == null || word.equals("");
    }

    private void addCharToCharsCount(Map<Character, List<Character>> charToChars, char[] tempWord) {
        for (int index = 0; index < tempWord.length - 1; index++) {
            if (charToChars.containsKey(tempWord[index])) {
                charToChars.get(tempWord[index]).add(0, tempWord[index + 1]);
            } else {
                charToChars.put(tempWord[index], new ArrayList<>());
                charToChars.get(tempWord[index]).add(0, tempWord[index + 1]);
            }
        }
    }
}
