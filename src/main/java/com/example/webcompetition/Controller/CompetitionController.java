package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Entity.Student;
import com.example.webcompetition.Entity.Teacher;
import com.example.webcompetition.Repository.CompetitionRepository;
import com.example.webcompetition.Repository.StudentRepository;
import com.example.webcompetition.Repository.TeacherRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CompetitionRepository competitionRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

//    @GetMapping("/competition")
//    public String competition()
//    {
//        return "competition";
//
//    }

    @GetMapping("/competition/list")                 //列出所有比赛
    @ResponseBody
    public List<Competition> listcompetition()
    {
        return competitionRepository.findAll();
    }

    @PostMapping("/competition")                     //添加比赛
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

    @DeleteMapping("/competition")                      //删除比赛
    @ResponseBody
    @Transactional
    public List<Competition> deletecompetition(@RequestBody Map<String,Long> map)
    {
//        studentRepository.deleteAllByCompetitionId(map.get("id"));
//        teacherRepository.deleteAllByCompetitionId(map.get("id"));
        competitionRepository.deleteById(map.get("id"));
        return competitionRepository.findAll();
    }

    @PostMapping("/competition/query")                  //根据要求查询比赛
    @ResponseBody
    public List<Competition> querycompetition(@RequestBody QCompetition qCompetition)
    {
        List<Competition> competitions= competitionRepository.findtest(qCompetition.getYear(),qCompetition.getGrade(),qCompetition.getName(),qCompetition.getStudent(),qCompetition.getTeacher());
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

    @PostMapping("/competition/teacher")
    @ResponseBody
    public List<Teacher> PutTeacherIntoDB(@RequestParam("file") MultipartFile file)
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
                        Teacher teacher=new Teacher();
                        XSSFCell xssfCell1=xssfRow.getCell(0);
                        XSSFCell xssfCell2=xssfRow.getCell(1);
                        XSSFCell xssfCell3=xssfRow.getCell(2);
                        teacher.setName(String.valueOf(xssfCell1));
                        teacher.setTeacherId(decimalFormat.format(xssfCell2.getNumericCellValue()));
                        teacher.setAcademy(String.valueOf(xssfCell3));
                        teacherRepository.save(teacher);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return teacherRepository.findAll();
    }

    @PostMapping("/competition/student")
    @ResponseBody
    public List<Student> PutStudentIntoDB(@RequestParam("file") MultipartFile file)
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
                        Student student=new Student();
                        XSSFCell xssfCell1=xssfRow.getCell(0);
                        XSSFCell xssfCell2=xssfRow.getCell(1);
                        XSSFCell xssfCell3=xssfRow.getCell(2);
                        XSSFCell xssfCell4=xssfRow.getCell(3);
                        XSSFCell xssfCell5=xssfRow.getCell(4);
                        student.setName(String.valueOf(xssfCell1));
                        student.setClassId(decimalFormat.format(xssfCell2.getNumericCellValue()));
                        student.setStudentId(decimalFormat.format(xssfCell3.getNumericCellValue()));
                        student.setAcademy(String.valueOf(xssfCell4));
                        student.setMajor(String.valueOf(xssfCell5));
                        studentRepository.save(student);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return studentRepository.findAll();
    }
}
