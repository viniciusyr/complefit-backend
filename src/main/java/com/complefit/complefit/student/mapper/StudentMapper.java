package com.complefit.complefit.student.mapper;

import com.complefit.complefit.student.domain.Student;
import com.complefit.complefit.student.dto.StudentRequestDTO;
import com.complefit.complefit.student.dto.StudentResponseDTO;
import com.complefit.complefit.student.dto.StudentUpdateDTO;
import com.complefit.complefit.user.domain.User;

public class StudentMapper {

    public static Student toEntity(StudentRequestDTO dto, User user) {
        Student student = new Student();
        student.setUser(user);
        student.setGoal(dto.goal());
        student.setLevel(dto.level());
        student.setMedicalRestrictions(dto.medicalRestrictions());
        return student;
    }

    public static void updateEntity(Student student, StudentUpdateDTO dto) {
        if (dto.goal() != null) student.setGoal(dto.goal());
        if (dto.level() != null) student.setLevel(dto.level());
        if (dto.medicalRestrictions() != null) student.setMedicalRestrictions(dto.medicalRestrictions());
    }

    public static StudentResponseDTO toResponse(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getUser().getId(),
                student.getUser().getFirstName() + " " + student.getUser().getLastName(),
                student.getGoal(),
                student.getLevel(),
                student.getMedicalRestrictions()
        );
    }
}
