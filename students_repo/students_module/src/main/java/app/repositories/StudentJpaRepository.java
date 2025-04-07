package app.repositories;

import app.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer>,
        JpaSpecificationExecutor<Student> {

    @Query("select s from Student s where age > :age")
    List<Student> findStudentsWithMoreThanAge(@Param("age") Integer age);

    @Query("select s from Student s where age < :age")
    List<Student> findStudentsWithLessThanAge(@Param("age") Integer age);

    @Query("select s from Student s where age = :age")
    List<Student> findStudentsWithAge(@Param("age") Integer age);

    List<Student> findByOrderByFullName();

    @Query("select s from Student s order by LENGTH(email) desc")
    List<Student> findTop1ByEmail();

    @Query(value = "select * from Students where array_length(grades_list, 1) > :count", nativeQuery = true)
    List<Student> findWithMoreGradesThan(@Param("count") Integer count);
}