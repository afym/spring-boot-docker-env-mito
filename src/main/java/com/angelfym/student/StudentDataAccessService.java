package com.angelfym.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StudentDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT " +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender " +
                "FROM student";

        return jdbcTemplate.query(sql, mapStudentFomDb());
    }

    public int insertStudent(UUID studentId, Student student) {
        String sql = "" +
                "INSERT INTO student (" +
                " student_id, " +
                " first_name, " +
                " last_name, " +
                " email, " +
                " gender) " +
                "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                studentId,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase()
        );
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isEmailTaken(String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                " SELECT 1 " +
                " FROM student " +
                " WHERE email = ?" +
                ")";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{email},
                (resultSet, i) -> resultSet.getBoolean(1)
        );
    }

    private RowMapper<Student> mapStudentFomDb() {
        return (resultSet, i) -> {
            String studentIdStr = resultSet.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            String genderStr = resultSet.getString("gender").toUpperCase();
            Student.Gender gender = Student.Gender.valueOf(genderStr);
            return new Student(
                    studentId,
                    firstName,
                    lastName,
                    email,
                    gender
            );
        };
    }

    public int updateEmail(UUID studentId, String email) {
        String sql = "" +
                "UPDATE student " +
                "SET email = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, email, studentId);
    }

    public int updateFirstName(UUID studentId, String firstName) {
        String sql = "" +
                "UPDATE student " +
                "SET first_name = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, firstName, studentId);
    }

    public int updateLastName(UUID studentId, String lastName) {
        String sql = "" +
                "UPDATE student " +
                "SET last_name = ? " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, lastName, studentId);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean selectExistsEmail(UUID studentId, String email) {
        String sql = "" +
                "SELECT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM student " +
                "   WHERE student_id <> ? " +
                "    AND email = ? " +
                ")";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{studentId, email},
                (resultSet, columnIndex) -> resultSet.getBoolean(1)
        );
    }

    public int deleteStudentById(UUID studentId) {
        String sql = "" +
                "DELETE FROM student " +
                "WHERE student_id = ?";
        return jdbcTemplate.update(sql, studentId);
    }
}
