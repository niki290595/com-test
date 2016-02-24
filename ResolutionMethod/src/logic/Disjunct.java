package logic;

import java.util.ArrayList;

/**
 * Created by Ilya on 19.12.2015.
 */
public class Disjunct {
    private ArrayList<Atom> atoms;
    private int uses = 0;

    public Disjunct() {
        atoms = new ArrayList<>();
    }

    public void add(Atom atom) {
        atoms.add(atom);
    }

    public Atom getAtom(int index) {
        return atoms.get(index);
    }

    public int getUses() {
        return uses;
    }

    public int size() {
        return atoms.size();
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public String toString() {
        String res = "{";
        int tmp = atoms.size();
        for (Atom atom : atoms) {
            res += atom + (--tmp > 0 ? ", " : "");
        }
        return res + "}";
    }
}
