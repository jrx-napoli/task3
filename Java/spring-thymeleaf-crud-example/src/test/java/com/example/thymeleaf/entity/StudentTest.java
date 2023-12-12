package com.example.thymeleaf.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentTest {

    @Test
    void shouldSetEmail_correctInput() {
        // given
        Student student = new Student();
        String validEmail = "john.doe@mail.com";

        // when
        student.setEmail(validEmail);

        // then
        assertEquals(validEmail, student.getEmail());
    }

    @Test
    void shouldThrow_tryToSetEmail_incorrectInput() {
        // given
        Student student = new Student();
        String invalidEmail = null;

        // when
        assertThrows(Exception.class, () -> student.setEmail(invalidEmail));
    }

    @Test
    void shouldThrow_tryToSetEmail_SQLInjectionAttempt() {
        // given
        Student student = new Student();
        String sqlInjectionAttempt = "1'; DROP TABLE Student; --";

        // when
        assertThrows(Exception.class, () -> student.setEmail(sqlInjectionAttempt));
    }

    @Test
    void shouldThrow_tryToSetEmail_XSSAttempt() {
        // given
        Student student = new Student();
        String xssAttempt = "1'; alert('Hello'); //";

        // when
        assertThrows(Exception.class, () -> student.setEmail(xssAttempt));
    }

    @Test
    void shouldThrow_tryToSetEmail_extremeInput() {
        // given
        Student student = new Student();
        String extremeString = "X".repeat(1000000);

        // when
        assertThrows(Exception.class, () -> student.setEmail(extremeString));
    }
}