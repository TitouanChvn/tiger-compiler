package controles_sem;

public class FunDoubleDecla extends Error {

    private String symbole;

    public FunDoubleDecla(String s, int line, int charPos ) {
        super(line, charPos);
        this.symbole=s;
    }

    @Override
    public String toString() {
        String err = "Ligne " + line + ":" + charPos + " : \u001B[31merreur: \u001B[0m La fonction "+ symbole+" est déclarée plusieurs fois.";
        return err;
    }
}