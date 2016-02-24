package resolution;

import java.util.List;

/**
 * Created by Ilya on 30.01.2016.
 */
public class Atom {
    Boolean not; //A - true, ~A - false;
    Character name;
    List<Atom> atomList;

    public Atom(char name, boolean not, List<Atom> elementList) {
        this.name = name;
        this.not = not;
        this.atomList = elementList;
    }

    public Atom(char name, boolean not) {
        this.name = name;
        this.not = not;
        this.atomList = null;
    }

    public Atom(String formula) {
        initNot(formula);
        initName(formula);
        initElementList(formula);
    }

    protected void initNot(String formula) {
        not = formula.indexOf('~') != 0;
    }

    protected void initName(String formula) {
        String s = (not.booleanValue()) ? formula : formula.substring(1, formula.length());
        name = s.charAt(0);
    }

    protected void initElementList(String formula) {
        if (formula.length() < 3) {
            atomList = null;
            return;
        }
        String s = formula.substring(formula.indexOf('(') + 1, formula.lastIndexOf(')'));
        atomList = Parser.getInstance().buildElementListForPredicate(s);
    }

    public List<Atom> getAtomList() {
        return atomList;
    }

    public void setAtomList(List<Atom> atomList) {
        this.atomList = atomList;
    }

    public void setNot(Boolean not) {
        this.not = not;
    }

    public Boolean getNot() {
        return not;
    }

    public Character getName() {
        return name;
    }

    public void setName(Character name) {
        this.name = name;
    }

    public String getKey() {
        return this.toString();
    }

    public String getAlternativeKey() {
        return (!not ? "" : "~") + name + (atomList == null ? "" : atomList.toString());
    }

    public String getKeyWithoutElements() {
        return (not ? "" : "~") + name;
    }

    public String getAlternativeKeyWithoutElements() {
        return (!not ? "" : "~") + name;
    }

    @Override
    public String toString() {
        return (not ? "" : "~") + name + (atomList == null ? "" : atomList.toString());
    }
/*
    public String getKeyWithElem() {
        return super.getKey()  + atomList.toString();
    }

    public String getAlternativeKeyWithElem() {
        return super.getAlternativeKey()  + atomList.toString();
    }
    */

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Atom)) {
            return false;
        } else {
            Atom atom = (Atom) obj;
            return this.toString().equals(atom.toString());
        }
    }
}
