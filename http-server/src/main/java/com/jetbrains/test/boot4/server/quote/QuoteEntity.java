package com.jetbrains.test.boot4.server.quote;

import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("quote")
public class QuoteEntity {
    @Id
    @Nullable
    private Long id;
    private String text;
    private String author;
    private String source;

    @PersistenceCreator
    public QuoteEntity(@Nullable Long id, String text, String author, String source) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.source = source;
    }

    public QuoteEntity(String text, String author, String source) {
        this(null, text, author, source);
    }


    public @Nullable Long getId() { return id; }
    public String getText() { return text; }
    public String getAuthor() { return author; }
    public String getSource() { return source; }

    public void setId(@Nullable Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setAuthor(String author) { this.author = author; }
    public void setSource(String source) { this.source = source; }
}
