package com.smartbrowser.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class URLUtilsTest {

    @Test
    public void testIsValidURL() {
        assertTrue(URLUtils.isValidURL("http://www.google.com"));
        assertTrue(URLUtils.isValidURL("https://github.com/test?a=1&b=2"));
        assertFalse(URLUtils.isValidURL("not a url"));
        assertFalse(URLUtils.isValidURL("www.google.com")); // \u7f3a\u5c11\u534f\u8bae
    }

    @Test
    public void testParseInput() {
        assertEquals("https://www.google.com/search?q=hello+world", URLUtils.parseInput("hello world"));
        assertEquals("http://www.google.com", URLUtils.parseInput("http://www.google.com"));
        assertEquals("http://google.com", URLUtils.parseInput("google.com"));
    }

    @Test
    public void testExtractDomain() {
        assertEquals("www.google.com", URLUtils.extractDomain("https://www.google.com/search?q=test"));
        assertEquals("github.com", URLUtils.extractDomain("http://github.com/someone/repo"));
    }
}
