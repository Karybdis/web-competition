package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends CrudRepository<Competition,Long>
{
//    List<Competition> findAll();
    Page<Competition> findAll(Pageable pageable);
    Optional<Competition> findById(Long id);
    @Query(value = "select * from competition  where ((case when ?1='' then 1 end ) or year like %?1%) and ((case when ?2='' then 1 end ) or grade_large like %?2%) and ((case when ?3='' then 1 end ) or grade_small like %?3%) and ((case when ?4='' then 1 end ) or name_large like %?4%) and ((case when ?5='' then 1 end ) or name_detail like %?5%) and ((case when ?6='' then 1 end ) or student like %?6%) and ((case when ?7='' then 1 end ) or teacher like %?7%) and ((case when ?8='' then 1 end ) or belong like %?8%) ",nativeQuery = true)
    List<Competition> findtest(String year,String gradeLarge,String gradeSmall,String nameLarge,String nameDetail,String student,String teacher,String belong);
    @Query(value="select max(id) from competition",nativeQuery = true)
    Long getMaxId();
}