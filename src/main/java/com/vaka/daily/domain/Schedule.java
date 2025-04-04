package com.vaka.daily.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vaka.daily.domain.serialization.ScheduleDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "schedule")
@JsonDeserialize(using = ScheduleDeserializer.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "schedule_name", nullable = false, length = 100)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({"schedules"})
    private User user;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks;

    public Schedule() {
    }

    public Schedule(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
