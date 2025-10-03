package com.complefit.CompleFit.student.service;

import com.complefit.CompleFit.student.domain.Student;
import com.complefit.CompleFit.student.dto.StudentRequestDTO;
import com.complefit.CompleFit.student.dto.StudentResponseDTO;
import com.complefit.CompleFit.student.dto.StudentUpdateDTO;
import com.complefit.CompleFit.student.mapper.StudentMapper;
import com.complefit.CompleFit.student.repository.StudentRepository;
import com.complefit.CompleFit.user.domain.User;
import com.complefit.CompleFit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = StudentMapper.toEntity(dto, user);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponseDTO getStudentById(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return StudentMapper.toResponse(student);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO updateStudent(UUID id, StudentUpdateDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentMapper.updateEntity(student, dto);
        return StudentMapper.toResponse(studentRepository.save(student));
    }

    public void deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
    }
}
