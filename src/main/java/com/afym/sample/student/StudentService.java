package com.afym.sample.student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    List<Student> getAllStudents();
    void addNewStudent(Student student);
    void updateStudent(UUID studentId, Student student);
    void deleteStudent(UUID studentId);
}
