package com.example.businesssource.repositories;


import com.example.businesssource.entities.BusinessNameSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessNameSuggestionRepository extends JpaRepository<BusinessNameSuggestion, Long> {

}
