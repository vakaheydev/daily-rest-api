package com.vaka.daily.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Task", uniqueConstraints = {@UniqueConstraint(columnNames = {"task_name", "task_description",
        "id_schedule"})})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "task_name", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "task_description", nullable = false, length = 100)
    private String description;

    @Column(name = "task_deadline")
    private LocalDateTime deadline;

    @Column(name = "task_status")
    private Boolean status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_schedule")
    @JsonBackReference
    private Schedule schedule;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_task_type")
    private TaskType taskType;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                ", schedule=(" + schedule.getId() + "," + schedule.getName() +
                "), taskType=(" + taskType.toString() +
                ")}";
    }
}
