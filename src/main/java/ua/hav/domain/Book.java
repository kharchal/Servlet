package ua.hav.domain;

import ua.hav.domain.annotations.Reference;
import ua.hav.domain.annotations.Table;

import javax.validation.constraints.Size;

/**
 * Created by Юля on 12.08.2016.
 */
@Table(name = "books")
public class Book {

    private int id;

    @Size(min = 1, max = 20)
    private String name;

    @Size(min = 1, max = 15)
    private String author;

    @Size(min = 0, max = 2000)
    private int pages;

    @Reference
    private Genre genre;

    @Size(min = 0, max = 1000)
    private int price;

    @Size(min = 0, max = 100)
    private int qty;

    public Book() {
        setGenre(new Genre(true));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", genre=" + genre +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
