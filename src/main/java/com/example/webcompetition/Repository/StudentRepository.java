package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student,Long>
{
//    @Modifying
//    void deleteAllByCompetitionId(Long competition);
    List<Student> findAll();
    Student findByName(String name);
}
