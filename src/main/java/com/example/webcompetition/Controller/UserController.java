package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Student;
import com.example.webcompetition.Entity.Teacher;
import com.example.webcompetition.Repository.StudentRepository;
import com.example.webcompetition.Repository.TeacherRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Controller
public class UserController
{
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @PostMapping("/teacher")
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

    @PostMapping("/student")
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

    @PostMapping("/student/info")
    @ResponseBody
    public Student StudentInfo(@RequestParam("name") String name)
    {
        Student student=studentRepository.findByName(name);
        return student;
    }

    @PostMapping("/teacher/info")
    @ResponseBody
    public Teacher TeacherInfo(@RequestParam("name") String name)
    {
        Teacher teacher=teacherRepository.findByName(name);
        return teacher;
    }
}
