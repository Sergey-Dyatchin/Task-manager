package ru.dyatchin.Task_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "createdDate")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deadlineDate")
    private LocalDate deadlineDate;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Status status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private User executor;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}