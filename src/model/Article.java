package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {

    private int aid;
    private StringProperty name;
    private boolean available;

    public Article(int aid,String name,boolean available){
        this.aid = aid;
        this.name = new SimpleStringProperty(name);
        this.available = available;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
