package com.jetbrains.test.boot4.server.quote;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("quote")
public class QuoteEntity {
    @Id
    private Long id;
    private String text;
    private String author;
    private String source;

    @PersistenceCreator
    public QuoteEntity(Long id, String text, String author, String source) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.source = source;
    }

    public QuoteEntity(String text, String author, String source) {
        this(null, text, author, source);
    }


    public Long getId() { return id; }
    public String getText() { return text; }
    public String getAuthor() { return author; }
    public String getSource() { return source; }
}
