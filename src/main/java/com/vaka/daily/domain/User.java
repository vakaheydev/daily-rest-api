package com.vaka.daily.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_user")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "user_login", nullable = false, length = 100)
    private String login;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "user_password", nullable = false, length = 100)
    private String password;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "user_first_name", nullable = false, length = 100)
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "user_second_name", nullable = false, length = 100)
    private String secondName;

    @Size(max = 100)
    @Column(name = "user_patronymic", length = 100)
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    public User(Integer id, String login, String password, String firstName, String secondName, String patronymic,
                UserType userType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.userType = userType;
    }

    public User() {
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", userType=" + userType.getName() +
                '}';
    }
}
