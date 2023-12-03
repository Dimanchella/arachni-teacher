package ru.mai.arachni.teacher.datasetcreator;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.mai.arachni.teacher.xmlloader.Article;
import ru.mai.arachni.teacher.xmlloader.XMLReader;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArticlesStorage {
    private static final String DIR_SOURCE_PATH = "data\\universes\\";
    private static final String FILE_RESULT_PATH = "data\\artifacts\\dataset_%d.arff";

    private record ArticleUniverse(
            int uniInd,
            @NotNull String universe,
            @NotNull String text
    ) {
    }

    public static final List<String> CLASSES = List.of(
            "Elite Dangerous",
            "Гарри Поттер",
            "Метро",
            "Звёздные войны",
            "The Elder Scrolls",
            "Другое"
    );

    private final ARFFCreator arffCreator;
    private final XMLReader xmlReader;
    private final TextFormatter textFormatter;

    private ArrayList<Attribute> attributes;
    private Instances instances;
    private List<ArticleUniverse> articlesUniverses;
    private List<Article> articles;

    private String fileResultPath;

    @SneakyThrows
    public ArticlesStorage() {
        arffCreator = new ARFFCreator();
        xmlReader = new XMLReader();
        textFormatter = new TextFormatter();
    }

    private List<Article> loadArticles(String universe, int articlesNum) {
        List<Object> articlesObjects = xmlReader.getListObjectsFromXMLs(
                DIR_SOURCE_PATH + universe,
                Article.class,
                articlesNum
        );
        fileResultPath = String.format(FILE_RESULT_PATH, articlesNum);
        return articlesObjects
                .stream()
                .map(ob -> (Article) ob)
                .toList();
    }

    public ArticlesStorage loadArticles(int articlesNum) {
        articles = new ArrayList<>();
        articlesUniverses = new ArrayList<>();
        for (int i = 0; i < CLASSES.size(); i++) {
            List<Article> loadedArticles = loadArticles(
                    CLASSES.get(i),
                    articlesNum
            );
            articles.addAll(loadedArticles);
            final int index = i;
            articlesUniverses.addAll(
                    loadedArticles
                            .stream()
                            .map(article -> new ArticleUniverse(
                                    index,
                                    CLASSES.get(index),
                                    article.getText().getText()
                            ))
                            .toList()
            );
        }
        return this;
    }

    @SneakyThrows
    public ArticlesStorage createInstances() {
        attributes = new ArrayList<>();
        attributes.add(new Attribute("universe", CLASSES));
        attributes.add(new Attribute("text", true));
        instances = new Instances("articles_universes", attributes, 1);

        for (var au : articlesUniverses) {
            double[] instVal = new double[]{
                    au.uniInd,
                    instances.attribute(1).addStringValue(
                            textFormatter.lemmatizeWords(
                                    //au.text
                                    textFormatter.deletePunctuation(au.text)
                            )
                    )
            };
            Instance inst = new DenseInstance(1, instVal);
            instances.add(inst);
        }

        return this;
    }

    public void saveRawDataset() {
        arffCreator.writeARFF(fileResultPath, instances);
    }
}
