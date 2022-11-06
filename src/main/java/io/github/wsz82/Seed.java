package io.github.wsz82;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seed {

    private final List<Integer> wordsLengths;
    private final List<Character> firstChars;
    private final List<Character> lastChars;
    private final Map<Character, List<Character>> charToChars;

    public Seed(List<String> input) {
        InputAnalyser inputAnalyser = new InputAnalyser(input);
        Seed seed = inputAnalyser.makeSeed();
        this.wordsLengths = seed.getWordsLengths();
        this.firstChars = seed.getFirstChars();
        this.lastChars = seed.getLastChars();
        this.charToChars = seed.getCharToChars();
    }

    public Seed() {
        this.wordsLengths = new ArrayList<>();
        this.firstChars = new ArrayList<>();
        this.lastChars = new ArrayList<>();
        this.charToChars = new HashMap<>();
    }

    public static Seed fromJson(String json) {
        return new Gson().fromJson(json, Seed.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    List<Integer> getWordsLengths() {
        return wordsLengths;
    }

    List<Character> getFirstChars() {
        return firstChars;
    }

    List<Character> getLastChars() {
        return lastChars;
    }

    Map<Character, List<Character>> getCharToChars() {
        return charToChars;
    }
}
