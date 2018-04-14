package com.lazovsky.DAO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mp3")
@Scope("prototype")
public class MP3 {
    private int id;
    private String name;

    private Author author;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
