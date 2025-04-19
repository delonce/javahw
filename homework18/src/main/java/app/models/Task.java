package app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс - сущность объектов таблицы tasks
 * @author Danma
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /** Поле - идентификатор записи в базе данных */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Поле, содержащее название задания */
    @Column(name = "name")
    private String name;

    /** Поле, содержащее описание задания */
    @Column(name = "description")
    private String description;

    /** Поле, содержащее время и дату установки задания */
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}