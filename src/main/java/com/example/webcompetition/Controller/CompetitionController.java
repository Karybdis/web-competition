package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static String UPLOADED_FOLDER = "/home/cheng/下载/";

    @Autowired
    CompetitionRepository competitionRepository;
//    @Autowired
//    StudentRepository studentRepository;
//    @Autowired
//    TeacherRepository teacherRepository;

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
//        studentRepository.deleteAllByCompetitionId(map.get("id"));
//        teacherRepository.deleteAllByCompetitionId(map.get("id"));
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

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes)
    {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/competition";
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/competition";
    }
}
