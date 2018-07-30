package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher,Long>
{
//    @Modifying
//    void deleteAllByCompetitionId(Long id);
    List<Teacher> findAll();
    Teacher findByName(String name);
}
