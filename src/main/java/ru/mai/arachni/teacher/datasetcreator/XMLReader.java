package ru.mai.arachni.teacher.datasetcreator;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class XMLReader {
    private List<File> getFilesList(String path) {
        File dir = new File(path);
        try {
            return List.of(Objects.requireNonNull(dir.listFiles()));
        } catch (NullPointerException e) {
            return List.of();
        }
    }

    public Object getObjectFromXML(
            @NotNull String filePath,
            @NotNull Class<?> clazz
    ) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String body = br.lines().collect(Collectors.joining());
            StringReader reader = new StringReader(body);
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException | FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<Object> getListObjectsFromXMLs(
            @NotNull String dirPath,
            @NotNull Class<?> clazz,
            int numFiles
    ) {
        List<File> files = getFilesList(dirPath);
        ArrayList<Object> data = new ArrayList<>();
        int limit = numFiles != -1 && files.size() > numFiles ? numFiles : files.size();
        double step = (double) files.size() / limit;
        for (double i = 0; i < files.size(); i += step) {
            data.add(getObjectFromXML(files.get((int) i).toString(), clazz));
        }
        return data;
    }

    public List<Object> getListObjectsFromXMLs(
            @NotNull String dirPath,
            @NotNull Class<?> clazz
    ) {
        return getListObjectsFromXMLs(dirPath, clazz, -1);
    }
}
