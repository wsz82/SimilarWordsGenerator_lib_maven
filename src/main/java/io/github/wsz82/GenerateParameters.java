package io.github.wsz82;

public final class GenerateParameters {
    private final boolean sorted;
    private final boolean firstCharAsInInput;
    private final boolean lastCharAsInInput;
    private final int numberOfWords;
    private final int minWordLength;
    private final int maxWordLength;

    private GenerateParameters(Builder builder) {
        this.sorted = builder.sorted;
        this.firstCharAsInInput = builder.firstCharAsInInput;
        this.lastCharAsInInput = builder.lastCharAsInInput;
        this.numberOfWords = builder.numberOfWords;
        this.minWordLength = builder.minWordLength;
        this.maxWordLength = builder.maxWordLength;
    }

    public boolean isSorted() {
        return sorted;
    }

    public boolean isFirstCharAsInInput() {
        return firstCharAsInInput;
    }

    public boolean isLastCharAsInInput() {
        return lastCharAsInInput;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public final static class Builder {
        private boolean sorted = true;
        private boolean firstCharAsInInput = true;
        private boolean lastCharAsInInput = true;
        private int numberOfWords = 10;
        private int minWordLength = 0;  //number 0 is a flag for default word length
        private int maxWordLength = 0;  //number 0 is a flag for default word length

        public Builder setSorted(boolean sorted) {
            this.sorted = sorted;
            return this;
        }

        public Builder setFirstCharAsInInput(boolean firstCharAsInInput) {
            this.firstCharAsInInput = firstCharAsInInput;
            return this;
        }

        public Builder setLastCharAsInInput(boolean lastCharAsInInput) {
            this.lastCharAsInInput = lastCharAsInInput;
            return this;
        }

        public Builder setNumberOfWords(int numberOfWords) {
            this.numberOfWords = numberOfWords;
            return this;
        }

        public Builder setMinWordLength(int minWordLength) {
            this.minWordLength = minWordLength;
            return this;
        }

        public Builder setMaxWordLength(int maxWordLength) {
            this.maxWordLength = maxWordLength;
            return this;
        }

        public GenerateParameters build() {
            return new GenerateParameters(this);
        }
    }
}
