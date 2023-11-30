package ru.mai.arachni.teacher;

import company.evo.jmorphy2.FileLoader;
import company.evo.jmorphy2.MorphAnalyzer;
import company.evo.jmorphy2.ResourceFileLoader;
import company.evo.jmorphy2.lucene.LuceneFileLoader;
import lombok.SneakyThrows;
import ru.mai.arachni.teacher.datasetcreator.Article;
import ru.mai.arachni.teacher.datasetcreator.ArticlesStorage;
import ru.mai.arachni.teacher.datasetcreator.XMLReader;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import company.evo.jmorphy2.lucene.Jmorphy2Analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArachniTeacherApplication {
    public static Object loadModel(Class<?> clazz) {
        try {
            File modelFile = new File("..\\900_Ngrams_3_1_3000_PART.model");
            return SerializationHelper.read(new FileInputStream(modelFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        MorphAnalyzer.Builder builder = new MorphAnalyzer.Builder();

        String lang = "ru";
        String dictResourcePath = String.format("/company/evo/jmorphy2/%s/pymorphy2_dicts", lang);
        MorphAnalyzer morphy = builder
                .fileLoader(new ResourceFileLoader(dictResourcePath))
                .charSubstitutes(null)
        .build();
//        morphy.normalForms("Коты");
        System.out.println(morphy.normalForms("красивого"));
    }
}
