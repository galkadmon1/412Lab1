import java.io.IOException;

public class parser {

    private scanner scanner;
    private int lineCounter;
    
    public parser(String filename) throws IOException {
        this.scanner = new scanner(filename);
        this.lineCounter = 1;
    }

    /*
     * This parse method returns true if the file has a correct grammar and false otherwise.
     * This method also prints an ERROR whenever it found a wrong grammar
     */
    public boolean parse() throws IOException {
        boolean correct = true;
        token token = scanner.scan();
        while (token.category != "EOF") {
            switch(token.category) {
                /* each case will end after 1 line. need to increment the lineCounter, 
                    as well as get the next token (in the new line) */
                case "memop":
                    if (!finish_memop()) {
                        correct = false;
                        scanner.nextLine();  

                    };
                    break;

                case "loadi":
                    if (!finish_loadi()) {
                        correct = false;
                        scanner.nextLine();  

                    }
                    break;

                case "arithop":
                    if (!finish_arithop()) {
                        correct = false;
                        scanner.nextLine();  
                    }
                    break;

                case "output":
                    if(!finish_output()) {
                        correct = false;
                        scanner.nextLine();
                    }
                    break;

                case "nop":
                    if (!finish_nop()) {
                        correct = false;
                        scanner.nextLine();
                    }
                    break;
                
                case "comment": 
                    /* declare end of line? */
                    // need to change scanner so that // creates EOL and skips to next line. 
                
                case "EOL":
                    // This could be the case of empty lines or a line with only a comment
                    break;
                
                default:
                    System.out.println("ERROR parsing the line. line: " + lineCounter);
                    /* if we have reached an invalid sentence: need to tell the scanner to move to next line.
                    * then parse again */
                    correct = false;
                    scanner.nextLine();  
                    // when a line reaches the end correctly, it goes to the next line.
                    // but if a line reached an Error, I need it to go to the next line.
                    break;
            }
            /* increment the lineCounter */
            /* start reading the next line */
            lineCounter++;
            token = scanner.scan(); // read the first character in the new line
        }
        // finished parsing the file!
        // reset everything?        
        return correct;
    }

    /* *** TODO: ! IMPPOTANT! ***
     * change all token.catogory into a int comparison instead of string to save time. 
     * save token.category as int!
     */

    public boolean finish_memop() throws IOException{
        token token = scanner.scan();
        if (token.category != "reg"){
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "into") {
            System.out.println("ERROR: missing into. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "reg"){
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category == "EOL"){
            /* build the IR for this Op */
            /* add it to the list of Ops */
            return true;
        }
        else {
            System.out.println("ERROR: missing EOL. line: " + lineCounter);
            return false;
        }
    }

    public boolean finish_loadi() throws IOException {
     token token = scanner.scan();
        if (token.category != "constant") {
            System.out.println("ERROR: missing constant. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "into") {
            System.out.println("ERROR: missing into. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "reg"){
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category == "EOL"){
            /* build the IR for this Op */
            /* add it to the list of Ops */
            return true;
        }
        else {
            System.out.println("ERROR: missing EOL. line: " + lineCounter);
            return false;
        }   
    }

    public boolean finish_arithop() throws IOException {
     token token = scanner.scan();
        if (token.category != "reg") {
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "comma") {
            System.out.println("ERROR: missing comma. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "reg"){
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "into") {
            System.out.println("ERROR: missing into. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category != "reg") {
            System.out.println("ERROR: missing register. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category == "EOL"){
            /* build the IR for this Op */
            /* add it to the list of Ops */
            return true;
        }
        else {
            System.out.println("ERROR: missing EOL. line: " + lineCounter);
            return false;
        }   
    }

    public boolean finish_output() throws IOException {
        token token = scanner.scan();
        if (token.category != "constant") {
            System.out.println("ERROR: missing constant. line: " + lineCounter);
            return false;
        }
        token = scanner.scan();
        if (token.category == "EOL"){
            /* build the IR for this Op */
            /* add it to the list of Ops */
            return true;
        }
        else {
            System.out.println("ERROR: missing EOL. line: " + lineCounter);
            return false;
        }
    }

    public boolean finish_nop() throws IOException {
        token token = scanner.scan();
        if (token.category == "EOL"){
            /* build the IR for this Op */
            /* add it to the list of Ops */
            return true;
        }
        else {
            System.out.println("ERROR: missing EOL. line: " + lineCounter);
            return false;
        }
    }
}
