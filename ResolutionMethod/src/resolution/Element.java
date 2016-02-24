package resolution;

/**
 * Created by Ilya on 30.01.2016.
 */
public abstract class Element {
    Boolean not; //A - true, ~A - false;
    Character name;

    protected void initNot(String formula) {
        not = formula.indexOf('~') != 0;
    }

    protected void initName(String formula) {
        String s = (not.booleanValue()) ? formula : formula.substring(1, formula.length());
        name = s.charAt(0);
    }

    public Boolean getNot() {
        return not;
    }


}
