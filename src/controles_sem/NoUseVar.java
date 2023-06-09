package controles_sem;

public class NoUseVar extends Warning {

    public String varName;

    public NoUseVar(String varName, int line, int charPos) {
        super(line, charPos);
        this.varName = varName;
    }
    
    @Override
    public String toString() {
        String err = "Ligne "+line+" : "+charPos+" : \u001B[33mwarning: \u001B[0m La variable "+varName+" n'est pas utilisée.";
        return err;
    }
}
