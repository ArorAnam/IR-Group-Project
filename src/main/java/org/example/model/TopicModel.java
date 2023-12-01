package org.example.model;

public class TopicModel {
    private int number;
    private String title;
    private String description;
    private String narrative;

    public TopicModel() {}

    @Override
    public String toString() {
        return "TopicModel {" + "\n" +
                "number="+ number + "\n" +
                "title="+ title + "\n" +
                "description="+ description + "\n" +
                "nar" +
                "rative="+ narrative + "\n" +
                "}";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
}
