package controles_sem;

public class WrongNumberOfFields extends Error {

    private int expected;
    private int found;

    public WrongNumberOfFields(int line, int charPos ,int expected, int found) {
        super(line,charPos);
        this.expected = expected;
        this.found = found;
    }

    public String toString() {
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le nombre de champ pour l'initalisation est incorrecte. Nombre de champ attendu : "+expected+" Nombre trouvé : "+found+".";
        return err;    }

}
