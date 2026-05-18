package ra.model.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book {
    private Long id;
    private String title;
    private String author;
    private int year;
    private boolean available;

    public Book() {}

    public Book(Long id, String title, String author, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = available;
    }

    @XmlElement
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @XmlElement
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @XmlElement
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @XmlElement
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @XmlElement
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}