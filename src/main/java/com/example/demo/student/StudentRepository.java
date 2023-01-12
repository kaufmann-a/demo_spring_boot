package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is the Database layer, i.e. this layer is responsible for data-access.
 * JpaRepository: We have to specify the type of object we work with (Student) and the type of the @Id field which is
 * Long (See student class)
 *
 * The @Repository annotation is needed such that the service can do dependency injection, it is also a Controller but
 * further specified to be a Repository
 *
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1") //I think it does nothing, just telling Spring that it is a query
    Optional<Student> findStudentByEmail(String email); //Transforms to SELECT * FROM student WHERE email = ?


}
