/**
 * Copyright (c) 2021 Wells Burke
 * SPDX short identifier: MIT
 */
package com.github.wellsb1.srg0.subleq;

import com.github.wellsb1.srg0.SRG0Computer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SubleqTest {

    public static void main(String[] args) {
        new SubleqTest().subleq();
    }

    @Test
    public void test_fetchRegister() {
        String input    = "[00000000][10000000][00000000][11100000][00000000][00000000]F[00000100][00000000][00000000][00000001]";
        String expected = "[00000000][10000000][00000000][11100000][00000000][00000000]R{11100000}[00000100][00000000][00000000][00000001]";

        SRG0Computer comp = new SRG0Computer();
        comp.withDebug(true);
        comp.loadRules("subleq/fetch.rules");
        String output = comp.run(input);
        assertEquals(expected, output);
    }


    public void subleq() {
        SRG0Computer comp = new SRG0Computer();
        comp.withDebug(true);
        comp.loadRules("subleq/subleq.rules");
        comp.loadRules("subleq/fetch.rules");
        comp.runFile("subleq/subleq.input");
    }
}
