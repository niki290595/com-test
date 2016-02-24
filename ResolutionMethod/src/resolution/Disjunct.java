package resolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ilya on 30.01.2016.
 */
public class Disjunct {
    private List<Atom> atomList;

    public Disjunct(List<Atom> atomList) {
        this.atomList = atomList;
    }

    public Disjunct(String disjunctString) {
        atomList = Parser.getInstance().buildAtomListForDisjunct(disjunctString);
    }

    public List<Atom> getAtomList() {
        return atomList;
    }

    public int countElement() {
        return atomList.size();
    }

    public List<String> getKeys() {
        List<String> resList = new ArrayList<>();
        if (countElement() == 1) {
            resList.add(atomList.get(0).getKey());
        } else {
            resList.addAll(atomList.stream().map(Atom::getKeyWithoutElements).collect(Collectors.toList()));
        }
        return resList;
    }

    public Atom getFirstAtom() {
        return atomList.get(0);
    }

    @Override
    public String toString() {
        final String[] s = {"{"};
        final int[] t = {atomList.size()};
        atomList.forEach(atom -> s[0] += atom + (--t[0] > 0 ? ", " : ""));
        return s[0] + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Disjunct)) return false;

        Disjunct d = (Disjunct) obj;
        List<Atom> atomList = new ArrayList<>(this.getAtomList());
        List<Atom> atomList1 = new ArrayList<>(d.getAtomList());
        if (atomList.size() != atomList1.size()) return false;

        for (Atom atom : atomList) {
            if (!atomList1.contains(atom)) return false;
            atomList1.remove(atom);
        }
        return atomList1.size() == 0;
    }


    //test
    public static void main(String[] args) {
        Disjunct d1 = new Disjunct("A+~B+C");
        Disjunct d2 = new Disjunct("A+B+C");
        Disjunct d3 = new Disjunct("~B+A+C");
        Disjunct d4 = new Disjunct("A+~B+C");

        System.out.println(d1 + " = " + d2 + ": " + d1.equals(d2));
        System.out.println(d1 + " = " + d3 + ": " + d1.equals(d3));
        System.out.println(d1 + " = " + d4 + ": " + d1.equals(d4));
        System.out.println(d1 + " = " + d1 + ": " + d1.equals(d1));

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }
}
