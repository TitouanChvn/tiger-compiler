package controles_sem;

public class UnknownType extends Error {

    public UnknownType(int line,int charPos){
        super(line,charPos);
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le type n'est pas déclaré auparavant.";
        return err;
    }
    
}
