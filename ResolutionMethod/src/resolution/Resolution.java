package resolution;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ilya on 31.01.2016.
 */
public class Resolution {
    private final int MAX_RESOLVENT_COUNT = 1000;
    private List<Disjunct> disjunctList;
    private List<Resolvent> resolventList;
    private int resolventCount;
    private Map<String, List<Disjunct>> table;

    public Resolution(List<Disjunct> disjunctList) {
        this.disjunctList = new ArrayList<>(disjunctList);
    }

    class Resolvent {
        private Integer index, left, right;
        private Disjunct disjunct;

        public Resolvent(Integer index, Integer left, Integer right, Disjunct disjunct) {
            this.index = index;
            this.left = left;
            this.right = right;
            this.disjunct = disjunct;
        }

        public Resolvent(Integer index, Disjunct disjunct) {
            this(index, null, null, disjunct);
        }

        public Disjunct getDisjunct() {
            return disjunct;
        }

        @Override
        public String toString() {
            String s2 = (left == null) ? "" : "from (" + left + ") (" + right + ") ";
            return "(" + index + "): " + s2 + disjunct.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (!(obj instanceof Resolvent)) return false;
            Resolvent r = (Resolvent) obj;
            return this.getDisjunct().equals(r.getDisjunct());
        }
    }

    public int buildBySimpleEnumeration() {
        init();
        List<Disjunct> disList = new ArrayList<>(disjunctList);
        int leftEdge = 0;
        int rightEdge = disList.size();
        while (resolventCount < 1000) {
            for (int i = 0; i < rightEdge; i++) {
                //todo check it
                for (int j = (i + 1 < leftEdge) ? leftEdge : (i + 1); j < rightEdge; j++) {
                    Disjunct d1 = disList.get(i);
                    Disjunct d2 = disList.get(j);
                    if (isHasResolvent(d1, d2)) {
                        Resolvent resolvent = buildResolvent(d1, d2);
                        if (resolvent != null) {
                            resolventList.add(resolvent);
                            resolventCount++;
                            //System.out.println(resolvent);
                            disList.add(resolvent.getDisjunct());
                            if (isProve(resolvent)) {
                                return 0;
                            }
                        }
                    }
                }
            }
            if (rightEdge == disList.size()) {
                return 1;
            }
            leftEdge = rightEdge;
            rightEdge = disList.size();
        }
        return 2;
    }

