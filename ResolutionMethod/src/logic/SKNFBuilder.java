package logic;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * Created by Ilya on 19.12.2015.
 */
public class SKNFBuilder {
    private char[] chars;
    private int[][] ints;
    private ArrayList<Disjunct> disjuncts;
    private ArrayList<Atom> atoms;

    public SKNFBuilder(char[] chars, int[][] ints) {
        this.chars = chars;
        this.ints = ints;
    }

    private Atom search(char ch, boolean not) {
        for (Atom atom : atoms) {
            if (atom.getCh() == ch && atom.getNot() == not)
                return atom;
        }
        return null;
    }

    public void build() {
        //сформировать всевозможные атомы и отрицания атомов
        atoms = new ArrayList<>();
        for (int i = 1; i < chars.length ; i++) {
            atoms.add(new Atom(chars[i], true));
            atoms.add(new Atom(chars[i], false));
        }
/*
        System.out.println("Всевозможные атомы:");
        for (Atom atom : atoms) {
            System.out.print(atom + "  ");
        }
        System.out.println("\n");
*/
        //сформировать список дизъюнтов
        disjuncts = new ArrayList<>();
        for (int i = 1; i < ints.length; i++) {
            if (ints[i][0] == 0) {//строим дизъюнкт
                Disjunct disjunct = new Disjunct();
                for (int j = 1; j < ints[i].length; j++) {
                    //поиск нужного атома
                    Atom atom = search(chars[j], ints[i][j] == 1);
                    atom.incCountLink();
                    atom.addParent(disjunct);
                    disjunct.add(atom);
                }
                disjuncts.add(disjunct);
            }
        }
/*
        System.out.println("Дизъюнкты: ");
        for (Disjunct disjunct : disjuncts) {
            System.out.println(disjunct);
        }
        System.out.println("\n");
*/
    }

    public ArrayList<Disjunct> getDisjuncts() {
        return disjuncts;
    }

    public ArrayList<Atom> getAtoms() {
        return atoms;
    }
}
