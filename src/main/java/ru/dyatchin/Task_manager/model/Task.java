package ru.dyatchin.Task_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Сущность задача
 */
@Data
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * id задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Дата создания
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "createdDate")
    private LocalDate createdDate;

    /**
     * Дата дедлайна
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deadlineDate")
    private LocalDate deadlineDate;

    /**
     * Название
     */
    @Column(name = "title")
    private String title;

    /**
     * Содержание задачи
     */
    @Column(name = "content")
    private String content;

    /**
     * Комментарий
     */
    @Column(name = "comment")
    private String comment;

    /**
     * Статус задачи
     */
    @Column(name = "status")
    private Status status;

    /**
     * Автор задачи связь с User id
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Исполнитель задачи связь с User id
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private User executor;

}