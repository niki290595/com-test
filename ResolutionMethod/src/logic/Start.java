package logic;

import resolution.Main;

import java.util.Scanner;

/**
 * Created by Ilya on 18.12.2015.
 */
public class Start {

    public static void main(String[] args) {
        //считать формулу в строку
        //String f = read();
        String f = "(A+B)>C*(A+-B)>(D+A)";
        System.out.println();

        System.out.println("Исходные данные: \nF = " + f + "\n");

        int index = f.lastIndexOf('>');
        String f1 = f.substring(0, index);
        String f2 = "-(" + f.substring(index + 1, f.length()) + ")";

        System.out.println("Для доказательства методом резолюций разделим исходную формулу на две состовляющие:");
        System.out.println(f1);
        System.out.println(f2 + "\n");

        //преобразововать в рабочий вид
        //LogicTree tree = Parser.getLogicTree(f);
        //System.out.println("tree = " + tree + "\n");
        LogicTree tree1 = Parser.getLogicTree(f1);
        LogicTree tree2 = Parser.getLogicTree(f2);

        //построить таблицу истинности
        //TruthTableBuilder tableBuilder = new TruthTableBuilder(tree);
        //tableBuilder.build();
        //System.out.println();

        TruthTableBuilder tableBuilder1 = new TruthTableBuilder(tree1);
        tableBuilder1.build();
        TruthTableBuilder tableBuilder2 = new TruthTableBuilder(tree2);
        tableBuilder2.build();

        //построить СКНФ
        //SKNFBuilder sknfBuilder = new SKNFBuilder(tableBuilder.getChars(), tableBuilder.getTable());
        //sknfBuilder.build();

        SKNFBuilder sknfBuilder1 = new SKNFBuilder(tableBuilder1.getChars(), tableBuilder1.getTable());
        sknfBuilder1.build();
        SKNFBuilder sknfBuilder2 = new SKNFBuilder(tableBuilder2.getChars(), tableBuilder2.getTable());
        sknfBuilder2.build();

        //построить КНФ
        //KNFBuilder knfBuilder = new KNFBuilder(sknfBuilder.getAtoms(), sknfBuilder.getDisjuncts());
        //knfBuilder.build();

        KNFBuilder knfBuilder1 = new KNFBuilder(sknfBuilder1.getAtoms(), sknfBuilder1.getDisjuncts());
        knfBuilder1.build();
        KNFBuilder knfBuilder2 = new KNFBuilder(sknfBuilder2.getAtoms(), sknfBuilder2.getDisjuncts());
        knfBuilder2.build();

        System.out.println("Для первой формулы получили дизъюнкты: ");
        System.out.println(knfBuilder1.print());
        System.out.println("\nДля второй формулы получили дизъюнкты: ");
        System.out.println(knfBuilder2.print());

        String[] mas = {knfBuilder1.print() + ", " + knfBuilder2.print()};
        Main.main(mas);
    }

    public static String read(){
        System.out.print("enter a logical formula:\n" +
                "0, 1 - const\n" +
                "A, B, C, D, E, ..., Z - atoms\n" +
                "\"+\" - or\n" +
                "\"*\" - and\n" +
                "\"-\" - not\n" +
                "\"~\" - equivalence\n" +
                "\">\" - follow\nenter: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
