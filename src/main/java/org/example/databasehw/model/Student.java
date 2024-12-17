package org.example.databasehw.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "student")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Student() {
    }

    public Student(String name, Integer age, Long id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

