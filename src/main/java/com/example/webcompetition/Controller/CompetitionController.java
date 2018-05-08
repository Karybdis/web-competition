package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.Student;
import com.example.webcompetition.Entity.Teacher;
import com.example.webcompetition.Repository.CompetitionRepository;
import com.example.webcompetition.Repository.StudentRepository;
import com.example.webcompetition.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CompetitionController
{
    @Autowired
    CompetitionRepository competitionRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/competition")
    @ResponseBody
    public List<Competition> listcompetition()
    {
        return competitionRepository.findAll();
    }

    @PostMapping("/competition")
    @ResponseBody
    public List<Competition> addcompetition(@RequestBody Competition competition)
    {
        Student student=new Student();
        Teacher teacher=new Teacher();
        student.setCompetitionId(competition.getId());
        if (competition.getStudent1()!="")
        {
            student.setName(competition.getStudent1());
            studentRepository.save(student);
        }
        if (competition.getStudent2()!="")
        {
            student.setName(competition.getStudent2());
            studentRepository.save(student);
        }
        if (competition.getStudent3()!="")
        {
            student.setName(competition.getStudent3());
            studentRepository.save(student);
        }
        teacher.setCompetitionId(competition.getId());
        if (competition.getTeacher1()!="")
        {
            teacher.setName(competition.getTeacher1());
            teacherRepository.save(teacher);
        }
        if (competition.getTeacher2()!="")
        {
            teacher.setName(competition.getTeacher2());
            teacherRepository.save(teacher);
        }
        competitionRepository.save(competition);
        return competitionRepository.findAll();
    }

    @DeleteMapping("/competition")
    @ResponseBody
    public List<Competition> deletecompetition(@RequestBody Map<String,Long> map)
    {
        studentRepository.deleteByCompetitionId(map.get("id"));
        teacherRepository.deleteByCompetitionId(map.get("id"));
        competitionRepository.deleteById(map.get("id"));
        return competitionRepository.findAll();
    }

}
