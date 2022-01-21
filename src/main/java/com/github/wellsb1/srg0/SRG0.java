/**
 * Copyright (c) 2021 Wells Burke
 * SPDX short identifier: MIT
 */
package com.github.wellsb1.srg0;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class SRG0 {

    boolean    debug = false;
    List<Rule> rules = new ArrayList<>();

    LinkedHashSet<String>               globals = new LinkedHashSet();
    List<LinkedHashMap<String, String>> locals  = new ArrayList<>();

    public String runFile(String inputFile) {
        String input = Utils.read(inputFile);
        return run(input);
    }

    public String run(String input) {

        //replaceLocalTokens(input);

        try {
            if (debug) {
                System.out.println("\r\n-------------------------------");
                System.out.println("INPUT: \"" + input + "\"\r\n\r\n");
                System.out.println(len(30, "RULE") + "OUTPUT");
                System.out.println("--------------------------------------------------------------------------------------");
            }
            boolean matched = false;

            do {
                matched = false;
                for (int i = 0; i < rules.size(); i++) {
                    Rule   rule   = rules.get(i);
                    String result = rule.match(input);
                    if (result != null) {
                        if (result.equals(input)) {
                            throw new RuntimeException("ERROR: Matched rule did not change the output.  This will cause an infinite recursion...exiting. " + rule);
                        }
                        if (debug) {
                            if (rule.comment != null) {
                                System.out.println("--" + rule.comment);
                            }
                            System.out.print(len(50, rule.toString()));
                            String output = result.trim();

                            BufferedReader reader = new BufferedReader(new StringReader(output));
                            String         line   = reader.readLine();
                            do {
                                System.out.println(line);
                                System.out.print(len(50, " "));
                            } while ((line = reader.readLine()) != null);
                            System.out.println("");
                        }
                        input = result;
                        matched = true;
                        break;
                    }

                }
            } while (matched);

            if (debug) {
                System.out.println("\r\nOUTPUT -> " + input);
                System.out.println("-------------------------------");
            }

            return input;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public SRG0 loadRules(String ruleFilePath) {
        return loadRules(Utils.find(ruleFilePath));
    }

    SRG0 loadRules(InputStream inputStream) {
        List<Rule> rules = parseRules(inputStream);
        this.rules.addAll(rules);
        if (debug) {
            System.out.println("\r\nLOADING RULES -----------------");
            rules.forEach(System.out::println);
            System.out.println("\r\n-------------------------------");
        }
        return this;
    }

    public List<Rule> parseRules(InputStream input) {
        List<Rule> rules = new ArrayList<>();
        try {
            LinkedHashMap<String, String> tempLocals = new LinkedHashMap<>();
            String                        comment    = null;
            BufferedReader                reader     = new BufferedReader(new InputStreamReader(input));
            String                        line       = null;
            boolean                       inComment  = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                //skip comment lines
                if (line.startsWith("//"))
                    continue;

                if (line.endsWith("*/")) {
                    inComment = false;
                    continue;
                }

                if (inComment)
                    continue;

                if (line.startsWith("/*")) {
                    inComment = true;
                    continue;
                }

                //remove comments after the rule
                if (line.indexOf("//") > 0)
                    line = line.substring(0, line.indexOf("//")).trim();

                //this is a rule comment
                if (line.startsWith("--")) {
                    comment = line.substring(2).trim();
                    continue;
                }

                if (line.startsWith("GLOBAL ")) {
                    String[] parts = line.substring(line.indexOf(" ") + 1).split(" ");
                    for (int i = 0; parts != null && i < parts.length; i++) {
                        String part = parts[i].trim();
                        if (part.length() > 0) {
                            globals.add(part);
                        }
                    }
                }

                if (line.startsWith("LOCAL ")) {
                    String[] parts = line.substring(line.indexOf(" ") + 1).split(" ");
                    for (int i = 0; parts != null && i < parts.length; i++) {
                        String part = parts[i].trim();
                        if (part.length() > 0) {
                            tempLocals.put(part, null);
                        }
                    }
                    locals.add(tempLocals);
                }

                String leftSide  = null;
                String rightSide = null;
                int    index     = line.indexOf("->");
                if (index > -1) {
                    leftSide = line.substring(0, index).trim();
                    rightSide = line.substring(index + 2).trim();
                    rules.add(new Rule(tempLocals, leftSide, rightSide, comment));
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Parse Exception: " + ioe.getMessage(), ioe);
        }
        return rules;
    }


    public void replaceLocalTokens(String input) {
        for (Rule rule : rules) {
            if (rule.locals != null) {
                for (String token : rule.locals.keySet()) {
                    for (String global : globals) {
                        for (int i = 0; i < token.length(); i++) {
                            if (global.indexOf(token.charAt(i)) > -1) {
                                throw new RuntimeException("Local token '" + token + "' conflicts with local token '" + global + "'");
                            }
                        }
                    }

                    String replacement = rule.locals.get(token);
                    if (replacement == null) {
                        replacement = nextReplacement(input);
                        rule.locals.put(token, replacement);
                    }

                    rule.leftSide = rule.leftSide.replace(token, replacement);
                    rule.rightSide = rule.rightSide.replace(token, replacement);
                }
            }
        }
    }

    int nextReplace = 19968;

    String nextReplacement(String input) {
        char c = 0;
        do {
            nextReplace += 1;
            c = (char) nextReplace;
        }
        while (input.indexOf(c) > -1);
        return c + "";
    }


    public SRG0 withDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public static class Rule {
        long                          matches   = 0;
        LinkedHashMap<String, String> locals    = null;
        String                        comment   = null;
        String                        leftSide  = null;
        String                        rightSide = null;

        public Rule(LinkedHashMap locals, String leftSide, String rightSide, String comment) {
            this.locals = locals;
            this.leftSide = leftSide;
            this.rightSide = rightSide;
            this.comment = comment;
        }

        public String match(String input) {
            int index = input.indexOf(leftSide);
            if (index >= 0) {
                matches += 1;
                String prefix      = input.substring(0, index);
                String replacement = rightSide;
                String postfix     = input.substring(index + leftSide.length());
                String output      = prefix + replacement + postfix;
                return output;
            }
            return null;
        }

        public String toString() {
            return leftSide + " -> " + rightSide;
        }

        public long getMatches() {
            return matches;
        }

        public String getLeftSide() {
            return leftSide;
        }

        public String getRightSide() {
            return rightSide;
        }
    }

    static String len(int len, String str) {
        while (str.length() < len)
            str = str + " ";
        if (str.length() > len) {
            str = str.substring(0, len + 1);
        }
        return str;
    }

}
