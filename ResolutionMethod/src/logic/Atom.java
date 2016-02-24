package logic;

import java.util.ArrayList;

/**
 * Created by Ilya on 18.12.2015.
 */
public class Atom {
    private char ch;
    private boolean not;

    private int countLink;
    private ArrayList<Disjunct> parents;

    public Atom(char ch, boolean not) {
        this.ch = ch;
        this.not = new java.lang.Boolean(not);
        countLink = 0;
        parents = new ArrayList<>();
    }

    public Character getCh() {
        return ch;
    }

    public boolean getNot() {
        return not;
    }

    public int getCountLink() {
        return countLink;
    }

    public void incCountLink() {
        countLink++;
    }

    public void decCountLink() {
        countLink--;
    }

    public void addParent(Disjunct disjunct) {
        parents.add(disjunct);
    }

    @Override
    public String toString() {
        String out = "";
        if (not) out = "~";
        return out + ch;
    }
}
