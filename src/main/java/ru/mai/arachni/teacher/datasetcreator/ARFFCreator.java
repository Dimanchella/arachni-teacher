package ru.mai.arachni.teacher.datasetcreator;

import org.jetbrains.annotations.NotNull;
import weka.core.Attribute;
import weka.core.Instances;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;

public class ARFFCreator {
    public void writeARFF(
            @NotNull String filePath,
            @NotNull Instances instances
    ) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(instances.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
