package io.github.wsz82;

import java.util.Set;

public interface WordsGenerator {

    Set<String> generate(GenerateParameters parameters);

}