package resolution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 30.01.2016.
 */
public class Parser {
    private static Parser instance;

    private Parser() {}

    public static Parser getInstance() {
        return instance == null ? (instance = new Parser()) : instance;
    }

    public List<Disjunct> buildDisjunctList(String formula) {
        String s = formula.replaceAll(" ", "");
        List<Disjunct> resList = new ArrayList<>();
        while (s.length() != 0) {
            String disjunctString = getFirstDisjunctString(s);
            resList.add(new Disjunct(buildAtomListForDisjunct(disjunctString)));
            s = getFormulaWithoutFirstDisjunct(s);
        }
        return resList;
    }

    private String getFirstDisjunctString(String formula) {
        return formula.substring(0, formula.indexOf('}') + 1);
    }

    private String getFormulaWithoutFirstDisjunct(String formula) {
        int index = getIndex(formula, '}');
        return (++index == formula.length()) ? "" : formula.substring(++index, formula.length());
    }




    public List<Atom> buildAtomListForDisjunct(String formula) {
        String s = formula.substring(1, formula.length() - 1);
        List<Atom> resList = new ArrayList<>();
        while (s.length() != 0) {
            String atomString = getFirstAtomString(s);
            resList.add(buildAtom(atomString));
            s = getFormulaWithoutFirstAtom(s);
        }
        return resList;
    }

    private String getFirstAtomString(String formula) {
        return formula.indexOf(',') == -1 ? formula : formula.substring(0, formula.indexOf(','));
    }

    private String getFormulaWithoutFirstAtom(String formula) {
        int index = getIndex(formula, ',');
        return (index == formula.length()) ? "" : formula.substring(++index, formula.length());
    }




    private Atom buildAtom(String formula) {
        boolean not = formula.indexOf('~') != 0;
        String s = not ? formula : formula.substring(1, formula.length());
        char name = s.charAt(0);
        return new Atom(name, not);
    }

    private int getIndex(String formula, char ch) {
        char[] chars = formula.toCharArray();
        int nesting = 0;
        for (int i = 0; i < chars.length; i++) {
            nesting += newNesting(chars[i]);
            if (nesting == 0 && chars[i] == ch) {
                return i;
            }
        }
        return formula.length();
    }

    private int newNesting(char ch) {
        return (ch == '(') ? 1 : (ch == ')') ? -1 : 0;
    }

    //for predicate
    public List<Atom> buildElementListForPredicate(String formula) {
        return buildElementList(formula, ',');
    }

    private List<Atom> buildElementList(String formula, char ch) {
        List<Atom> resList = new ArrayList<>();
        while (formula.length() != 0) {
            String elementString = getFirstElementString(formula, ch);
            resList.add(createElement(elementString));
            formula = getFormulaWithoutFirstElement(formula, ch);
        }
        return resList;
    }

    private String getFirstElementString(String formula, char ch) {
        int index = getIndex(formula, ch);
        return formula.substring(0, index);
    }


    private Atom createElement(String formula) {
        return new Atom(formula);
        //return (formula.length() > 2 /*it predicat?*/) ? new Atom(formula) : new Atom(formula);
    }

    private String getFormulaWithoutFirstElement(String formula, char ch) {
        int index = getIndex(formula, ch);
        return (index == formula.length()) ? "" : formula.substring(index + 1, formula.length());
    }

    //test

    public static void main(String[] args) {
        System.out.println(Parser.getInstance().buildDisjunctList("{A, B, C}, {~A, B, C}, {B}"));
    }

}
