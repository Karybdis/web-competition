package com.example.webcompetition.Entity;

import javax.persistence.*;

@Entity
public class Competition
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
//    private String grade;
//    private String name;
    @Column(name="grade_large")
    private String gradeLarge;
    @Column(name="grade_small")
    private String gradeSmall;
    @Column(name="name_large")
    private String nameLarge;
    @Column(name="name_detail")
    private String nameDetail;
//    private String student1;
//    private String student2;
//    private String student3;
    private String student;
//    private String teacher1;
//    private String teacher2;
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

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

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

//    public String getGrade() {
//        return grade;
//    }
//
//    public void setGrade(String grade) {
//        this.grade = grade;
//    }

//    public String getStudent1()
//    {
//        return student1;
//    }
//
//    public void setStudent1(String student1)
//    {
//        this.student1 = student1;
//    }
//
//    public String getStudent2()
//    {
//        return student2;
//    }
//
//    public void setStudent2(String student2)
//    {
//        this.student2 = student2;
//    }
//
//    public String getStudent3()
//    {
//        return student3;
//    }
//
//    public void setStudent3(String student3)
//    {
//        this.student3 = student3;
//    }
//
//    public String getTeacher1()
//    {
//        return teacher1;
//    }
//
//    public void setTeacher1(String teacher1)
//    {
//        this.teacher1 = teacher1;
//    }
//
//    public String getTeacher2()
//    {
//        return teacher2;
//    }
//
//    public void setTeacher2(String teacher2)
//    {
//        this.teacher2 = teacher2;
//    }

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
