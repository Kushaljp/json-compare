package io.json.compare.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class JsonPrettyPrintlTest {

    @Test
    public void testPrettyPrint() {
        String expected = "{\n" +
                "  \"a\" : \"test\"\n" +
                "}";
        assertEquals(expected, JSONCompare.prettyPrint(new ObjectMapper().createObjectNode().set("a", new TextNode("test"))));
    }

    @Test
    public void testNullPrettyPrint() {
        assertEquals("null", JSONCompare.prettyPrint(null));
    }


    @Test
    public void testObjectToJsonNodeConversion() throws IOException {
        Map<String, Object> obj = new HashMap<>();
        obj.put("key", "value");
        JsonNode jsonNode = JsonUtils.toJson(obj);

        assertNotNull(jsonNode, "The converted JsonNode should not be null.");
        assertTrue(jsonNode.isObject(), "The JsonNode should be an object.");
        assertEquals("value", jsonNode.get("key").asText(), "The JsonNode should contain the correct value for 'key'.");
    }


    @Test
    public void testPrettyPrintWithEmptyString() throws IOException {
        String emptyString = "";
        String result = JsonUtils.prettyPrint(emptyString);

        assertEquals("", result, "Pretty print of an empty string should return an empty string.");
    }


    @Test
    public void testToJsonWithStringInput() throws IOException {
        String jsonString = "{\"name\":\"Test\"}";
        JsonNode jsonNode = JsonUtils.toJson(jsonString);

        assertNotNull(jsonNode, "The JsonNode should not be null.");
        assertTrue(jsonNode.isObject(), "The JsonNode should be an object.");
        assertEquals("Test", jsonNode.get("name").asText(), "The JsonNode should contain the correct value for 'name'.");
    }


}
