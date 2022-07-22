package io.json.compare.matcher.issues;

import org.junit.jupiter.api.Test;
import io.json.compare.DefaultJsonComparator;
import io.json.compare.JSONCompare;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Issue13Test {
    //Issue: https://github.com/fslev/json-compare/issues/28

    @Test
    // Check for hint message from failed comparison of jsons with unintentional regexes
    public void compareJsonWithUnintentionalRegexAndDefaultComparator() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertMatches(expected, actual);
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("unintentional regexes"));
        }
        try {
            JSONCompare.assertMatches(expected, actual, new DefaultJsonComparator());
        } catch (AssertionError e) {
            assertTrue(e.getMessage().contains("unintentional regexes"));
        }
    }

    @Test
    // Check hint message is missing from failed comparison with custom comparator
    public void compareJsonWithUnintentionalRegexAndCustomComparator() {
        String expected = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        String actual = "[{ \"name\" : \"someText (anotherText)\", \"code\" : \"oneMoreText\" }]";
        try {
            JSONCompare.assertMatches(expected, actual, new CustomComparator());
        } catch (AssertionError e) {
            assertFalse(e.getMessage().contains("unintentional regexes"));
        }
    }

    private static class CustomComparator extends DefaultJsonComparator {

    }
}
