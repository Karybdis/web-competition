package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long>
{
    void deleteByCompetitionId(Long id);
}
