package logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ilya on 19.12.2015.
 */
public class KNFBuilder {
    private ArrayList<Atom> atoms;
    private ArrayList<Disjunct> oldDisjuncts;
    private ArrayList<Disjunct> disjuncts;

    public KNFBuilder(ArrayList<Atom> atoms, ArrayList<Disjunct> disjuncts) {
        this.atoms = atoms;
        this.oldDisjuncts = disjuncts;
    }

    //перевод дизъюнкты в числовой вид (A-BCD -> 1011)
    private char[] refactor(Disjunct disjunct) {
        char[] item = new char[disjunct.size()];
        for (int i = 0; i < item.length; i++) {
            item[i] = disjunct.getAtom(i).getNot() ? '1' : '0';
        }
        return item;
    }

    //перевод всех дизъюнкт в числовой вид (A-BCD -> 1011)
    private ArrayList<char[]> refactor(ArrayList<Disjunct> Disjuncts) {
        ArrayList<char[]> res = new ArrayList<>();
        for (Disjunct disjunct : Disjuncts) {
            res.add(refactor(disjunct));
        }
        return res;
    }

    //определение группы для дизъюнкта
    private int detGroup(char[] item) {
        int count = 0;
        for (int i = 0; i < item.length; i++)
            if (item[i] == '1') count++;
        return count;
    }

    //группировка относительно кол-ву входящих единиц
    private ArrayList<char[]>[] grouping(ArrayList<char[]> disjuncts) {
        ArrayList<char[]>[] items = new ArrayList[atoms.size()/2 + 1];
        for (int i = 0; i < items.length; i++)
            items[i] = new ArrayList<>();

        for (char[] disjunct : disjuncts)
            items[detGroup(disjunct)].add(disjunct);
        return items;
    }



    private HashMap<String, ArrayList<char[]> > stepOne(ArrayList<char[]>[] items){
        HashMap<String, ArrayList<char[]> > groups = new HashMap<>();
        for (int i = 0; i < items.length - 1; i++) {
            ArrayList<char[]> groupNow = items[i];
            ArrayList<char[]> groupNext = items[i + 1];
            for (char[] itemNow : groupNow) {
                for (char[] itemNext : groupNext) {
                    char[] mask = getMask(itemNow, itemNext);
                    if (mask != null)
                        add(groups, getGroupMask(mask), mask);
                }
            }
        }
        return groups;
    }

    private ArrayList<char[]>[] createGroups(ArrayList<char[]> groups) {
        ArrayList<char[]>[] items = new ArrayList[atoms.size() + 1];
        for (int i = 0; i < items.length; i++) {
            items[i] = new ArrayList<>();
        }

        for (char[] item : groups) {
            items[detGroup(item)].add(item);
        }
        return items;
    }

    private String getGroupMask(char[] mask) {
        String res = "";
        for (int i = 0; i < mask.length; i++) {
            char c = mask[i];
            res += (c == 'X')? 'X' : '-';
        }
        return res;
    }

    private char[] getMask(char[] item1, char[] item2) {
        char[] mask = new char[item1.length];
        int count = 0;
        for (int i = 0; i < item1.length; i++) {
            if (item1[i] != item2[i]) {
                if (++count > 1) return null;
                mask[i] = 'X';
            } else {
                mask[i] = item1[i];
            }
        }
        if (count == 1)
            return mask;
        else return null;
    }

    private boolean equals(char[] c1, char[] c2) {
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i])
                return false;
        }
        return true;
    }

    private void add(HashMap<String, ArrayList<char[]>> toMap, String key, char[] item) {
        if (toMap.containsKey(key)) {
            ArrayList<char[]> list = toMap.get(key);
            for (char[] elem : list) {
                if (equals(elem, item))
                    return;
            }
            toMap.get(key).add(item);
        } else {
            ArrayList<char[]> list = new ArrayList<>();
            list.add(item);
            toMap.put(key, list);
        }
    }

    private void add(HashMap<String, ArrayList<char[]>> toMap, HashMap<String, ArrayList<char[]>> fromMap) {
        for (String key : fromMap.keySet()) {
            for (char[] item : fromMap.get(key)) {
                add(toMap, key, item);
            }
        }
    }

    public void build() {
        //перевели все в числовой вид
        ArrayList<char[]> disjuncts = refactor(oldDisjuncts);

        //группировка
        ArrayList<char[]>[] disjunctsInGroups = grouping(disjuncts);

        //склеивание
        HashMap<String, ArrayList<char[]>> groups = stepOne(disjunctsInGroups);

        HashMap<String, ArrayList<char[]>> prevGroups = groups;
        boolean wasChange;
        do {
            wasChange = false;
            HashMap<String, ArrayList<char[]>> newGroups = new HashMap<>();
            for (String key : prevGroups.keySet()) {
                ArrayList<char[]> group = prevGroups.get(key);
                HashMap<String, ArrayList<char[]>> addGroups = stepOne(createGroups(group));
                for (String key2 : addGroups.keySet()) {
                    wasChange = true;
                    add(newGroups, addGroups);
                }
            }
            //add newgroup to allgroup
            for (String key2 : newGroups.keySet()) {
                wasChange = true;
                add(groups, newGroups);
            }
            prevGroups = newGroups;
        } while (wasChange);

/*
        System.out.println("Склеивание:");
        for (String key : groups.keySet()) {
            System.out.println(key);
            for (char[] item : groups.get(key)) {
                System.out.println(item);
            }
            System.out.println();
        }
*/

        //two step
        ArrayList<char[]> list = deleteCovered(groups);
/*
        System.out.println("\n\n");
        System.out.println("Отобранные:");
        for (char[] item : list) {
            print(item);
            System.out.print(" ");
        }
        System.out.println("\n");
*/
        int[][] table = new int[disjuncts.size()][list.size()];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = (covered(disjuncts.get(i), list.get(j)))? 1 : 0;
            }
        }
        String interval = "";
        for (int i = 0; i < atoms.size()/2 - 1; i++) {
            interval += " ";
        }
