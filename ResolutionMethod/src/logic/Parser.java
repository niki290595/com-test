package logic;

import java.util.ArrayList;

/**
 * Created by Ilya on 18.12.2015.
 */
public class Parser {
    private ArrayList<Character> liganentSet;

    public Parser() {
        /*
        if (liganentSet == null) {
            liganentSet = logic.LogicLiganent.getKeySet();
        }
        */
    }

    private static boolean canDeleteBracket(char[] chars) {
        if (chars[0] == '(' && chars[chars.length - 1] == ')') {
            int k = 0;
            for (int i = chars.length - 1; i >= 0; i--) {
                char c = chars[i];
                switch (c) {
                    case '(': k++; break;
                    case ')': k--; break;
                    default:
                        if (k == 0) return false;
                }
            }
            return true;
        }
        return false;
    }

    private static int getOptimalLogicLiganent(char[] chars) {
        int countBracket = 0;
        int operationIndex = -1;
        LogicLiganent logicLiganent = null;

        for (int i = chars.length - 1; i >= 0 ; i--) {
            char c = chars[i];
            switch (c) {
                case '(':
                    countBracket++;
                    break;
                case ')':
                    countBracket--;
                    break;
                default:
                    if (countBracket == 0 && LogicLiganent.isLogicLiganent(c) && (logicLiganent == null || logicLiganent.ordinal() < LogicLiganent.getLogicLiganet(c).ordinal())) {
                        operationIndex = i;
                        logicLiganent = LogicLiganent.getLogicLiganet(c);
                        if (logicLiganent.ordinal() == 4) return operationIndex;
                    }
            }
        }
        return operationIndex;

    }

    public static LogicTree getLogicTree(String logicalFormula) {
        if (logicalFormula.length() == 0) return null;
        char[] chars = logicalFormula.toCharArray();
        if (canDeleteBracket(chars))
            return getLogicTree(logicalFormula.substring(1, logicalFormula.length()-1));

        int operationIndex = getOptimalLogicLiganent(chars);
        if (operationIndex != -1) {
            LogicTree leftTree = getLogicTree(logicalFormula.substring(0, operationIndex));
            LogicTree rightTree = getLogicTree(logicalFormula.substring(operationIndex + 1, logicalFormula.length()));
            LogicLiganent liganent = LogicLiganent.getLogicLiganet(chars[operationIndex]);
            return new LogicTree(leftTree, rightTree, liganent);
        } else {
            return new LogicTree(null, null, chars[0]);
        }
    }
}
