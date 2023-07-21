package eu.makke4ever.projects.word_combinations_api.service;

import java.util.Collection;
import java.util.function.Consumer;

public interface WordCombinationsService
{
    void findCombinations(Collection<String> allWords,
                          int combinationWordSize,
                          Consumer<String> foundCombinationsConsumer);
}
