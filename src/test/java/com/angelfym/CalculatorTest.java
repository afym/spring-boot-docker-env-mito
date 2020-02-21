package com.angelfym;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculatorTest {

    @Test
    public void testAddStudents() {
        StudentCalculator sc = new StudentCalculator();
        int a = sc.add(2, 2);
        assertTrue(a == 4);
    }
}
