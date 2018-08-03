package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Repository.CompetitionRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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

    @GetMapping("/competition/list")                 //列出第一页比赛
    @ResponseBody
    public List<Competition> ListCompetition()
    {
        Pageable pageable=PageRequest.of(0,15, Sort.Direction.ASC,"id");
        Page<Competition> competitions=competitionRepository.findAll(pageable);
        return competitions.getContent();
    }

    @GetMapping("/competition/page/{page}")
    @ResponseBody
    public List<Competition> PageCompetition(@PathVariable int page)
    {
        Pageable pageable=PageRequest.of(page,15, Sort.Direction.ASC,"id");
        Page<Competition> competitions=competitionRepository.findAll(pageable);
        return competitions.getContent();
    }

    @GetMapping("/competition/getpage")
    @ResponseBody int GetPage()
    {
        Pageable pageable=PageRequest.of(0,15, Sort.Direction.ASC,"id");
        Page<Competition> competitions=competitionRepository.findAll(pageable);
        return competitions.getTotalPages();
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
        return ListCompetition();
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
        return ListCompetition();
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

    @PostMapping("/competition/excel")
    @ResponseBody
    public List<Competition> PutCompetitionIntoDB(@RequestParam("file") MultipartFile file)
    {
        try
        {
            DecimalFormat decimalFormat=new DecimalFormat("0");
            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(file.getInputStream());
            for (int sheetNum=0;sheetNum<xssfWorkbook.getNumberOfSheets();sheetNum++)
            {
                XSSFSheet xssfSheet=xssfWorkbook.getSheetAt(sheetNum);
                for (int rowNum=1;rowNum<=xssfSheet.getLastRowNum();rowNum++)
                {
                    XSSFRow xssfRow=xssfSheet.getRow(rowNum);
                    if (xssfRow!=null)
                    {
                        Competition competition=new Competition();
                        XSSFCell xssfCell1=xssfRow.getCell(0);
                        XSSFCell xssfCell2=xssfRow.getCell(1);
                        XSSFCell xssfCell3=xssfRow.getCell(2);
                        XSSFCell xssfCell4=xssfRow.getCell(3);
                        XSSFCell xssfCell5=xssfRow.getCell(4);
                        XSSFCell xssfCell6=xssfRow.getCell(5);
                        XSSFCell xssfCell7=xssfRow.getCell(6);
                        XSSFCell xssfCell8=xssfRow.getCell(7);
                        competition.setYear(decimalFormat.format(xssfCell1.getNumericCellValue()));
                        competition.setGradeLarge(String.valueOf(xssfCell2));
                        competition.setGradeSmall(String.valueOf(xssfCell3));
                        competition.setNameLarge(String.valueOf(xssfCell4));
                        competition.setNameDetail(String.valueOf(xssfCell5));
                        competition.setStudent(String.valueOf(xssfCell6));
                        competition.setTeacher(String.valueOf(xssfCell7));
                        competition.setBelong(String.valueOf(xssfCell8));
                        competitionRepository.save(competition);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       return  ListCompetition();
    }
}
