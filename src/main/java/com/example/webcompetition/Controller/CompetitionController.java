package com.example.webcompetition.Controller;

import com.example.webcompetition.Entity.Competition;
import com.example.webcompetition.Entity.QCompetition;
import com.example.webcompetition.Repository.CompetitionRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CompetitionController
{
    private static String UPLOADED_FOLDER = "/home/certificate/";      //存文件的地址
//    private static String UPLOADED_FOLDER = "/home/cheng/文档/";
    @Autowired
    CompetitionRepository competitionRepository;
//    @Autowired
//    StudentRepository studentRepository;
//    @Autowired
//    TeacherRepository teacherRepository;

    public void deldir(File folder)             //递归删除文件夹
    {
        if (folder.isDirectory())
        {
            File[] files=folder.listFiles();
            for (File file:files)
                deldir(file);
        }
        folder.delete();
    }

    public void uploadfile(MultipartFile file,File folder)    //上传文件
    {
        try
        {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder.getPath() + "/" +file.getOriginalFilename());
            Files.write(path, bytes);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/competition")
    public String competition()
    {
        return "competition.html";
    }

    @GetMapping("/addnew")
    public String addnew()
    {
        return "addnew.html";
    }

    @GetMapping("/competition/page/{page}")         //返回某页(page)数据
    @ResponseBody
    public List<Competition> PageCompetition(@PathVariable int page)
    {
        Pageable pageable=PageRequest.of(page,15, Sort.Direction.ASC,"id");     //每页15个数据，依据ID正序排序
        Page<Competition> competitions=competitionRepository.findAll(pageable);
        return competitions.getContent();
    }

    @GetMapping("/competition/getpage")             //返回页数
    @ResponseBody int GetPage()
    {
        Pageable pageable=PageRequest.of(0,15, Sort.Direction.ASC,"id");
        Page<Competition> competitions=competitionRepository.findAll(pageable);
        return competitions.getTotalPages();
    }

    @PostMapping("/competition")                     //添加比赛
    @ResponseBody
    public String AddCompetition(@RequestBody Competition competition)
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
        return "Success";
    }

    @DeleteMapping("/competition")                      //删除比赛
    @ResponseBody
    @Transactional
    public String DeleteCompetition(@RequestBody Map<String,Long> map)
    {
//        studentRepository.deleteAllByCompetitionId(map.get("id"));
//        teacherRepository.deleteAllByCompetitionId(map.get("id"));
//        Competition competition=competitionRepository.findById(map.get("id")).get();
//        Path path=Paths.get(UPLOADED_FOLDER+competition.getCertificate());
//        try
//        {
//            Files.delete(path);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        File folder=new File("/home/cheng/文档/"+map.get("id"));   //本地测试用
        File folder=new File("/home/certificate/"+map.get("id"));     /*同时删除该比赛*/
        deldir(folder);                                                 /*对应的所有文件*/
        competitionRepository.deleteById(map.get("id"));
        return "Success";
    }

    @PostMapping("/competition/query")                  //根据要求查询比赛
    @ResponseBody
    public List<Competition> QueryCompetition(@RequestBody QCompetition qCompetition)
    {
        List<Competition> competitions= competitionRepository.findtest(qCompetition.getYear(),qCompetition.getGradeLarge(),qCompetition.getGradeSmall(),qCompetition.getNameLarge(),qCompetition.getNameDetail(),qCompetition.getStudent(),qCompetition.getTeacher(),qCompetition.getBelong());
        return competitions;
    }

    @PostMapping("/competition/upload")                 //上传文件
    @ResponseBody
    public String FileUpload(@RequestParam("file") MultipartFile[] files)
    {
        Long id=competitionRepository.getIncId();
        File folder=new File("/home/cheng/文档/"+id);        //本地测试用
//        File folder=new File("/home/certificate/"+id);
        if (!folder.exists())
            folder.mkdir();                                   //创建存该比赛文件的文件夹
        if (files!=null && files.length>0)
        {
            for (MultipartFile file:files)
                uploadfile(file, folder);
        }
        //return file.getOriginalFilename();
        return "success";
    }

    @GetMapping("/competition/download")                  //下载文件
    @ResponseBody
    public void FileDownload(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "filename") String filename,
                             HttpServletResponse response) throws IOException
    {
        File file=new File("/home/certificate/"+id+"/"+filename);
//        File file=new File("/home/cheng/文档/"+id+"/"+filename);      //本地测试用
        InputStream inputStream=new FileInputStream(file);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        FileCopyUtils.copy(inputStream,response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
    }

    @GetMapping("/competition/filename")                //获取某比赛所有相关附件文件名字
    @ResponseBody
    public List<String> GetFileName(@RequestParam Long id)
    {
        List<String> filename=new ArrayList<>();
//        File folder=new File("/home/cheng/文档/"+id);   //本地测试用
        File folder=new File("/home/certificate/"+id);
        File[] files=folder.listFiles();
        for (File file:files)
            filename.add(file.getName());
        return filename;
    }

    @PostMapping("/competition/excel")                  //导入比赛
    @ResponseBody
    public String PutCompetitionIntoDB(@RequestParam("file") MultipartFile file)
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
        return  "Success";
    }
}
