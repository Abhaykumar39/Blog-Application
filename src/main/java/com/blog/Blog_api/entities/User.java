package com.blog.Blog_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="") // If you want to change the name of the user table.
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id  //This annotation is used make primary key.
    @GeneratedValue(strategy = GenerationType.AUTO) //Auto generated value of Id
    private Integer id;

    @Column(name="user_name") //Change the column name in the column.
    private String name;
    private String email;
    private String password;
    private String about;

}
