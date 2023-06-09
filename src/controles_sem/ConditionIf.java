package controles_sem;

public class ConditionIf extends Error {
    
    public ConditionIf(int line,int charPos){
        super(line,charPos);
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  La type de la condition du If n'est pas int.";
        return err;
    }
}
