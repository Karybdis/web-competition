package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition,Long>
{
    List<Competition> findAll();

    @Query(value = "select * from competition  where ((case when ?1='' then 1 end ) or year=?1) and ((case when ?2='' then 1 end ) or grade=?2) and ((case when ?3='' then 1 end ) or name=?3) and ((case when ?4='' then 1 end ) or student1=?4 or student2=?4 or student3=?4) and ((case when ?5='' then 1 end ) or teacher1=?5 or teacher2=?5) ",nativeQuery = true)
    List<Competition> findtest(String year,String grade,String name,String student,String teacher);
}