/**
 * Copyright (c) 2021 Wells Burke
 * SPDX short identifier: MIT
 */
package com.github.wellsb1.srg0;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionTest {

    public static void main(String[] args){
        new AdditionTest().test_all_combinations_up_to_1000();
    }

    @Test
    public void testCompute_add_1_digit_positive_integers_no_carry() {
        assertEquals(7, sum(3, 4));
    }

    @Test
    public void testCompute_add_2_digit_positive_integers_no_carry() {
        assertEquals(46, sum(12, 34));
    }

    @Test
    public void testCompute_add_2_digit_positive_integers_with_carry() {
        assertEquals(174, sum(98, 76));
    }

    @Test
    public void testCompute_add_positive_integers_left_side_more_digits_than_right_no_carry() {
        assertEquals(10005, sum(10000, 5));
    }

    @Test
    public void testCompute_add_positive_integers_right_side_more_digits_than_left_no_carry() {
        assertEquals(10005, sum(5, 10000));
    }

    @Test
    public void testCompute_add_positive_integers_left_side_more_digits_than_right_with_carry() {
        assertEquals(1000, sum(999, 1));
    }

    @Test
    public void testCompute_add_positive_integers_right_side_more_digits_than_left_with_carry() {
        assertEquals(1000, sum(1, 999));
    }


    @Test
    public void test_all_combinations_up_to_1000() {
        long start = System.currentTimeMillis();
        int          maxTimes = 1001 * 1001;
        SRG0Computer computer = new SRG0Computer().loadRules("addition.rules");
        computer.withDebug(false);
        int cycles = 0;
        for (int i = 0; i <= 1000; i++) {
            for (int j = 0; j <= 1000; j++) {

                cycles +=1;
                if(cycles % 100000 == 0){
                    System.out.println(cycles + " - " + ((float)cycles / (float)maxTimes));
                }
                String operation = i + "+" + j;
                String result    = computer.runFile(operation);
                if (!((i + j) + "").equals(result)) {
                    //-- run it again so you can see the calculations to debug the failed case
                    computer.withDebug(true);
                    computer.runFile(operation);
                    assertEquals((i + j) + "", result);
                }
            }
        }
        System.out.println("TOTAL TIME: " + (System.currentTimeMillis() - start) + "ms");
        for(SRG0Computer.Rule rule : computer.getRules()){
            if(rule.getMatches() == 0){
                System.out.println("UNUSED RULE: " + rule);
            }
        }
    }


    int sum(int num1, int num2) {
        String num = new SRG0Computer().loadRules("addition.rules").withDebug(true).runFile(num1 + "+" + num2);
        return Integer.parseInt(num);
    }
}
