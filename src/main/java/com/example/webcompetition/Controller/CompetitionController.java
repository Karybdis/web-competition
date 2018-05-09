package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Entity.Student;
import com.example.webcompetition.Entity.Teacher;
import com.example.webcompetition.Repository.CompetitionRepository;
import com.example.webcompetition.Repository.StudentRepository;
import com.example.webcompetition.Repository.TeacherRepository;
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
        if (competition.getStudent1()!="")
        {
            Student student=new Student();
            student.setCompetitionId(competition.getId());
            student.setName(competition.getStudent1());
            studentRepository.save(student);
        }
        if (competition.getStudent2()!="")
        {
            Student student=new Student();
            student.setCompetitionId(competition.getId());
            student.setName(competition.getStudent2());
            studentRepository.save(student);
        }
        if (competition.getStudent3()!="")
        {
            Student student=new Student();
            student.setCompetitionId(competition.getId());
            student.setName(competition.getStudent3());
            studentRepository.save(student);
        }
        if (competition.getTeacher1()!="")
        {
            Teacher teacher=new Teacher();
            teacher.setCompetitionId(competition.getId());
            teacher.setName(competition.getTeacher1());
            teacherRepository.save(teacher);
        }
        if (competition.getTeacher2()!="")
        {
            Teacher teacher=new Teacher();
            teacher.setCompetitionId(competition.getId());
            teacher.setName(competition.getTeacher2());
            teacherRepository.save(teacher);
        }
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
    public List<Competition> competitionquery(@RequestBody QCompetition qCompetition)
    {
//        int i=0;
//        List<Map<String,Object>> maps=new ArrayList<>();
//        List<Competition> competitions=competitionRepository.findAll();
//        for (i=0;i<competitions.size();i++)
//        {
//            Map<String,Object> map=new HashMap<>();
//            map.put("id",competitions.get(i).getId());
//            map.put("year",competitions.get(i).getYear());
//            map.put("grade",competitions.get(i).getGrade());
//            map.put("name",competitions.get(i).getName());
//            map.put("student1",competitions.get(i).getStudent1());
//            map.put("student2",competitions.get(i).getStudent2());
//            map.put("student3",competitions.get(i).getStudent3());
//            map.put("teacher1",competitions.get(i).getTeacher1());
//            map.put("teacher2",competitions.get(i).getTeacher2());
//            map.put("bool","1");
//            maps.add(map);
//        }
//        if (qCompetition.getYear()!="")
//            for (i=0;i<maps.size();i++)
//                if (!maps.get(i).get("year").equals(qCompetition.getYear())) maps.get(i).put("bool",0);
//        if (qCompetition.getGrade()!="")
//            for (i=0;i<maps.size();i++)
//                if (!maps.get(i).get("grade").equals(qCompetition.getGrade())) maps.get(i).put("bool",0);
//        if (qCompetition.getName()!="")
//            for (i=0;i<maps.size();i++)
//                if (!maps.get(i).get("name").equals(qCompetition.getName())) maps.get(i).put("bool",0);
//        if (qCompetition.getStudent()!="")
//            for (i=0;i<maps.size();i++)
//                if (!maps.get(i).get("student1").equals(qCompetition.getStudent()) &&
//                        maps.get(i).get("student2").equals(qCompetition.getStudent()) &&
//                        maps.get(i).get("student3").equals(qCompetition.getStudent()) ) maps.get(i).put("bool",0);
//        if (qCompetition.get)

    }
}
