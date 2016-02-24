package old;

import gui.Controller;
import logic.LogicTree;

/**
 * Created by User on 26.11.2015.
 */
public class Main {
    public static void main(String[] args) {
        String logicalFormula = args[0];
        LogicTree tree = Parser.getLogicTree(logicalFormula);

        Controller.addToLog(tree.toString());
        //Controller.addToLog(NormFormBuilder.getNormForm(tree).toString());
    }
}
