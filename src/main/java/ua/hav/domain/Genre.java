package ua.hav.domain;

import ua.hav.domain.annotations.ShowByReference;
import ua.hav.domain.annotations.Table;

import javax.validation.constraints.Size;

/**
 * Created by Юля on 16.08.2016.
 */
@Table(name = "genres")
public class Genre {


    private int id;

    @Size(min = 3, max = 15)
    @ShowByReference
    private String name;

    private String note;

    public Genre() {
    }

    public Genre(boolean b) {
        id = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
