package com.userservice.repository;

import com.userservice.model.Discussion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    @Query(value = "select d.*,u.username from discussion d, users u where d.uid=u.id and d.course_id=:course_id",
            nativeQuery = true,
    countQuery = "select count(d.*) from discussion d where d.course_id=:course_id")
    List<Map<String,Object>> findByCourseId(@Param("course_id") long course_id, Pageable pageable);

}
