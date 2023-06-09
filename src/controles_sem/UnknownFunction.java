package controles_sem;

public class UnknownFunction extends Error{

    private String symbole;

    public UnknownFunction(int line,int charPos, String symbole){
        super(line,charPos);
        this.symbole = symbole;
    }

    @Override
    public String toString(){
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m La fonction \"" +  symbole  +"\" n'est pas déclarée .";
        return err;
    }
    
}
