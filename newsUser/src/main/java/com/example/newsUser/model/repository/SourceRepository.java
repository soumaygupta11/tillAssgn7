package com.example.newsUser.model.repository;

import com.example.newsUser.contract.sourceWrapper.Source;
import com.example.newsUser.model.entity.PrefSources;
import com.example.newsUser.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<PrefSources, Long> {
}
