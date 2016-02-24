package logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by User on 18.11.2015.
 */
public enum LogicLiganent {
    NOT(0),
    AND(1),
    OR(2),
    FOLLOW(3),
    EQUIVALENCE(4);

    /*
    * 1 - НЕ
    * 2 - И
    * 3 - ИЛИ
    * 4 - СЛЕДОВАНИЕ
    * 5 - ЭКВИВАЛЕНТНОСТЬ
    * */

    private final int level;

    LogicLiganent(int i) {
        level = i;
    }

    public static Comparator<LogicLiganent> logicLiganentComparator = new Comparator<LogicLiganent>() {
        @Override
        public int compare(LogicLiganent l1, LogicLiganent l2) {
            return l1.level - l2.level;
        }
    };

    public static char getKey(LogicLiganent liganent) {
        switch (liganent) {
            case NOT:
                return (char) 172;
            case AND:
                return (char) 708;
            case OR:
                return (char) 709;
            case FOLLOW:
                return (char) 62;
            case EQUIVALENCE:
                return (char) 126;
            default:
                return (char) 1;
        }
    }

    public static ArrayList<Character> getKeySet() {
        ArrayList<Character> liganentSet = new ArrayList<>();
        liganentSet.add((char) 172);
        liganentSet.add((char) 708);
        liganentSet.add((char) 709);
        liganentSet.add((char) 126);
        liganentSet.add((char) 62);
        return liganentSet;
    }


    public static LogicLiganent getLogicLiganet(char key) {
        switch (key) {
            case '-':
                return NOT;
            case '*':
                return AND;
            case '+':
                return OR;
            case '>':
                return FOLLOW;
            case '~':
                return EQUIVALENCE;
            default:
                return null;
        }
    }

    public static boolean isLogicLiganent(char c) {
        return getLogicLiganet(c) != null;
    }

    public static char getChar(LogicLiganent liganent) {
        switch (liganent) {
            case NOT:
                return '-';
            case AND:
                return '*';
            case OR:
                return '+';
            case FOLLOW:
                return '>';
            case EQUIVALENCE:
                return '~';
            default:
                return '_';
        }
    }

    public static int getSolve(int op1, int op2, LogicLiganent liganent) {
        switch (liganent) {
            case NOT:
                return (op2 + 1) % 2;
            case AND:
                return (op1 == 1 && op2 == 1) ? 1:0;
            case OR:
                return (op1 == 0 && op2 == 0) ? 0:1;
            case FOLLOW:
                return (op1 == 1 && op2 == 0) ? 0:1;
            case EQUIVALENCE:
                return (op1 == op2) ? 1:0;
            default:
                return -1;
        }
    }
}
