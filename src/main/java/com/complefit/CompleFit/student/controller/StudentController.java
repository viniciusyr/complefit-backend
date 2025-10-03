package com.complefit.CompleFit.student.controller;

import com.complefit.CompleFit.student.dto.StudentRequestDTO;
import com.complefit.CompleFit.student.dto.StudentResponseDTO;
import com.complefit.CompleFit.student.dto.StudentUpdateDTO;
import com.complefit.CompleFit.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.createStudent(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody StudentUpdateDTO dto
    ) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
