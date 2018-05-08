package com.example.webcompetition.Repository;

import com.example.webcompetition.Entity.Competition;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition,Long>
{
    List<Competition> findAll();
}
