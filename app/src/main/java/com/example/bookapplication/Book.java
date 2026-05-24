package com.example.bookapplication;

public class Book {
    int id;
    String title;
    String author;
    String category;
    String publisher;
    String date;
    String rating;
    String count;
    String description;
    String tags;
    String info;

    public Book(
            int id,
            String title,
            String author,
            String category,
            String publisher,
            String date,
            String rating,
            String count,
            String description,
            String tags,
            String info
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.date = date;
        this.rating = rating;
        this.count = count;
        this.description = description;
        this.tags = tags;
        this.info = info;
    }
}