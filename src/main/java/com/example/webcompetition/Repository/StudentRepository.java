package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long>
{
    @Modifying
    void deleteAllByCompetitionId(Long competition);
}