    private boolean isHasResolvent(Disjunct d1, Disjunct d2) {
        boolean flag = false; //definition the first entry contrAtoms
        for (Atom atom1 : d1.getAtomList()) {
            for (Atom atom2 : d2.getAtomList()) {
                if (atom1.getName().equals(atom2.getName()) &&
                        atom1.getNot().equals(!atom2.getNot())) {
                    if (!flag) {
                        flag = true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return flag;
    }

    private Resolvent buildResolvent(Disjunct d1, Disjunct d2) {
        List<Atom> newAtomList = new ArrayList<>();
        addUniqueAtoms(d1.getAtomList(), newAtomList);
        addUniqueAtoms(d2.getAtomList(), newAtomList);

        for (Atom atom1 : d1.getAtomList()) {
            for (Atom atom2 : d2.getAtomList()) {
                if (atom1.getName().equals(atom2.getName()) &&
                        atom1.getNot().equals(!atom2.getNot())) {
                    newAtomList.remove(atom1);
                    newAtomList.remove(atom2);
                    Resolvent res = new Resolvent(resolventList.size(),
                            resolventList.indexOf(findResolvent(d1)),
                            resolventList.indexOf(findResolvent(d2)),
                            new Disjunct(newAtomList));
                    return (resolventList.contains(res)) ? null : res;
                }
            }
        }
        return null;
    }

    private void addUniqueAtoms(List<Atom> fromList, List<Atom> toList) {
        fromList.stream().filter(atom -> !toList.contains(atom)).forEach(toList::add);
    }

    private boolean isProve(Resolvent resolvent) {
        return resolvent.getDisjunct().countElement() == 0;
    }

    public int build() {
        init();
        Disjunct disjunct = findDisjunct();
        while (true) {
            List<Disjunct> disjunctList = findDisjunctsForResolvent(disjunct);
            Resolvent resolvent = buildResolvent(disjunct, disjunctList.get(0));
            resolventList.add(resolvent);
            resolventCount++;
            disjunct = (resolvent.getDisjunct().countElement() == 1) ? resolvent.getDisjunct() : findDisjunct();
            if (isProve(disjunct)) {
                return 0;
            }
        }
    }

    private void init() {
        resolventCount = 0;
        resolventList = new ArrayList<>(disjunctList.stream().map(disjunct -> new Resolvent(disjunctList.indexOf(disjunct), disjunct)).collect(Collectors.toList()));
        table = new HashMap<>();
        disjunctList.forEach(this::addDisjunctToTable);
    }

    private void addDisjunctToTable(Disjunct disjunct) {
        List<String> keyList = disjunct.getKeys();
        for (String key : keyList) {
            addDisjunctToTable(key, disjunct);
        }
    }

    private void addDisjunctToTable(String key, Disjunct disjunct) {
        if (table.get(key) == null) {
            List<Disjunct> disjunctList = new ArrayList<>();
            table.put(key, disjunctList);
        }
        table.get(key).add(disjunct);
    }

    private Disjunct findDisjunct() {
        for (Disjunct disjunct : disjunctList) {
            if (isHasResolvent(disjunct)) {
                return disjunct;
            }
        }
        return null;
    }

    private boolean isHasResolvent(Disjunct disjunct) {
        if (disjunct.countElement() != 1) {
            return false;
        }
        List<Disjunct> secondDisjunctList = findDisjunctsForResolvent(disjunct);
        //todo надо проверить не только количество, но и может ли из этого получится новый дизъюнкт, т.е.
        //возможно придется сделать весь алгоритм унификации, чтобы понять, что такие дизъюнкты уже существуют,
        //либо применять новый алгоритм
        return secondDisjunctList != null;
    }

    private List<Disjunct> findDisjunctsForResolvent(Disjunct disjunct) {
        String key = disjunct.getFirstAtom().getAlternativeKeyWithoutElements();
        return table.get(key);
    }

/*
    private Resolvent buildResolvent(Disjunct disjunct, Disjunct disjunct1) {
        List<Atom> atomList = disjunct1.getAtomList();
        Atom atom1 = disjunct.getFirstAtom();
        Atom atom2 = null;
        for (Atom atom : atomList) {
            if (atom.getKeyWithoutElements().equals(atom1.getAlternativeKeyWithoutElements())) {
                atom2 = atom;
                break;
            }
        }
        List<Atom> newAtomList = unification(atom1, atom2, atomList);
        Disjunct newDisjunct = new Disjunct(newAtomList);
        return new Resolvent(resolventList.size(), resolventList.indexOf(findResolvent(disjunct)), resolventList.indexOf(findResolvent(disjunct1)), newDisjunct);
    }
*/

    private Resolvent findResolvent(Disjunct disjunct) {
        for (Resolvent resolvent : resolventList) {
            if (resolvent.getDisjunct() == disjunct) {
                return resolvent;
            }
        }
        return null;
    }

    private List<Atom> unification(Atom atomNew, Atom atomOld, List<Atom> atomList) {
        if (atomNew.getAtomList() == null) {
            return newAtomListWithoutUnification(atomNew, atomOld, atomList);
        }

        Map<String, Atom> unificatator = new HashMap<>();
        for (int i = 0; i < atomNew.atomList.size(); i++) {
            unificatator.put(atomOld.atomList.get(i).toString(), atomNew.atomList.get(i));
        }

        List<Atom> resList = new ArrayList<>();
        atomList.stream().filter(atom -> !atomNew.getKeyWithoutElements().equals(atom.getAlternativeKeyWithoutElements())).forEach(atom -> {
            List<Atom> newAtomList = new ArrayList<>();
            for (Atom atom1 : atom.atomList) {
                Atom newAtom = unificatator.get(atom1.toString());
                newAtomList.add(newAtom == null ? atom : newAtom);
            }
            resList.add(new Atom(atom.getName(), atom.getNot(), newAtomList));
        });
        return resList;
    }

    private List<Atom> newAtomListWithoutUnification(Atom atomNew, Atom atomOld, List<Atom> atomList) {
        return atomList.stream().filter(atom -> !atom.getKey().equals(atomNew.getAlternativeKey())).collect(Collectors.toList());
    }

    private boolean isProve(Disjunct disjunct) {
        if (disjunct.countElement() > 1) {
            return false;
        }
        List<Disjunct> disjunctList = table.get(disjunct.getFirstAtom().getAlternativeKey());
        if (disjunctList == null) {
            return false;
        }

        resolventList.add(buildResolvent(disjunct, disjunctList.get(0)));
        return true;
    }

    @Override
    public String toString() {
        resolventList.forEach(System.out::println);
        return "";
    }

    public void print() {
        System.out.println("Count resolvent: " + resolventCount + "\n");
        List<Resolvent> resList = new ArrayList<>();
        getTree(resolventList.get(resolventList.size() - 1), resList);
        Collections.sort(resList, new Comparator<Resolvent>() {
            @Override
            public int compare(Resolvent o1, Resolvent o2) {
                return o1.index.compareTo(o2.index);
            }
        });
        resList.forEach(System.out::println);
    }

    private void getTree(Resolvent resolvent, List<Resolvent> resList) {
        if (resolvent.left != null)
            getTree(resolventList.get(resolvent.left), resList);
        if (resolvent.right != null)
            getTree(resolventList.get(resolvent.right), resList);
        resList.add(resolvent);
    }


}
