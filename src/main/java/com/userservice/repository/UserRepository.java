package com.userservice.repository;

import com.userservice.model.User;
import com.userservice.model.response.TeacherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    @Query(value = "select r.rolename from user_role us," +
            "users u, " +
            "roles r " +
            "where us.uid=u.id and r.id=us.rid and u.username=:username",nativeQuery = true)
    List<String> findRoles(@Param("username") String username);
    User findByUsername(String username);
    User findByEmail(String email);
    @Query(value = "select count(*) from users where id not in (1)",nativeQuery = true)
    Long countUserById();

    @Query(value = "select count(*) from users where active = true and id not in (1)",nativeQuery = true)
    Long countActive();
    @Query(value = "select count(*) from users where active = false and id not in (1)",nativeQuery = true)
    long countOff();

    @Query(value = "with a as\n" +
            "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
            "     b as \n" +
            "         (select * from a where uid not in (select uid from a where rid = 2))\n" +
            "select u.* from b,\n" +
            "     users u\n" +
            "where b.uid = u.id ",
            countQuery = "with a as\n" +
                    "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
                    "     b as \n" +
                    "         (select * from a where uid not in (select uid from a where rid = 2))\n" +
                    "select count(u.id) from b,\n" +
                    "     users u\n" +
                    "where b.uid = u.id ", nativeQuery = true)
    Page<User> findAllStudent(Pageable pageable);

    @Query(value = "with a as\n" +
            "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
            "     b as (select od.course_id,c.uid,sum(od.price) as total\n" +
            "           from order_detail od,course c,orders o\n" +
            "           where od.course_id = c.id and od.order_id=o.id and o.status=1\n" +
            "           group by od.course_id),\n" +
            "     c as (select u.* from a,\n" +
            "                           users u\n" +
            "           where a.uid=u.id and  a.rid=2)\n" +
            "select c.* from c left join b on b.uid=c.id group by c.id",
            countQuery = "with a as\n" +
                    "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
                    "     b as (select od.course_id,c.uid,sum(od.price) as total\n" +
                    "           from order_detail od,course c,orders o\n" +
                    "           where od.course_id = c.id and od.order_id=o.id and o.status=1\n" +
                    "           group by od.course_id),\n" +
                    "     c as (select u.* from a,\n" +
                    "                           users u\n" +
                    "           where a.uid=u.id and  a.rid=2)\n" +
                    "select count(*) from c left join b on b.uid=c.id group by c.id",nativeQuery = true)
    Page<User> findAllTeacher(Pageable pageable);

//    @Query(value = "with a as\n" +
//            "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
//            "     b as (select od.course_id,c.uid,sum(od.price) as total\n" +
//            "           from order_detail od,course c,orders o\n" +
//            "           where od.course_id = c.id and od.order_id=o.id and o.status=1\n" +
//            "           group by od.course_id),\n" +
//            "     c as (select u.* from a,\n" +
//            "                           users u\n" +
//            "           where a.uid=u.id and  a.rid=2)\n" +
//            "select c.*,sum(b.total) as earn from c left join b on b.uid=c.id group by c.id order by earn desc",
//            nativeQuery = true)
//    List<Map<String, Object>> findAllTeacher(Pageable pageable);

    @Query(value = "with a as\n" +
            "         (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
            "     b as (select od.course_id,c.uid,sum(od.price) as total\n" +
            "           from order_detail od,course c,orders o\n" +
            "           where od.course_id = c.id and od.order_id=o.id and o.status=1\n" +
            "           group by od.course_id),\n" +
            "     c as (select u.* from a,\n" +
            "                           users u\n" +
            "           where a.uid=u.id and  a.rid=2)\n" +
            "select sum(b.total) as earn from c left join b on b.uid=c.id group by c.id",
            nativeQuery = true)
    List<Double> findEarnOfTeacher(Pageable pageable);

    @Query(value = "with a as (select * from user_role where uid not in (select uid from user_role where rid in (1))),\n" +
            "b as (select * from a where uid not in (select uid from a where rid = 2))\n" +
            "select count(u.id) as quantityOfStudent from b, users u where b.uid = u.id", nativeQuery = true)
    Integer countStudent();

    @Query(value = "with a as (select * from user_role where uid not in (select uid from user_role where rid in (1)))\n" +
            "select count(u.id) as quantityOfTeacher from a, users u where a.uid = u.id and a.rid=2", nativeQuery = true)
    Integer countTeacher();

    @Query(value = "with a as ( select * from users u where year(create_at) = year(current_date))\n" +
            "select month(create_at) as month,count(id) as assign from a group by month(create_at)", nativeQuery = true)
    List<Map<String, Object>> quantityUserRegisByMonth();

    @Query(value = "with a as (select od.*,o.create_at from order_detail od, course c, users u,orders o\n" +
            "where u.id=c.uid and od.course_id=c.id and o.id=od.order_id and YEAR(o.create_at)=2024)\n" +
            "select MONTH(a.create_at) as month,sum(a.price) as receive from a group by MONTH(a.create_at)\n" +
            "                                                                  order by month", nativeQuery = true)
    List<Map<String, Object>> revenueByMonth();
}