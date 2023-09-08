
/**  This calss is an internal representation of 1 line!
* it is equle to a node in a doubly linked list.
* each node (lineIR) has a next, a prev, and an array that holds [Operands 1 - 3] the internal representaion of that line
it also has a field to hold the line number that it represents, and a field to hold the opcode of that line. 
*/
public class lineIR {

    // fields 
    int line;
    String opcode;
    int[] operands = new int[12];
    lineIR nextLineIR;
    lineIR prevLineIR;

    public lineIR() {

    }
    public void setline(int line) {
        this.line = line;
    }
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
    public void setOperand(int[] operands) {
        this.operands = operands;
    }
    public void setNextLineIR(lineIR nexLineIR) {
        this.nextLineIR = nexLineIR;
    }
    public void setPrevLineIR(lineIR prevLineIR) {
        this.prevLineIR = prevLineIR;
    }
}