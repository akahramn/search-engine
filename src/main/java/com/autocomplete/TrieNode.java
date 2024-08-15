package com.autocomplete;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    Map<Character, TrieNode> children;
    Integer frequency;
    boolean endOfWord;

    TrieNode() {
        children = new HashMap<Character, TrieNode>();
        endOfWord = false;
    }
}
