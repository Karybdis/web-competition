package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Teacher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher,Long>
{
    @Modifying
    void deleteAllByCompetitionId(Long id);
}
