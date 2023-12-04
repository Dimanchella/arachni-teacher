package ru.mai.arachni.teacher.datasetcreator;

import company.evo.jmorphy2.MorphAnalyzer;
import company.evo.jmorphy2.ResourceFileLoader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormatter {
    private static final String LANG = "ru";
    private static final String PATH = String.format("/company/evo/jmorphy2/%s/pymorphy2_dicts", LANG);
    private static final Pattern RU_WORD_PAT = Pattern.compile(
            "^\\W*?([а-яёА-ЯЁ]+)\\W*?$"
    );

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
            Matcher match = RU_WORD_PAT.matcher(words[i]);
            if (match.find()) {
                words[i] = words[i].replaceAll(
                        match.group(1),
                        getNormalForm(match.group(1)).get(0)
                );
            }
        }
        return String.join(" ", words);
    }
}
