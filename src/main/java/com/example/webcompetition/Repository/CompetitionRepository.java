package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition,Long>
{
    List<Competition> findAll();

//    @Query(value = "select * from competition  where ((case when ?1='' then 1 end ) or year like %?1%) and ((case when ?2='' then 1 end ) or grade like %?2%) and ((case when ?3='' then 1 end ) or name like %?3%) and ((case when ?4='' then 1 end ) or student1 like %?4% or student2 like %?4% or student3 like %?4%) and ((case when ?5='' then 1 end ) or teacher1 like %?5% or teacher2 like %?5%) ",nativeQuery = true)
//    List<Competition> findtest(String year,String grade,String name,String student,String teacher);
    @Query(value = "select * from competition  where ((case when ?1='' then 1 end ) or year like %?1%) and ((case when ?2='' then 1 end ) or grade_large like %?2%) and ((case when ?3='' then 1 end ) or grade_small like %?3%) and ((case when ?4='' then 1 end ) or name_large like %?4%) and ((case when ?5='' then 1 end ) or name_detail like %?5%) and ((case when ?6='' then 1 end ) or student like %?6%) and ((case when ?7='' then 1 end ) or teacher like %?7%) ",nativeQuery = true)
    List<Competition> findtest(String year,String gradeLarge,String gradeSmall,String nameLarge,String nameDetail,String student,String teacher);
}