package countKeywords;


import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords filename");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (file.exists()) {
            System.out.println("The number of keywords in " + args[0]
                    + " is " + countKeywords(file));
        } else {
            System.out.println("File " + args[0] + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        try (Scanner input = new Scanner(file)) {
            boolean inBlockComment = false;

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                // Ignore line comments
                if (line.startsWith("//")) {
                    continue;
                }

                // Check for start and end of block comments
                if (line.contains("/*")) {
                    inBlockComment = true;
                }
                if (inBlockComment) {
                    if (line.contains("*/")) {
                        inBlockComment = false;
                    }
                    continue;
                }

                // Remove strings by replacing with empty strings
                line = line.replaceAll("\"(\\\\\"|[^\"])*\"", "");

                // Check each word
                String[] words = line.split("\\W+");
                for (String word : words) {
                    if (keywordSet.contains(word)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}