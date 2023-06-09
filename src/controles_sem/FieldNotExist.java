package controles_sem;

public class FieldNotExist extends Error{

    private String name;

    public FieldNotExist(int line,int charPos, String name){
        super(line,charPos);
        this.name = name;
    }

    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m Le champ "+ name +" n'existe pas.";
        return err;
    }    
    
}
