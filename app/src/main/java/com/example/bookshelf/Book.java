package com.example.bookshelf;

import androidx.annotation.NonNull;

public class Book {
    private int id;
    public String title;
    private String author;
    private String coverURL;

    Book(int id, String title, String author,String coverURL){
        this.id = id;
        this.title = title;
        this.author = author;
        this.coverURL=coverURL;
    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getCoverURL(){
        return coverURL;
    }

    @NonNull
    @Override
    public String toString() {
        return id+", " + title + ", " + author+", " + getCoverURL();
    }
}
