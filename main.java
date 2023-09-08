/*
 * This class is a front end to a two major components: a scanner and a parser.
 * The scanner reads the input stream, character by character,
 * and aggregates characters into words in the ILOC language. 
 * The parser looks at the stream of words returned by the scanner
 * and determines whether those words form a sequence of valid ILOC operations. 
 */


import java.io.File;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

public class main {

    public static void main(String[] args) throws IOException {
        
        String[] args2 = {"-h", "test1.txt"};
        args = args2;
        
        /* 1: check that the user has inserted exactly 2 inputs: */
        if (args.length != 2) {
            System.out.println("ERROR: please insert 2 arguments.");
            return;
        }
        /* start by reading the file path and check if its valid */
        File file = new File(args[1]);
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println("Valid file path.");
            } else {
                System.out.println("ERROR: The specified path is a directory, not a file.");
                return;
            }
        } else {
            System.out.println("ERROR: Invalid file path.");
            return;
        }
        String filename = args[1];
        //String filename = "txt.txt";
        // TO DO: check for valid pathname

        boolean h, r, p, s = false;
        switch(args[0]) {
            case "-h":
                h = true;
                break;
            
            case "-r":
                r = true;
                break;
            
            case "-p":
                p = true;
                break;
            
            case "-s":
                s = true;
                break;
            
            default:
                System.out.println("ERROR: Invalid flag. Please use -h, -p, -r, or -s.\n");
                return;
        }

        // after we astablished the flags -- let the program begin !
        // call parser with filename as argument
        parser parser = new parser(filename);
        boolean correct = parser.parse();
        System.out.println("grammar is correct: " + correct);
    }
}
