package com.example.thymeleaf.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.thymeleaf.entity.Student;
import com.example.thymeleaf.repository.AddressRepository;
import com.example.thymeleaf.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    // Correct and incorrect data tests
    @Test
    void findById_CorrectId_ReturnsStudent() {
        // given
        String validId = "1";
        Student expectedStudent = new Student(); // Provide appropriate test data
        when(studentRepository.findById(validId)).thenReturn(java.util.Optional.of(expectedStudent));

        // when
        Student result = studentService.findById(validId);

        // then
        assertEquals(expectedStudent, result);
    }

    @Test
    void findById_IncorrectId_ThrowsException() {
        // given
        String invalidId = "invalidId";
        when(studentRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        // when & then
        assertThrows(Exception.class, () -> studentService.findById(invalidId));
    }

    @Test
    void save_CorrectStudent_ReturnsSavedStudent() {
        // given
        Student validStudent = new Student(); // Provide appropriate test data

        // when
        Student result = studentService.save(validStudent);

        // then
        assertNotNull(result);
        verify(studentRepository, times(1)).save(validStudent);
        verify(addressRepository, times(1)).save(validStudent.getAddress());
    }

    @Test
    void save_IncorrectStudent_ThrowsException() {
        // given
        Student invalidStudent = new Student(); // Provide invalid test data
        invalidStudent.setId(null); // Simulating incorrect data

        // when & then
        assertThrows(Exception.class, () -> studentService.save(invalidStudent));
    }

    @Test
    void update_CorrectIdAndStudent_ReturnsUpdatedStudent() {
        // given
        String validId = "1";
        Student existingStudent = new Student(); // Provide appropriate test data
        Student updatedStudent = new Student(); // Provide appropriate test data
        when(studentRepository.findById(validId)).thenReturn(java.util.Optional.of(existingStudent));
        when(studentRepository.save(any())).thenReturn(updatedStudent);

        // when
        Student result = studentService.update(validId, updatedStudent);

        // then
        assertNotNull(result);
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void update_IncorrectIdOrStudent_ThrowsException() {
        // given
        String invalidId = "invalidId";
        Student invalidStudent = new Student(); // Provide invalid test data
        invalidStudent.setId(null); // Simulating incorrect data

        // when & then
        assertThrows(Exception.class, () -> studentService.update(invalidId, invalidStudent));
    }

    @Test
    void deleteById_CorrectId_DeletesStudent() {
        // given
        String validId = "1";

        // when
        studentService.deleteById(validId);

        // then
        verify(studentRepository, times(1)).delete(any());
    }

    @Test
    void deleteById_IncorrectId_ThrowsException() {
        // given
        String invalidId = "invalidId";

        // when & then
        assertThrows(Exception.class, () -> studentService.deleteById(invalidId));
    }

    // SQL and JS injections tests
    @Test
    void findById_SQLInjectionAttempt_ThrowsException() {
        // given
        String sqlInjectionAttempt = "1'; DROP TABLE Student; --";

        // when
        assertThrows(Exception.class, () -> studentService.findById(sqlInjectionAttempt));
    }

    @Test
    void save_SQLInjectionAttempt_ThrowsException() {
        // given
        Student studentWithSQLInjection = new Student();
        studentWithSQLInjection.setId("1'; DROP TABLE Student; --");

        // when
        assertThrows(Exception.class, () -> studentService.save(studentWithSQLInjection));
    }

    @Test
    void update_SQLInjectionAttempt_ThrowsException() {
        // given
        String validId = "1";
        Student studentWithSQLInjection = new Student();
        studentWithSQLInjection.setId("1'; DROP TABLE Student; --");

        // when
        assertThrows(Exception.class, () -> studentService.update(validId, studentWithSQLInjection));
    }

    @Test
    void deleteById_SQLInjectionAttempt_ThrowsException() {
        // given
        String sqlInjectionAttempt = "1'; DROP TABLE Student; --";

        // when
        assertThrows(Exception.class, () -> studentService.deleteById(sqlInjectionAttempt));
    }

    @Test
    void findById_JSInjectionAttempt_ThrowsException() {
        // given
        String jsInjectionAttempt = "1'; alert('Hello'); //";

        // when
        assertThrows(Exception.class, () -> studentService.findById(jsInjectionAttempt));
    }

    @Test
    void save_JSInjectionAttempt_ThrowsException() {
        // given
        Student studentWithJSInjection = new Student();
        studentWithJSInjection.setId("1'; alert('Hello'); //");

        // when
        assertThrows(Exception.class, () -> studentService.save(studentWithJSInjection));
    }

    @Test
    void update_JSInjectionAttempt_ThrowsException() {
        // given
        String validId = "1";
        Student studentWithJSInjection = new Student();
        studentWithJSInjection.setId("1'; alert('Hello'); //");

        // when
        assertThrows(Exception.class, () -> studentService.update(validId, studentWithJSInjection));
    }

    @Test
    void deleteById_JSInjectionAttempt_ThrowsException() {
        // given
        String jsInjectionAttempt = "1'; alert('Hello'); //";

        // when
        assertThrows(Exception.class, () -> studentService.deleteById(jsInjectionAttempt));
    }

    // Extreme tests

}
