package controles_sem;

public class NotAnIndice extends Error {

    public NotAnIndice(int line,int charPos){
        super(line,charPos);
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  L'indice n'est pas un int.";
        return err;
    
    }
}
