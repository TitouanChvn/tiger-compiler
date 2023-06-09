package controles_sem;

import java.util.ArrayList;

public class ArgCall extends Error {

    private String func;
    private ArrayList<String> types;

    public ArgCall(int line, int charPos, String f, ArrayList<String> t) {
        super(line, charPos);
        this.func = f;
        this.types = t;
    } 

    @Override
    public String toString() {
        String err = "Ligne " + line + " : " + charPos + " : \u001B[31merreur: \u001B[0m Mauvais arguments donnés à la fonction "+func+", requiert "+ types.toString() + ".";
        return err;
    }
}