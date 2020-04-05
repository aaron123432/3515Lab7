package com.example.bookshelf;

public class Books {
    private String book;
    private String author;

    public Books(String book, String author){
        this.book = book;
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
