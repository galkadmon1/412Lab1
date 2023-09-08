/*
 * A token is a pair of <category, lexeme> where category
 * is one of the cateroies options of the microSyntax and lexeme is a string of words 
 * (maybe in the future lexeme will be an int, with a conversion table when needed.)
 */
public class token {
    //fields:
    String category;
    String lexeme;

    public token(String category, String lexeme) { 
        this.category = category;
        this.lexeme = lexeme;
    }

    public String getCategory() {
        return category;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String toString() {
        return("<" + lexeme + ", " + category + ">");
    }
}
