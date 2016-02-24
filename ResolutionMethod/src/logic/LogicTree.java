package logic;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 18.11.2015.
 */
public class LogicTree {
    LogicTree left;
    LogicTree right;
    LogicLiganent liganent;
    char date;

    public LogicTree(LogicTree left, LogicTree right, LogicLiganent liganent) {
        this.left = left;
        this.right = right;
        this.liganent = liganent;
    }

    public LogicTree(LogicTree left, LogicTree right, char date) {
        this.left = left;
        this.right = right;
        this.date = date;
        liganent = null;
    }

    @Override
    public String toString(){
        String s = "";
        if (left != null) s = "(" + left + ")";
        if (liganent != null) s += LogicLiganent.getChar(liganent);
        else return String.valueOf(date);
        if (right != null) s += "(" + right + ")";
        return s;

    }

    private ArrayList<Character> searcher(LogicTree tree, ArrayList<Character> chars) {
        if (tree == null) {
            return chars;
        } else {
            searcher(tree.left, chars);
            searcher(tree.right, chars);
            //todo not work
            if (tree.liganent == null && !chars.contains(tree.date)) {
                chars.add(tree.date);
            }
            return chars;
        }
    }

    public ArrayList<Character> getAtomsArray() {
        ArrayList<Character> chars = new ArrayList<>();
        chars = searcher(this, chars);
        return chars;
    }

    private int solver(LogicTree tree, char[] chars, int[] ints){
        if (tree == null) return -1;

        if (tree.liganent == null) {
            return match(tree.date, chars, ints);
        } else {
            int op1 = solver(tree.left, chars, ints);
            int op2 = solver(tree.right, chars, ints);
            return LogicLiganent.getSolve(op1, op2, tree.liganent);
        }
    }

    private int match(char date, char[] chars, int[] ints) {
        for (int i = 1; i < chars.length; i++) {
            if (date == chars[i])
                return ints[i];
        }
        return -1;
    }

    public int getSolve(char[] chars, int[] ints) {
        return solver(this, chars, ints);

    }
}
