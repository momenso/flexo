package org.momenso;

import java.util.*;


public class MeaningDefiner {
    private Map<String, String> definitions = new HashMap<>();
    private Map<String, MeaningDefiner> nested = new HashMap<>();

    public MeaningDefiner define(String value, String meaning) {
        definitions.put(value, meaning);
        return this;
    }

    public Optional<String> getMeaning(String... values) {
        Iterator<String> iterator = Arrays.asList(values).iterator();
        if (iterator.hasNext()) {
            return getMeaning(iterator);
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> getMeaning(Iterator<String> iterator) {
        String value = iterator.next();
        if (!iterator.hasNext()) {
            return Optional.ofNullable(definitions.get(value));
        } else if (nested.containsKey(value)) {
            return nested.get(value).getMeaning(iterator);
        } else {
            return Optional.empty();
        }
    }

    public MeaningDefiner define(String value, String meaning, MeaningDefiner definer) {
        definitions.put(value, meaning);
        nested.put(value, definer);
        return this;
    }
}
