package com.example.webcompetition.Entity;

import javax.persistence.*;

@Entity
public class Competition
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    @Column(name="grade_large")
    private String gradeLarge;
    @Column(name="grade_small")
    private String gradeSmall;
    @Column(name="name_large")
    private String nameLarge;
    @Column(name="name_detail")
    private String nameDetail;
    private String student;
    private String teacher;
    private String belong;
    private String status;
    private String certificate;

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificate()
    {
        return certificate;
    }

    public void setCertificate(String certificate)
    {
        this.certificate = certificate;
    }

    public Long getId()
    {
        return id;
    }

    public String getGradeLarge()
    {
        return gradeLarge;
    }

    public void setGradeLarge(String gradeLarge)
    {
        this.gradeLarge = gradeLarge;
    }

    public String getGradeSmall()
    {
        return gradeSmall;
    }

    public void setGradeSmall(String gradeSmall)
    {
        this.gradeSmall = gradeSmall;
    }

    public String getNameLarge()
    {
        return nameLarge;
    }

    public void setNameLarge(String nameLarge)
    {
        this.nameLarge = nameLarge;
    }

    public String getNameDetail()
    {
        return nameDetail;
    }

    public void setNameDetail(String nameDetail)
    {
        this.nameDetail = nameDetail;
    }

    public String getStudent()
    {
        return student;
    }

    public void setStudent(String student)
    {
        this.student = student;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }
}
