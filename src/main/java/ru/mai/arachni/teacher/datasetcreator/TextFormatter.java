package ru.mai.arachni.teacher.datasetcreator;

import company.evo.jmorphy2.MorphAnalyzer;
import company.evo.jmorphy2.ResourceFileLoader;

import java.io.IOException;
import java.util.List;

public class TextFormatter {
    private static final String LANG = "ru";
    private static final String PATH = String.format("/company/evo/jmorphy2/%s/pymorphy2_dicts", LANG);

    private final MorphAnalyzer morphy;

    public TextFormatter() throws IOException {
        morphy = newMorphAnalyzer();
    }

    private static MorphAnalyzer newMorphAnalyzer()
            throws IOException {
        return new MorphAnalyzer.Builder()
                .fileLoader(new ResourceFileLoader(PATH))
                .charSubstitutes(null)
                .build();
    }

    public List<String> getNormalForm(String word) throws IOException {
        return morphy.normalForms(word);
    }

    public String deletePunctuation(String text) {
        return text.replaceAll("[.,;:'\"()?!]", "");
    }

    public String lemmatizeWords(String text) throws IOException {
        String[] words = text.split("\\s");
        for (int i = 0; i < words.length; i++) {
            words[i] = getNormalForm(words[i]).get(0);
        }
        return String.join(" ", words);
    }
}
