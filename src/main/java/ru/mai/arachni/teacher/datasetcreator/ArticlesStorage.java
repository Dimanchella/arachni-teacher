package ru.mai.arachni.teacher.datasetcreator;

import lombok.Getter;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;

@Getter
public class ArticlesStorage {
    private static final String DIR_SOURCE_PATH = "data\\universes\\";
    private static final int LIMIT_FILES_IN_CLASS = 900;
    private static final String FILE_RESULT_PATH = (new Formatter())
            .format("data\\artifacts\\Articles_%d.arff", LIMIT_FILES_IN_CLASS)
            .toString();

    public static final List<String> CLASSES = List.of(
            "EliteDangerous",
            "HarryPotter",
            "Metro",
            "StarWars",
            "TheElderScrolls",
            "Other"
    );

    private final ArrayList<Attribute> attributes;
    private final Instances instances;
    private final List<Article> articles;

    public ArticlesStorage() {
        attributes = new ArrayList<>();
        attributes.add(new Attribute("universe", CLASSES));
        attributes.add(new Attribute("text", true));
        instances = new Instances("articles_universes", attributes, 1);
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < CLASSES.size(); i++) {
            List<Article> localArticles = loadArticle(CLASSES.get(i));
            articles.addAll(localArticles);
            for (Article article : localArticles) {
                double[] instVal = new double[]{
                        i,
                        instances.attribute(1).addStringValue(article.getText().getText())
                };
                Instance inst = new DenseInstance(1, instVal);
                instances.add(inst);
            }
        }
        this.articles = articles.stream().toList();
    }

    private List<Article> loadArticle(String universe) {
        XMLReader xmlReader = new XMLReader();
        List<Object> articlesObjects = xmlReader.getListObjectsFromXMLs(
                DIR_SOURCE_PATH + universe,
                Article.class,
                LIMIT_FILES_IN_CLASS
        );
        return articlesObjects
                .stream()
                .map(ob -> (Article) ob)
                .toList();
    }

    public void saveRawDataset() {
        ARFFCreator arffCreator = new ARFFCreator();
        arffCreator.writeARFF(FILE_RESULT_PATH, instances);
    }
}
