package com.lazovsky.DAO;

import org.springframework.stereotype.Component;

@Component("mp3")
public class MP3 {
    private int id;
    private String name;
    private String author;

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
