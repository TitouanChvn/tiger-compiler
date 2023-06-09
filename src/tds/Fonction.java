package tds;

public class Fonction extends Symbole {

    protected String returnType;
    public String typeRetour;
    public TDS tds;


    Fonction(String name, String returnType, String typeRetour) {
        super(name);
        this.returnType = returnType;
        this.type = "Fonction : " + returnType;
        this.typeRetour = typeRetour;
    }
    Fonction(String name, TDS tds, String returnType, String typeRetour) {
        super(name);
        this.tds=tds;
        this.returnType = returnType;
        this.type = "Fonction : " + returnType;
        this.typeRetour = typeRetour;
    }

    @Override
    public String getTextDeplacement(){
        return "0";
    }
    
}
