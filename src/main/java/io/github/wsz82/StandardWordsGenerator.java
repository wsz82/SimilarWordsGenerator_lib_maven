package io.github.wsz82;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class StandardWordsGenerator implements WordsGenerator {
    private static final long INITIAL_CHECK_TIME_PERIOD = 200;
    private static final int TIMES_TO_EXTEND_TIME_CHECK = 2;

    private final Seed seed;

    public StandardWordsGenerator(Seed seed) {
        this.seed = seed;
    }

    @Override
    public Set<String> generate(GenerateParameters parameters) {
        int numberOfWords = parameters.getNumberOfWords();
        GenerateProcess generateProcess = makeGenerateProcess(parameters, numberOfWords);
        TimeCheck timeCheck = new TimeCheck(INITIAL_CHECK_TIME_PERIOD);
        WordMaker wordMaker = new WordMaker(seed, parameters.getMinWordLength(), parameters.getMaxWordLength(),
                parameters.isFirstCharAsInInput(), parameters.isLastCharAsInInput());
        while (generateProcess.isNotEnoughWordsGenerated()) {
            String word;
            try {
                word = wordMaker.makeWord();
            } catch (InvalidWordException e) {
                continue;
            }
            try {
                addWord(timeCheck, generateProcess, word);
            } catch (WorthlessGenerateProcessException e) {
                break;
            }
        }
        return generateProcess.getResult();
    }

    private GenerateProcess makeGenerateProcess(GenerateParameters parameters, int numberOfWords) {
        Set<String> result = makeEmptyWordsSet(parameters.isSorted());
        Set<String> resultCheck = new HashSet<>();
        return new GenerateProcess(result, resultCheck, numberOfWords);
    }

    private void addWord(TimeCheck timeCheck, GenerateProcess generateProcess, String word) {
        if (timeCheck.isTimeBeforeCheck()) {
            generateProcess.addToResult(word);
        } else if (timeCheck.isTimeBeforeDoubleCheck()) {
            generateProcess.addToResultCheck(word);
        } else {
            generateProcess.mergeResultCheckToResult();
            timeCheck.extendCheckTime(TIMES_TO_EXTEND_TIME_CHECK);
        }
    }

    private Set<String> makeEmptyWordsSet(boolean sorted) {
        Set<String> result;
        if (sorted) {
            result = new TreeSet<>();
        } else {
            result = new HashSet<>();
        }
        return result;
    }

    public Seed getSeed() {
        return seed;
    }
}
