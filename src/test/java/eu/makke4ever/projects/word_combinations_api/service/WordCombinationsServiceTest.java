package eu.makke4ever.projects.word_combinations_api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class WordCombinationsServiceTest
{
    private WordCombinationsService service = new WordCombinationsServiceImpl();

    private List<String> output = new ArrayList<>();

    @Test
    void emptyCollection()
    {
        this.service.findCombinations(List.of(), 6, this.output::add);
        assertThat(this.output).isEmpty();
    }

    @Test
    void findOneCombinationWithTwoParts()
    {
        this.service.findCombinations(List.of("dummy", "foo", "foobar", "extra", "bar", "extras"), 6, this.output::add);
        assertThat(this.output).containsExactly("foo+bar=foobar");
    }

    @Test
    void findTwoCombinationsWithTwoParts()
    {
        this.service.findCombinations(List.of("dummy", "s", "foo", "foobar", "extra", "bar", "extras"),
                                      6,
                                      this.output::add);
        assertThat(this.output).containsExactlyInAnyOrder("foo+bar=foobar", "extra+s=extras");
    }

    @Test
    void findOneCombinationWithThreeParts()
    {
        this.service
                .findCombinations(List.of("oo", "dummy", "f", "foobar", "extra", "bar", "extras"), 6, this.output::add);
        assertThat(this.output).containsExactly("f+oo+bar=foobar");
    }

    @Test
    void skipCombinationsNotMatchingRequestedSize()
    {
        this.service.findCombinations(List
                .of("oo", "dummy", "f", "dummybar", "foobar", "extra", "bar", "extras", "du", "mmy"),
                                      6,
                                      this.output::add);
        assertThat(this.output).containsExactly("f+oo+bar=foobar");
    }

    @Test
    void onePartIsUsedInTwoCombinations()
    {
        this.service.findCombinations(List.of("dummy", "oo", "f", "foobar", "faabar", "extra", "bar", "aa", "extras"),
                                      6,
                                      this.output::add);
        assertThat(this.output).containsExactlyInAnyOrder("f+oo+bar=foobar", "f+aa+bar=faabar");
    }

    @Test
    void findWordWithTwoPossibleCombinations()
    {
        this.service.findCombinations(List.of("dummy", "foo", "foobar", "extra", "bar", "oo", "extras", "f"),
                                      6,
                                      this.output::add);
        assertThat(this.output).containsExactlyInAnyOrder("foo+bar=foobar", "f+oo+bar=foobar");
    }

    @Test
    void findCombinationsButWithDifferentSize()
    {
        this.service.findCombinations(List.of("dummy", "foo", "foobar", "extra", "bar", "extras", "foobars", "s"),
                                      7,
                                      this.output::add);
        assertThat(this.output).containsExactlyInAnyOrder("foo+bar+s=foobars", "foobar+s=foobars");
    }

}
