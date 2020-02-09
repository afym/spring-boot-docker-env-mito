package com.afym.sample;

import com.afym.sample.validator.EmailValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailValidatorTest {

    private final EmailValidator underTest = new EmailValidator();

    @Test
    public void itShouldValidateCorrectEmail() {
        assertThat(underTest.test("hello@gmail.com")).isTrue();
    }

    @Test
    public void itShouldValidateAnIncorrectEmail() {
        assertThat(underTest.test("hellogmail.com")).isFalse();
    }

    @Test
    public void itShouldValidateAnIncorrectEmailWithoutDotAtTheEnd() {
        assertThat(underTest.test("hello@gmail")).isFalse();
    }

    @Test
    public void itShouldValidateAnIncorrectEmailWithoutDotAtTheEndV2() {
        assertThat(underTest.test("hello@gmail")).isFalse();
    }
}