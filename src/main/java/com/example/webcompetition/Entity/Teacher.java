package com.example.webcompetition.Entity;

import javax.persistence.*;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name="competition_id")
    private Long competitionId;
    private String academy;
    @Column(name="teacher_id")
    private String teacherId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getAcademy()
    {
        return academy;
    }

    public void setAcademy(String academy)
    {
        this.academy = academy;
    }

    public String getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(String teacherId)
    {
        this.teacherId = teacherId;
    }
}
