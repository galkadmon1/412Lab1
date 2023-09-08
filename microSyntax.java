public class microSyntax {
    
    /*
     * This is a microSyntax class.
     */
    
     // Fields:
     String[] categories = {"memop", "loadl", "arithop", "output",
                            "nop", "constant", "register", "comma",
                            "into", "EOF", "EOL"};
    
    // -1: NO VALID WORD
    // 0: memop: load, store
    // 1: loadl: loadl
    // 2: arhtop: add, sub, mult, lshift, rshift
    // 3: output: output
    // 4: nop: nop
    // 5: constant: a non-negative integer
    // 6: register: 'r' followed by a constant
    // 7: comma: ','
    // 8: into: "=>"
    // 9: EOF: no specific word "".
    // 10: EOL: no specific word "". --> works when either \r\n | \n 
}