/*
        System.out.println("table");
        System.out.print(interval);
        for (int i = 0; i < table[0].length; i++) {
            print(list.get(i));
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < table.length; i++) {
            print(disjuncts.get(i));
            System.out.print("   ");
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + interval + " ");
            }
            System.out.println();
        }
*/
        ArrayList<char[]> finalDisjuncts = new ArrayList<>();

        for (int i = 0; i < table.length; i++) {
            int ind = -2;
            for (int j = 0; j < table[0].length; j++) {
                if (ind == -2) {
                    if (table[i][j] == 1) ind = j;
                } else
                    if (table[i][j] == 1) {
                        ind = -1;
                        break;
                    }
            }
            if (ind == -1) {//не ядро

            } else if (ind == -2){ //вообще все плохо
                finalDisjuncts.add(disjuncts.get(i));
            } else {//ядро
                if (!finalDisjuncts.contains(list.get(ind)))
                    finalDisjuncts.add(list.get(ind));
            }
        }

        int maxCountNotUsed = -1;
        do {
            maxCountNotUsed = -1;
            int ind = -1;
            for (int j = 0; j < table[0].length; j++) {
                if (!finalDisjuncts.contains(list.get(j))) {
                    int currMax = 0;

                    for (int i = 0; i < table.length; i++) {
                        if (table[i][j] == 1 && !finalDisjuncts.contains(disjuncts.get(i))) {
                            currMax++;
                        }
                    }

                    if (currMax > maxCountNotUsed)
                        ind = j;
                }
            }
            if (ind != -1) {
                finalDisjuncts.add(list.get(ind));
            }
        } while (maxCountNotUsed != -1);

        this.disjuncts = new ArrayList<>();
        for (char[] finalDisjunct : finalDisjuncts) {
            Disjunct d = new Disjunct();
            for (int i = 0; i < finalDisjunct.length; i++) {
                if (finalDisjunct[i] != 'X')
                    d.add(atoms.get(i*2 + ((finalDisjunct[i] - '0')+1)%2));
                /*if (finalDisjunct[i] == '1') {
                    d.add(atoms.get(i*2 + 1 + 1));
                } else if (finalDisjunct[i] == '0') {
                    d.add(atoms.get(i*2 + 1));
                }*/
            }
            this.disjuncts.add(d);
        }
/*
        System.out.println("Итоговые дизъюнкты:");
        for (Disjunct disjunct : this.disjuncts) {
            System.out.println(disjunct);
        }
*/
    }

    public String print() {
        int tmp = disjuncts.size();
        String res = "";
        for (Disjunct disjunct : disjuncts) {
            res += disjunct + (--tmp > 0 ? ", " : "");
        }
        return res;
    }



    private void print(char[] item) {
        for (int i = 0; i < item.length; i++) {
            System.out.print(item[i]);
        }
    }

    private ArrayList<char[]> deleteCovered(HashMap<String, ArrayList<char[]>> groups) {
        ArrayList<char[]>[] items = refactorItems(groups);
        ArrayList<char[]> res = new ArrayList<>();

        for (int i = 1; i < items.length; i++) {
            ArrayList<char[]> list = items[i];
            int j = 0;
            while (j < list.size()) {
                char[] item = list.get(j);
                boolean isCovered = false;
                for (int k = items.length - 1; k > 0; k--) {
                    if (!isCovered)
                        for (char[] coverItem : items[k]) {
                            isCovered = covered(item, coverItem);
                            if (isCovered) break;
                        }
                }
                if (isCovered) {
                    //delete ind j
                    list.remove(j);
                } else {
                    res.add(item);
                    j++;
                }
            }
        }
        return res;
    }

    private ArrayList<char[]>[] refactorItems(HashMap<String, ArrayList<char[]>> groups) {
        ArrayList<char[]>[] res = new ArrayList[atoms.size()/2 + 1];
        for (int i = 0; i < res.length; i++)
            res[i] = new ArrayList<>();

        for (String key : groups.keySet()) {
            ArrayList<char[]> list = groups.get(key);
            int index = count('X', key);
            for (char[] item : list) {
                res[index].add(item);
            }
        }
        return res;
    }

    private boolean covered(char[] item, char[] coverItem) {
        if (equals(item, coverItem)) return false;

        for (int i = 0; i < coverItem.length; i++) {
            char c = coverItem[i];
            if (c != 'X' && c != item[i])
                return false;
        }
        return true;
    }

    private int count(char ch, String key) {
        int count = 0;
        char[] chars = key.toCharArray();
        for (char aChar : chars) {
            if (ch == aChar)
                count++;
        }
        return count;
    }
}
