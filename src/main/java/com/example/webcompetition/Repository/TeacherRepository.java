package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Long>
{
    void deleteByCompetitionId(Long id);
}
