package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class CompetitionController
{
    private static String UPLOADED_FOLDER = "/home/certificate/";      //存证书图片的地址
//    private static String UPLOADED_FOLDER = "/home/cheng/下载/";
    @Autowired
    CompetitionRepository competitionRepository;
//    @Autowired
//    StudentRepository studentRepository;
//    @Autowired
//    TeacherRepository teacherRepository;

//    @GetMapping("/competition")
//    public String competition()
//    {
//        return "competition";
//
//    }

    @GetMapping("/competition/list")                 //列出所有比赛
    @ResponseBody
    public List<Competition> ListCompetition()
    {
        return competitionRepository.findAll();
    }

    @PostMapping("/competition")                     //添加比赛
    @ResponseBody
    public List<Competition> AddCompetition(@RequestBody Competition competition)
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
//            student.setName(competition.getStudent2()
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

    @DeleteMapping("/competition")                      //删除比赛
    @ResponseBody
    @Transactional
    public List<Competition> DeleteCompetition(@RequestBody Map<String,Long> map)
    {
//        studentRepository.deleteAllByCompetitionId(map.get("id"));
//        teacherRepository.deleteAllByCompetitionId(map.get("id"));
        Competition competition=competitionRepository.findById(map.get("id")).get();
        Path path=Paths.get(UPLOADED_FOLDER+competition.getCertificate());
        try
        {
            Files.delete(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        competitionRepository.deleteById(map.get("id"));

        return competitionRepository.findAll();
    }

    @PostMapping("/competition/query")                  //根据要求查询比赛
    @ResponseBody
    public List<Competition> QueryCompetition(@RequestBody QCompetition qCompetition)
    {
//        List<Competition> competitions= competitionRepository.findtest(qCompetition.getYear(),qCompetition.getGrade(),qCompetition.getName(),qCompetition.getStudent(),qCompetition.getTeacher());
        List<Competition> competitions= competitionRepository.findtest(qCompetition.getYear(),qCompetition.getGradeLarge(),qCompetition.getGradeSmall(),qCompetition.getNameLarge(),qCompetition.getNameDetail(),qCompetition.getStudent(),qCompetition.getTeacher());
        return competitions;
    }

    @PostMapping("/competition/upload")                 //上传证书图片
    @ResponseBody
    public String singleFileUpload(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()) { return "redirect:/competition"; }
        try
        {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file.getOriginalFilename();
    }
}
