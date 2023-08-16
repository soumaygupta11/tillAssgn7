package com.example.newsUser.model.repository;

import com.example.newsUser.model.entity.Call;
import com.example.newsUser.model.entity.EndpointCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CRepository extends JpaRepository<Call, Long> {

    @Query(value = "SELECT user_id FROM calls WHERE TO_TIMESTAMP(created_at, 'YYYY-MM-DD HH24:MI:SS') >= NOW() - INTERVAL '7 days' GROUP BY user_id ORDER BY COUNT(*) DESC;", nativeQuery = true)
    List<Long> getTopUsers();

    @Query(value = "SELECT response FROM calls WHERE request = :request LIMIT 1;", nativeQuery = true)
    String getResponse(@Param("request") String request);


    @Query(value = "SELECT timetaken FROM calls WHERE request= :request LIMIT 1;", nativeQuery = true)
    String getTimeTaken(@Param("request") String request);

    @Query(value = "SELECT endpoint, count(*)  FROM ( SELECT DISTINCT endpoint,request FROM calls ) AS t1  GROUP BY endpoint;", nativeQuery = true)
    List<EndpointCount> getEndpointCount();
}

