package ru.mai.arachni.teacher;


import ru.mai.arachni.teacher.datasetcreator.ArticlesStorage;

public class ArachniTeacherApplication {
    private static final int LIMIT_FILES_IN_CLASS = 900;
    private static final ArticlesStorage articlesStorage = new ArticlesStorage();

    public static void main(String[] args) {
        articlesStorage
                .loadArticles(LIMIT_FILES_IN_CLASS)
                .createInstances()
                .saveRawDataset();
        System.out.println("SUCCESS");
    }
}
