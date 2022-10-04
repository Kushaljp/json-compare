package io.json.compare.matcher.diffs;

import io.json.compare.CompareMode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonObjectDiffTests {

    @Test
    public void compareJsonObjectsAndCheckForSingleFieldNotFoundDifference() {
        String expected = "{\"a\":100,\"e\":\"lorem100\"}";
        String actual = "{\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Field 'e' was NOT FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForMultipleFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"e\":\"lorem100\",\"x\":true}";
        String actual = "{\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "Field 'e' was NOT FOUND.*" +
                "Field 'x' was NOT FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForMultipleInDepthFieldNotFoundDifferences() {
        String expected = "{\"a\":100,\"x\":51,\"b\":{\"b1\":\"val1\",\"b2\":{\"b21\":\"test\"}}}";
        String actual = "{\"b\":{\"b3\":\"val1\",\"b2\":{\"b22\":10.432}},\"a\":100,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Field 'x' was NOT FOUND.*" +
                "b -> Field 'b1' was NOT FOUND.*" +
                "b -> b2 -> Field 'b21' was NOT FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForSingleDoNotMatchFieldDifference() {
        String expected = "{\"c\":true,\"!a\":100}";
        String actual = "{\"a\":200,\"c\":true}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 1 DIFFERENCE.*" +
                "Field '!a' was FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForMultipleDoNotMatchFieldDifferences() {
        String expected = "{\"c\":true,\"!a\":100,\"!c\":200,\"!x\":200}";
        String actual = "{\"a\":200,\"c\":true,\"x\":\"lorem\"}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "Field '!a' was FOUND.*" +
                "Field '!x' was FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForInDepthDoNotMatchFieldDifferences() {
        String expected = "{\"!c\":true,\"a\":200,\"x\":{\"!x1\":\"lorem\",\"x2\":{\"!x21\":\"test\"}}}";
        String actual = "{\"c\":true,\"a\":200,\"x\":{\"x1\":\"lorem\",\"x2\":{\"x23\":\"pat\",\"x21\":\"lol\"}}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "Field '!c' was FOUND.*" +
                "x -> Field '!x1' was FOUND.*" +
                "x -> x2 -> Field '!x21' was FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForMultipleCandidatesDifferences() {
        String expected = "{\".*\":{\"a\":1,\"x\":2}}";
        String actual = "{\"test1\":{\"a\":10,\"b\":22},\"test2\":{\"a\":20,\"x\":2}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "\\Q.*\\E -> a -> .*Expected value: 1 But got: 10.*" +
                "\\Q.*\\E -> .*Field 'x' was NOT FOUND.*" +
                "\\Q.*\\E -> a -> .*Expected value: 1 But got: 20.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\".*\":{\"a\":1,\"x\":2}}";
        String actual1 = "{\"test1\":{\"a\":10,\"b\":22},\"test2\":{\"a\":1,\"x\":2}}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    public void compareJsonObjectsAndCheckForDoNotMatchDifferences() {
        String expected = "{\"b\":2,\"a\":1,\"c\":{\"c2\":\"lorem2\",\"!.*\":\".*\"},\"!.*\":\".*\"}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c1\":\"lorem1\",\"c2\":\"lorem2\"},\"d\":1}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "c -> Field \\Q'!.*'\\E was FOUND.*" +
                "Field \\Q'!.*'\\E was FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);

        String expected1 = "{\"b\":2,\"a\":1,\"c\":{\"c2\":\"lorem2\",\"!.*\":\".*\"},\"!.*\":\".*\"}";
        String actual1 = "{\"a\":1,\"b\":2,\"c\":{\"c2\":\"lorem2\"}}";
        JSONCompare.assertMatches(expected1, actual1);
    }

    @Test
    public void compareJsonObjectsAndCheckForMatchAnyDifferences() {
        String expected = "{\"b\":2,\"a\":1,\"c\":{\"c1\":\"lorem2\",\".*\":\".*\"},\"!.*\":\".*\"}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c1\":\"lorem2\"}}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual));
        assertTrue(error.getMessage().matches("(?s).*FOUND 2 DIFFERENCE.*" +
                "c -> Field \\Q'.*'\\E was NOT FOUND.*" +
                "Field \\Q'!.*'\\E was FOUND.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForNonExtensibleObjectDifferences() {
        String expected = "{\"b\":2,\"a\":0,\"c\":{\"c1\":\"lorem1\"}}";
        String actual = "{\"a\":1,\"b\":2,\"c\":{\"c2\":\"lorem2\",\"c1\":\"lorem1\"},\"x\":0}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual,
                new HashSet<>(Collections.singletonList(CompareMode.JSON_OBJECT_NON_EXTENSIBLE))));
        assertTrue(error.getMessage().matches("(?s).*FOUND 3 DIFFERENCE.*" +
                "a ->.*Expected value: 0 But got: 1.*" +
                "c -> Actual JSON OBJECT has extra fields.*" +
                "Actual JSON OBJECT has extra fields.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }

    @Test
    public void compareJsonObjectsAndCheckForMismatchDifferences() {
        String expected = "{\n" +
                "  \"branch\": -212276217.0580678,\n" +
                "  \"wagon\": {\n" +
                "    \"being\": true,\n" +
                "    \"position\": {\n" +
                "      \"interior1\": \"balance\",\n" +
                "      \"writer\": -806595724.6570452,\n" +
                "      \"area\": true,\n" +
                "      \"difficulty\": \"not equal\"\n" +
                "    },\n" +
                "    \"ill\": -2976723441,\n" +
                "    \"particular\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        894216220\n" +
                "      ],\n" +
                "      [\n" +
                "        -1644147917.075238,\n" +
                "        \"recent\",\n" +
                "        [\n" +
                "          false,\n" +
                "          111947014.54409099,\n" +
                "          {\n" +
                "            \"monkey\": -172346568.4752009,\n" +
                "            \"construction\": \"affect\"\n" +
                "          },\n" +
                "          true\n" +
                "        ]\n" +
                "      ],\n" +
                "      222085833,\n" +
                "      false\n" +
                "    ]" +
                "  },\n" +
                "  \"duty\": false,\n" +
                "  \"master\": -1005804822.0610056\n" +
                "}";
        String actual = "{\n" +
                "  \"branch\": -212276217.0580678,\n" +
                "  \"wagon\": {\n" +
                "    \"being\": false,\n" +
                "    \"arrange\": -2082234921.4886482,\n" +
                "    \"position\": {\n" +
                "      \"interior\": \"balance\",\n" +
                "      \"writer\": -806595724.6570451,\n" +
                "      \"area\": true,\n" +
                "      \"difficulty\": \"equal\"\n" +
                "    },\n" +
                "    \"ill\": -297672344,\n" +
                "    \"particular\": [\n" +
                "      [\n" +
                "        true,\n" +
                "        894216228\n" +
                "      ],\n" +
                "      [\n" +
                "        -1644147917.075238,\n" +
                "        \"recent\",\n" +
                "        [\n" +
                "          false,\n" +
                "          111947014.54409099,\n" +
                "          {\n" +
                "            \"monkey\": -172346568.4752009,\n" +
                "            \"construction\": \"affect\"\n" +
                "          },\n" +
                "          true\n" +
                "        ]\n" +
                "      ],\n" +
                "      222085833,\n" +
                "      false\n" +
                "    ]" +
                "  },\n" +
                "  \"duty\": false,\n" +
                "  \"master\": 1005804822.0610056\n" +
                "}";
        AssertionError error = assertThrows(AssertionError.class, () -> JSONCompare.assertMatches(expected, actual, null, "My custom message"));
        assertTrue(error.getMessage().matches("(?s).*FOUND 7 DIFFERENCE.*" +
                "wagon -> being ->.*Expected value: true But got: false.*" +
                "wagon -> position -> Field 'interior1' was NOT FOUND.*" +
                "wagon -> position -> writer ->.*Expected value: -806595724.6570452 But got: -806595724.6570451.*" +
                "wagon -> position -> difficulty ->.*Expected value: \"not equal\" But got: \"equal\".*" +
                "wagon -> ill ->.*Expected value: -2976723441 But got: -297672344.*" +
                "wagon -> particular ->.*Expected element from position 1 was NOT FOUND.*" +
                "true.*894216220.*" +
                "master ->.*Expected value: -1005804822.0610056 But got: 1005804822.0610056.*" +
                "My custom message.*"));
        JSONCompare.assertNotMatches(expected, actual);
    }
}
