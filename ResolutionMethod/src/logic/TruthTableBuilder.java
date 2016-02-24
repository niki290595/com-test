package logic;

import java.util.ArrayList;

/**
 * Created by Ilya on 19.12.2015.
 */
public class TruthTableBuilder {
    private char[] chars;
    private int[][] table;
    private LogicTree tree;

    public TruthTableBuilder(LogicTree tree) {
        this.tree = tree;
    }

    public void build() {
        //кол-во атомов
        ArrayList<Character> chars2 = tree.getAtomsArray();
        int n = chars2.size();
        chars = new char[n + 1];
        for (int i = 0; i < n; i++) {
            chars[i + 1] = chars2.get(i);
        }

        //System.out.println("count atoms = " + n);
        int m = (int) Math.round(Math.pow(2, n));
        //System.out.println("count row = " + m + "\n");

        table = new int[m + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            int series = (int) Math.round(Math.pow(2, i));
            int size = m / series;
            for (int j = 1; j <= series; j++) {
                int value = (j % 2 != 0)?0:1;
                for (int k = (j-1)*size + 1; k <= j*size; k++) {
                    table[k][i] = value;
                }
            }
        }

        for (int i = 1; i <= m; i++) {
            table[i][0] = tree.getSolve(chars, table[i]);
        }
/*
        //print table
        System.out.println("Truth table:");
        for (int i = 1; i <= n; i++) {
            System.out.print(chars[i] + "  ");
        }
        System.out.println("F?");
        for (int i = 1; i <= m ; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(table[i][j] + "  ");
            }
            System.out.println(table[i][0]);
        }
*/
    }

    public char[] getChars() {
        return chars;
    }

    public int[][] getTable() {
        return table;
    }

}
