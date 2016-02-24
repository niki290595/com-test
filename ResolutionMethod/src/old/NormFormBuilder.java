package old;

import logic.LogicLiganent;

/**
 * Created by User on 26.11.2015.
 */
public class NormFormBuilder {
/*
    public static LogicTree getNormForm(LogicTree tree) {
        if (tree != null && tree.liganent != null) {
            LogicTree F = getNormForm(tree.left);
            LogicTree G = getNormForm(tree.right);
            LogicTree left;
            LogicTree right;
            LogicTree newTree;
            switch (tree.liganent) {
                case LogicLiganent.NOT:
                    return new LogicTree(F, G, tree.liganent);
                case LogicLiganent.AND:
                    return new LogicTree(F, G, tree.liganent);
                case LogicLiganent.OR:
                    return new LogicTree(F, G, tree.liganent);
                case LogicLiganent.FOLLOW:
                    left = new LogicTree(null, F, LogicLiganent.NOT);
                    newTree = new LogicTree(left, G, LogicLiganent.OR);
                    return getNormForm(newTree);
                case LogicLiganent.EQUIVALENCE:
                    left = new LogicTree(F, G, LogicLiganent.FOLLOW);
                    right = new LogicTree(G, F, LogicLiganent.FOLLOW);
                    newTree = new LogicTree(left, right, LogicLiganent.AND);
                    return getNormForm(newTree);
            }
        }
        return tree;
    }

    public static void main(String[] args) {
        for (char c = 0; c < Character.MAX_VALUE; c++) {
            System.out.print(((int) c) + "  ");
            System.out.println(c);
        }
    }*/
}
