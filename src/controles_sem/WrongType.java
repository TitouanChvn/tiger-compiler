package controles_sem;

public class WrongType extends Error{

    String op;

    
    public WrongType(int line,int charPos, String op){
        super(line,charPos);
        this.op = op;
    }


    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le type est incompatible avec l'opération \"" + op + "\".";
        return err;
    
    }
}
