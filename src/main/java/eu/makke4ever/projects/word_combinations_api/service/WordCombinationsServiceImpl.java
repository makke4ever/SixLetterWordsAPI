package eu.makke4ever.projects.word_combinations_api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class WordCombinationsServiceImpl implements WordCombinationsService
{

    @Override
    public void findCombinations(final Collection<String> allWords,
                                 final int combinationWordSize,
                                 final Consumer<String> foundCombinationsConsumer)
    {
        final Set<String> toFind = allWords.stream().filter(s -> s.length() == combinationWordSize)
                .collect(Collectors.toSet());
        final Set<String> findIn = allWords.stream()
                .filter(s -> !toFind.contains(s) && s.length() < combinationWordSize).collect(Collectors.toSet());

        toFind.forEach(combinationToFind -> findCombinationsForOneWord(combinationToFind,
                                                                       findIn,
                                                                       0,
                                                                       1,
                                                                       new ArrayList<>(),
                                                                       foundCombinationsConsumer));
    }

    private void findCombinationsForOneWord(final String combinationToFind,
                                            final Set<String> findIn,
                                            final int startIndex,
                                            final int subSize,
                                            final List<String> previousSubStrings,
                                            final Consumer<String> foundCombinationsConsumer)
    {
        final int endIndex = startIndex + subSize;
        final String subString = combinationToFind.substring(startIndex, endIndex);
        final boolean continueSearch = endIndex < combinationToFind.length();
        if (findIn.contains(subString))
        {
            final List<String> subStrings = new ArrayList<>(previousSubStrings);
            subStrings.add(subString);
            if (continueSearch)
            {
                findCombinationsForOneWord(combinationToFind,
                                           findIn,
                                           endIndex,
                                           1,
                                           subStrings,
                                           foundCombinationsConsumer);
            }
            else
            {
                foundCombinationsConsumer.accept(createCombinationOutputString(subStrings, combinationToFind));
            }
        }
        if (continueSearch)
        {
            findCombinationsForOneWord(combinationToFind,
                                       findIn,
                                       startIndex,
                                       subSize + 1,
                                       previousSubStrings,
                                       foundCombinationsConsumer);
        }
    }

    private String createCombinationOutputString(final List<String> subStrings, final String combinationToFind)
    {
        return String.format("%s=%s", subStrings.stream().collect(Collectors.joining("+")), combinationToFind);
    }

}
