package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Entity.Student;
import com.example.webcompetition.Entity.Teacher;
import com.example.webcompetition.Repository.CompetitionRepository;
import com.example.webcompetition.Repository.StudentRepository;
import com.example.webcompetition.Repository.TeacherRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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
        competitionRepository.save(competition);
//        if (competition.getStudent1()!="")
//        {
//            Student student=new Student();
//            student.setCompetitionId(competition.getId());
//            student.setName(competition.getStudent1());
//            studentRepository.save(student);
//        }
//        if (competition.getStudent2()!="")
//        {
//            Student student=new Student();
//            student.setCompetitionId(competition.getId());
//            student.setName(competition.getStudent2());
//            studentRepository.save(student);
//        }
//        if (competition.getStudent3()!="")
//        {
//            Student student=new Student();
//            student.setCompetitionId(competition.getId());
//            student.setName(competition.getStudent3());
//            studentRepository.save(student);
//        }
//        if (competition.getTeacher1()!="")
//        {
//            Teacher teacher=new Teacher();
//            teacher.setCompetitionId(competition.getId());
//            teacher.setName(competition.getTeacher1());
//            teacherRepository.save(teacher);
//        }
//        if (competition.getTeacher2()!="")
//        {
//            Teacher teacher=new Teacher();
//            teacher.setCompetitionId(competition.getId());
//            teacher.setName(competition.getTeacher2());
//            teacherRepository.save(teacher);
//        }
        return competitionRepository.findAll();
    }

    @DeleteMapping("/competition")
    @ResponseBody
    @Transactional
    public List<Competition> deletecompetition(@RequestBody Map<String,Long> map)
    {
        studentRepository.deleteAllByCompetitionId(map.get("id"));
        teacherRepository.deleteAllByCompetitionId(map.get("id"));
        competitionRepository.deleteById(map.get("id"));
        return competitionRepository.findAll();
    }

    @PostMapping("/competition/query")
    @ResponseBody
    public List<Competition> querytest(@RequestBody QCompetition qCompetition)
    {
        List<Competition> competitions= competitionRepository.findtest(qCompetition.getYear(),qCompetition.getGrade(),qCompetition.getName(),qCompetition.getStudent(),qCompetition.getTeacher());
        return competitions;
    }

}
