package com.userservice.repository;

import com.userservice.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepo extends JpaRepository<Classroom,Long> {
    @Query(value = "select cu.uid from classroom c,class_user cu where c.id=cu.class_id and c.id=:id",nativeQuery = true)
    List<Long> getStudent(@Param("id") long id);
    @Query(value = "insert into class_user values (:cid,:uid)",nativeQuery = true)
    void addToClass(@Param("cid") long cid,@Param("uid") long uid);

    @Query(value = "select cr.* from classroom cr,course c,users u where c.id=cr.course_id and c.uid=u.id and u.id=:id",nativeQuery = true)
    List<Classroom> getAllClass(@Param("id") long id);
}
