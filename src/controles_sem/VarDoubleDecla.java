package controles_sem;

public class VarDoubleDecla extends Error{
    private String symbole;

    public VarDoubleDecla(int line,int charPos, String symb){
        super(line,charPos);
        this.symbole=symb;
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m Déclarations multiples de la variable "+symbole+".";
        return err;
    }
}
