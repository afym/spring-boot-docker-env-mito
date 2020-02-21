package com.angelfym.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/students")
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;

    @Autowired
    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student) {
        studentServiceImpl.addNewStudent(student);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") UUID studentId,
                              @RequestBody Student student) {
        studentServiceImpl.updateStudent(studentId, student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") UUID studentId) {
        studentServiceImpl.deleteStudent(studentId);
    }

}
