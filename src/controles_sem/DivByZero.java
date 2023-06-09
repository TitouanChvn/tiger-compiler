package controles_sem;

public class DivByZero extends Error{
    public DivByZero(int line,int charPos){
        super(line,charPos);
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m Division par Zero.";
        return err;
    }
}
    

