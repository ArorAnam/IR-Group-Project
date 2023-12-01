package org.example.parser;

import org.example.model.TopicModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TopicParser {
    private final static Path currentRelativePath = Paths.get("").toAbsolutePath();
    private final static String absPathToTopic = String.format("%s/dataset/topics", currentRelativePath);

    public ArrayList<TopicModel> loadTopics() throws IOException {
        List<String> fileData = Files.readAllLines(Paths.get(absPathToTopic), StandardCharsets.UTF_8);
        ArrayList<TopicModel> topics = new ArrayList<TopicModel>();
        TopicModel topic = new TopicModel();
        String fieldType = "";
        StringBuilder readString = new StringBuilder();
        for (String line : fileData) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '<') {
                    String newFieldType = line.substring(1, line.indexOf('>'));
                    switch (newFieldType) {
                        case "top":
                            topic = new TopicModel();
                            break;
                        case "/top":
                            topics.add(topic);
                            break;
                        case "num":
                            topic.setNumber(Integer.parseInt(line.substring(14).trim()));
                            break;
                        case "title":
                            topic.setTitle(line.substring(8).trim());
                    }
                    if (!newFieldType.equals(fieldType)) {
                        switch (fieldType) {
                            case "desc":
                                topic.setDescription(readString.toString().trim());
                                break;
                            case "narr":
                                topic.setNarrative(readString.toString().trim());
                                break;
                        }
                        readString = new StringBuilder();
                    }
                    fieldType = newFieldType;
                } else {
                    readString.append(line.trim()).append(" ");
                }
            }
        }
        return topics;
    }
}
