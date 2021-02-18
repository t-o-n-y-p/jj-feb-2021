package org.levelp.model;

import javax.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String partId;

    @Column(nullable = false, length = 1000)
    private String title;

    @ManyToOne
    private User owner;

    public Part() {
    }

    public Part(String partId, String title) {
        this.partId = partId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
