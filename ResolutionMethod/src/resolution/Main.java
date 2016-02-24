package resolution;

import java.util.List;

/**
 * Created by Ilya on 30.01.2016.
 */
public class Main {
    public static void main(String[] args) {
        String f = args.length != 0 ? args[0] : "";

        //page 87
        String f1 = "S={~T(x, y, u, v) + P(x, y, u, v), " +
                "~P(x, y, u, v) + E(x, y, v, u, v, y), " +
                "T(a, b, c, d), " +
                "~E(a, b, d, c, d, b)}";

        String f2 = "S={~P+Q,~Q,P}";

        String f3 = "S={" +
                "P+Q," +
                "~P+Q," +
                "P+~Q," +
                "~P+~Q}";

        String f4 = "S={" +
                "Z+~Y," +
                "X+D," +
                "~X+Z," +
                "~Y+D," +
                "Z+D}";

        String f5 = "S={" +
                "X+Y+Z," +
                "~X+Y+Z," +
                "X+~Y+Z," +
                "X+~Y+~Z," +
                "~X+Y+~Z," +
                "X+Y+~Z}";

        String f6 = "S={" +
                "X+Y," +
                "X+Z," +
                "~Y+~Z," +
                "~X}";

        System.out.println("\nНа вход методу резолюций попадает следующий список дизъюнкт: \n" + f + "\n");
        List<Disjunct> disjunctList = Parser.getInstance().buildDisjunctList(f);
        //ystem.out.println(disjunctList);
        Resolution res = new Resolution(disjunctList);
        switch (res.buildBySimpleEnumeration()) {
            case 0: res.print(); return;
            case 1: System.out.println("Построенны все резольвенты, но доказать не удалось");
                System.out.println(res);
                return;
            case 2: System.out.println("Построенно более 1000 резольвент. Возможно, методом резолюции доказать не получилось");
        }
    }
}
