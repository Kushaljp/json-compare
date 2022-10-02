package io.json.compare.matcher;

import com.fasterxml.jackson.databind.JsonNode;
import io.json.compare.CompareMode;
import io.json.compare.JsonComparator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class JsonArrayMatcher extends AbstractJsonMatcher {

    private final Set<Integer> matchedPositions = new HashSet<>();

    JsonArrayMatcher(JsonNode expected, JsonNode actual, JsonComparator comparator, Set<CompareMode> compareModes) {
        super(expected, actual, comparator, compareModes);
    }

    @Override
    public List<String> match() {
        return Collections.emptyList();
//        List<String> diffs = new ArrayList<>();
//        for (int i = 0; i < expected.size(); i++) {
//            JsonNode expElement = expected.get(i);
//            if (isJsonPathNode(expElement)) {
//                diffs.addAll(new JsonMatcher(expElement, actual, comparator, compareModes).match());
//            } else {
//                matchWithActualJsonArray(i, expElement, actual);
//            }
//        }
//        if (compareModes.contains(CompareMode.JSON_ARRAY_NON_EXTENSIBLE) && expected.size() < actual.size()) {
//            throw new MatcherException("Actual JSON ARRAY has extra elements");
//        }
    }

//    private void matchWithActualJsonArray(int expPosition, JsonNode expElement, JsonNode actual) {
//        UseCase useCase = getUseCase(expElement);
//        boolean found = false;
//        actualElementsLoop:
//        for (int j = 0; j < actual.size(); j++) {
//            if (matchedPositions.contains(j)) {
//                continue;
//            }
//            if (compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER) && j != expPosition) {
//                continue;
//            }
//            switch (useCase) {
//                case MATCH:
//                    JsonNode actElement = actual.get(j);
//                    try {
//                        new JsonMatcher(expElement, actElement, comparator, compareModes).match();
//                    } catch (MatcherException e) {
//                        if (compareModes.contains(CompareMode.JSON_ARRAY_STRICT_ORDER)) {
//                            throw new MatcherException(String
//                                    .format("JSON ARRAY elements differ at position %s:\n%s", expPosition + 1,
//                                            MessageUtil.cropL(JSONCompare.prettyPrint(expElement))));
//                        }
//                        continue actualElementsLoop;
//                    }
//                    found = true;
//                    matchedPositions.add(j);
//                    break actualElementsLoop;
//                case MATCH_ANY:
//                    matchedPositions.add(j);
//                    return;
//                case DO_NOT_MATCH:
//                    actElement = actual.get(j);
//                    if (!areOfSameType(expElement, actElement)) {
//                        continue actualElementsLoop;
//                    }
//                    try {
//                        new JsonMatcher(expElement, actElement, comparator, compareModes).match();
//                    } catch (MatcherException e) {
//                        found = true;
//                        break actualElementsLoop;
//                    }
//                    break;
//                case DO_NOT_MATCH_ANY:
//                    throw new MatcherException("Expected element from position " + (expPosition + 1)
//                            + " was FOUND:\n" + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
//            }
//        }
//        if (!found && useCase == UseCase.MATCH) {
//            throw new MatcherException("Expected element from position " + (expPosition + 1) + " was NOT FOUND:\n"
//                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
//        }
//        if (found && useCase == UseCase.DO_NOT_MATCH) {
//            throw new MatcherException("Expected element from position " + (expPosition + 1)
//                    + " was FOUND:\n" + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
//        }
//        if (useCase == UseCase.MATCH_ANY) {
//            throw new MatcherException("Expected condition of type MATCH_ANY from position " + (expPosition + 1)
//                    + " was NOT MET. Actual Json Array has no extra elements:\n"
//                    + MessageUtil.cropL(JSONCompare.prettyPrint(expElement)));
//        }
//    }
}
