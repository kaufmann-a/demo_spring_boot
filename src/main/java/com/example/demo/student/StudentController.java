package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is the API layer of the application
 *
 */
@RestController //Indicates that we use rest as an interface
@RequestMapping(path="api/v1/student")
public class StudentController {

    private final StudentService studentService;

    /**
     * Dependency injection, we want to automatically instanciate the class variable studentsservice
     * with whatever argument is passed in the constructor
     *
     * @param studentService The service layer which is below this API Layer
     */
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    /**
     * This method is called by REST (i.e. a Get call from the REST endpoint) and the method then basically
     * Uses the Service layer to
     * @return
     */
    @GetMapping //Rest endpoint, function is called when the request mapping path from above is called
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    /**
     * Requestbody says that a json file structure is taken from the Rest api (i.e. from the client)
     *
     * @param student
     */
    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    /**
     * PUT http://localhost:8080/api/v1/student/1?name=Maria&email=maria@gmail.com
     * This Put request changes name and email of student with id 1 to Maria and maria@gmail.com
     *
     * @param studentId
     * @param name
     * @param email
     */
    @PutMapping(path = "{studentId}")
    public void updateStudent (
            @PathVariable("studentId") Long studentId, //This pathvariable says that in the http url path the id is contained
            @RequestParam(required = false) String name, //String with a parameter (i think in json) with new name sent by PUT
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }

}
