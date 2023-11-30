package ru.mai.arachni.teacher;

import company.evo.jmorphy2.MorphAnalyzer;
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
        
        MorphAnalyzer morphy = builder.build();
        System.out.println(morphy.normalForms("Сковородки"));
    }
}
