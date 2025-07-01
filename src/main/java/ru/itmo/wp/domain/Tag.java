package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @noinspection unused*/
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @NotNull
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
