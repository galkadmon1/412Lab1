/*
 * This class is an internal representation for one parser. 
 * it points to the head and the tail of a doubly linked list that each node in the list is
 * a lineIR, that is a line internal representation of a single line 
 */

public class IR {
    
    // fields of one internal representation:
    lineIR head;
    lineIR tail;

    public IR () {
        head = new lineIR();
        tail = new lineIR();
        head.nextLineIR = tail;
        head.prevLineIR = null;
        tail.prevLineIR = head;
        tail.nextLineIR = null;
    }

    // placing a new node in the beginning of the list.
    public void addNewLineIR(lineIR newLine) {
        newLine.setNextLineIR(head.nextLineIR);
        newLine.setPrevLineIR(head);
        head.nextLineIR.setPrevLineIR(newLine);
        head.setNextLineIR(newLine); 
    }

    public void printIR() {
        lineIR itirate = head;
        while(!itirate.equals(tail)) {
            System.out.println("line number: " + itirate.line + ", opcode is: " + itirate.opcode);
        }
    }
}
