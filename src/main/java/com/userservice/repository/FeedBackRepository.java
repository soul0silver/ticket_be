package com.userservice.repository;

import com.userservice.model.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    @Query(value = "SELECT * FROM feedback f WHERE od_id IN (SELECT id FROM order_detail WHERE course_id = :course_id) ORDER BY f.createat DESC", nativeQuery = true)
    List<FeedBack> getAllCourseId(@PathVariable Long course_id);
    @Query(value = "SELECT * FROM feedback f WHERE od_id IN (SELECT id FROM order_detail WHERE course_id = :course_id) ORDER BY f.createat DESC", nativeQuery = true)
    Page<FeedBack> getFeedBacksByCourseId(@PathVariable Long course_id, Pageable pageable);
    Page<FeedBack> getAllByStar(org.springframework.data.domain.Pageable pageable,int star);
    Page<FeedBack> getFeedBacksByIdNotNullOrderByCreateatDesc(Pageable pageable);
    Page<FeedBack> getFeedBacksByIdNotNullOrderByCreateatAsc(Pageable pageable);
}
