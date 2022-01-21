/**
 * Copyright (c) 2021 Wells Burke
 * SPDX short identifier: MIT
 */
package com.github.wellsb1.srg0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class SRG0Compiler {

    public String compile(String content) throws IOException {

        String         line = null;
        BufferedReader reader = new BufferedReader(new StringReader(content));

        while((line = reader.readLine()) != null){

            line = line.trim();
            if(line.startsWith("replace ")){

            }
            else if(line.startsWith("for[")){

            }
        }
        return content;
    }

}
