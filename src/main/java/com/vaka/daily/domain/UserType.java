package com.vaka.daily.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_type")
@Data
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "user_type_name", nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "userType")
    private List<User> users;

    public UserType() {
    }

    public UserType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
