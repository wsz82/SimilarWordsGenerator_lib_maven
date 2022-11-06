package io.github.wsz82;

import java.util.Set;

class GenerateProcess {
    private final Set<String> result;
    private final Set<String> resultCheck;
    private final int numberOfWords;

    GenerateProcess(Set<String> result, Set<String> resultCheck, int numberOfWords) {
        this.result = result;
        this.resultCheck = resultCheck;
        this.numberOfWords = numberOfWords;
    }

    void addToResult(String word) {
        result.add(word);
    }

    void addToResultCheck(String word) {
        resultCheck.add(word);
    }

    boolean isNotEnoughWordsGenerated() {
        return result.size() < numberOfWords;
    }

    void mergeResultCheckToResult() throws WorthlessGenerateProcessException {
        boolean noMoreWordsGenerated = result.containsAll(resultCheck);
        if (noMoreWordsGenerated) {
            throw new WorthlessGenerateProcessException();
        }
        result.addAll(resultCheck);
    }

    Set<String> getResult() {
        return result;
    }
}
