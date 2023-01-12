package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * THe @Service is needed such that the StudentController can do dependency injection of this class
 * (normally the annotation is @Component, but as it is a service, we can be more specific
 * here (because of spring boot)
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Dependency injection of studentrepository, this means that the class variable studentRepository has to be
     * injected into the argument passed in the constructor However we also have to say which class has to be
     * instanciated (StudentRepository) this class has to be a spring beaan
     *
     * @param studentRepository Interface inherited from Spring JPA Class JpaRepository (provides various db methods)
     */
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    /**
     * This method makes a database call by using the studentRepository interface which is inherited from JpaRepository,
     * a class from Spring which provides database methods
     *
     * @return
     */
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    /**
     *
     *
     * @param student
     */
    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + "does not exist"
            );
        }
        studentRepository.deleteById(studentId);
    }

    /**
     * With the transactional annotation from Spring we can just get the student using the repository and then save the
     * new values into the student object. Transactional then updates the repository and thus the database with the new
     * values.
     *
     * @param studentId
     * @param name
     * @param email
     */
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "student with id " + studentId + "does not exist"
        ));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getName(), name)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
