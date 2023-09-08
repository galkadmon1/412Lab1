
/*
* The scanner class reads characters until it has a valid word;
* Them it clasifies the word to it's proper category.
* At that point it returns the word to the parser as a <category, lexeme> pair,
* where category is the kind of word and lexeme is the spelling of the specific word. 
* 
* To simplfy the parser, a scanner also returns a token when it reaches an end of a line: <\n, "">
* as well as when it reaches the end of the file: <EFO, "">.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class scanner{

    // fields:
    /*
     * BufferedReader creates an input buffer
     * and allows the input to be read from the hard drive
     * in large chunks of data rather than a byte at a time,
     * resulting in a huge improvement in performance
     * when comparing to fileReader.
     * 
     * BufferedReader reads lots of data at a time
     * and stores it in the created buffer memory.
     * When java.io.BufferedReader#read() is called,
     * it reads the data from the memory buffer.
     * When data is not available in the buffer,
     * it makes a corresponding read request
     * of the underlying character stream
     * and loads lots of data into the created buffer.
     * 
     */
    public BufferedReader reader; // Buffer that holds 8Kb of data from the file./
    public String line;
    public int index;
    //public int lineNumber;
    int[][] T; // transformation table
    boolean[] acceptingStates; // vector that holds all accepting states
    HashMap<Character, Integer> CharClass;
    HashMap<Integer, String> Type; // a vector that maps the state number to it's proper microSyntax 
    boolean potentialConstant;
    /* 
     * This is a constructor that reads the whole file and saves it into the reader field.
     */

    public scanner(String filename) throws IOException {
        // does reader contain the whole file? 
        // do I want it to? 
        // I need to put it in another line --> (readline) buffer.

        this.reader = new BufferedReader(new FileReader(filename));  // TO DO: need to specify a size (1042 will be good, then read lines from that).
        this.line = reader.readLine(); // reads the first line.
        index = 0; // keeps track of where we are reading in the line.
        //lineNumber = 1; 

        // initializing acceptingStates array that tells if a state is an accepting state
        acceptingStates = new boolean[43];
        for (int i = 0; i < acceptingStates.length; i++) {
            acceptingStates[i] = false;
        }

        acceptingStates[5] = true;
        acceptingStates[7] = true;
        acceptingStates[11] = true;
        acceptingStates[12] = true;
        acceptingStates[18] = true;
        acceptingStates[24] = true;
        acceptingStates[27] = true;
        acceptingStates[33] = true;
        acceptingStates[35] = true;
        acceptingStates[36] = true;
        acceptingStates[37] = true;
        acceptingStates[39] = true;
        acceptingStates[40] = true;
        acceptingStates[42] = true;

        // initiate the Transformation table
            // rows = states 0 ... 38
            // cols = characters
            // T[state][char] = int, this int is the only valid transformation to the next state

        T = new int[43][24];

        for (int i = 0; i < 43; i++) { // rows are states S0 --- S37 are chars, S38 is Se, 39 is integers
            for (int j = 0; j < 24; j++) { // cols are characters in language. 0-19 is valid chars, 20 is integers, 21 is other
                T[i][j] = 38; // start with initializing all states to Se = 38.
            }
        }

        /* manually placing the correct transitions into the Transition Table */
        T[0][0] = 1;
        T[0][1] = 8;
        T[0][2] = 13;
        T[0][3] = 19;
        T[0][4] = 22;
        T[0][5] =  25;
        T[0][6] = 28;
        T[0][7] = 34; 
        T[0][8] = 36;
        T[0][9] = 37;
        T[0][20] = 39; // constant
        T[0][22] = 40;
        T[0][23] = 41;
        T[1][10] = 6;
        T[1][12] = 2;
        T[2][6] = 3;
        T[3][2] = 4;
        T[4][13] = 5;
        T[6][11] = 7;
        T[8][0] = 14;
        T[8][6] = 9;
        T[9][4] = 10;
        T[10][14] = 11;
        T[11][16] = 12;
        T[13][0] = 14;
        T[13][20] = 39;
        T[14][15] = 15;
        T[15][16] = 16;    
        T[16][17] = 17;
        T[17][12] = 18;
        T[19][10] = 20;
        T[20][1] = 21;
        T[21][12] = 18;
        T[22][14] = 23;
        T[23][14] = 24;
        T[25][6] = 26;
        T[26][18] = 27;
        T[28][10] = 29;
        T[29][12] = 30;
        T[30][18] = 31;
        T[31][10] = 32;
        T[32][12] = 33;
        T[34][19] = 35;
        T[39][20] = 39;
        T[40][22] = 40;
        T[41][23] = 42;
        

        /* AH THAT WAS HARD ^^^ */

        // initializing CharClass
        // given a char c, let me know in what col it is.
        // note: is the cahracter is NOT it he Charclass:
        // it should recive the int 21 for 'other'.
        // All states in 'other' will be trtanslated itno Se (38).
        CharClass = new HashMap<>();
        CharClass.put('s', 0);
        CharClass.put('l', 1);
        CharClass.put('r', 2);
        CharClass.put('m', 3);
        CharClass.put('a', 4);
        CharClass.put('n', 5);
        CharClass.put('o', 6);
        CharClass.put('=', 7);
        CharClass.put(',', 8);
        //CharClass.put('\\', 9); //EOL FOR NOW --> need to add transidion to \n\r & \n
        CharClass.put('u', 10);
        CharClass.put('b', 11);
        CharClass.put('t', 12);
        CharClass.put('e', 13);
        CharClass.put('d', 14);
        CharClass.put('h', 15);
        CharClass.put('i', 16);
        CharClass.put('f', 17);
        CharClass.put('p', 18);
        CharClass.put('>', 19);
        CharClass.put('0', 20);
        CharClass.put('1', 20);
        CharClass.put('2', 20);
        CharClass.put('3', 20);
        CharClass.put('4', 20);
        CharClass.put('5', 20);
        CharClass.put('6', 20);
        CharClass.put('7', 20);
        CharClass.put('8', 20);
        CharClass.put('9', 20);
        CharClass.put(' ', 22); // space should be an accepting state with special token generated!
        CharClass.put('/', 23); // double // is a comment and should move to the next line.

        
        // Type translates the states to it's category
        // like what TYPE of accepting state is this?
        
        Type = new HashMap<>();
        Type.put(5, "memop");
        Type.put(7, "arithop");
        Type.put(11, "memop");
        Type.put(12, "loadi");
        Type.put(18, "arithop");
        Type.put(24, "arithop");
        Type.put(27, "nop");
        Type.put(33, "output");
        Type.put(35, "into");
        Type.put(36, "comma");
        Type.put(37, "EOL");
        Type.put(39, "reg/constant");
        Type.put(40, "SPACE");
        Type.put(42, "comment");
    }

    /**
     * This methode is being called when you want to scan 1 word from the file.
     * It returns a token type that holds the category & the word.
     *
     * @return token that holds the category and the word if valid, or invalid token if the word is invalid.
     **/
    public token scan() throws IOException { // BETTER IF I HAVE TRY-CATCH?
        

        /*
        * The following is an implementation of Table-Driven Scanner
        * from the book in page 64.
        */

        token token = new token(null, null);
        char c; // char to read
        int state = 0;
        String lexeme = "";
        Stack<Integer> stack = new Stack<Integer>(); // stacks of states        
        stack.clear();
        stack.push(-1); // bad = invalid word
        int col;
        potentialConstant = true;

        while (state != 38) { // while state is not Se
            
            /* needs to check that line is not empty, bc if it is then it is the end of the file! */
            // ^^^ thats not true, we can have an empty line in the middle of the code.

            if (line == null) {
                // EOF
                token = new token("EOF", "");
                return token;
            }

            if (line.equals("")) {
                // empty line
                token = new token("EOL", "");
                /* do whatever needs to be done with token */
                nextLine();
                return token;
            }

            if (index == line.length()) {
                index++;
                break;

            }

            if (index > line.length()) {
                // reached the end of the line
                token = new token("EOL", "");
                /* do whatever needs to be done with token */
                nextLine();
                return token;
            }

            c = line.charAt(index); // checked for outOfBound -- read the character
            index++;
            lexeme = lexeme + c;

            if (c == 'r') {
                potentialConstant = false;
            }
            
            if (acceptingStates[state]) { // state is in accepting states
                stack.clear(); // clear stack
                stack.push(-1); // bad
            }
            
            stack.push(state);
            if (CharClass.containsKey(c)) {
                col = CharClass.get(c); // the charClass is the clasification table, it maps c into the column in T
            }
            else {
                col = 21; // This is the 'other' col, that translates all to Se (state 38: error state).
            }
            state = T[state][col]; // find the next state accorfing to current state and c. 
        }
        
        while ((state != -1) && (!acceptingStates[state])) { // while state is not in the accepting state and state is not bad
            
            state = stack.pop();
            if (state != -1) {
                // truncate & roll back
                lexeme = lexeme.substring(0, lexeme.length() -1);
                index--;
            }
        }

        if (state == -1) { // couldnt find an accepting state - return invalid word
            /* if we have reached here, the word is unknown.
             * Maybe it's invalid word
             * Maybe it's a constant --> which we need to be able to categorize !
             */
            token.category = "invalid";
            token.lexeme = "";
            index++;
        }
        else {
            // return valic token with lexeme
            //Type.put(39, "reg/constant");
            if (state != 39) {
                token.category = Type.get(state);
                token.lexeme = lexeme;
            }
            else { // state is 39: either reg or constant
                if (potentialConstant) {
                    // true: r was not read in the beginning
                    token.category = "constant";
                    token.lexeme = lexeme;
                }
                else {
                    token.category = "reg";
                    token.lexeme = lexeme;
                }
            }
        }

        if (token.category == "SPACE") {
            token = scan();
        }

        if (token.category == "comment") {
            // reached the end of the line
            token = new token("EOL", "");
            /* do whatever needs to be done with token */
            nextLine();
            return token;   
        }
        return token;
    }

    public void nextLine() throws IOException {
        // reached the end of the line
        /* reset fields such that nexts time the next line will be available. */
        this.line = this.reader.readLine(); // reads the next line.
        index = 0;
    }
}
