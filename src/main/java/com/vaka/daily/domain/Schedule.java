package com.vaka.daily.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "schedule_name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({"schedules"})
    private User user;

    @OneToMany(mappedBy = "schedule")
    @JsonManagedReference
    private List<Task> tasks;

    public Schedule() {
    }

    public Schedule(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
