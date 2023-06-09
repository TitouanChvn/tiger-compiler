package tds;

public class Type extends Symbole{

    public Type(String name, int deplacement, String typetype) {
        super(name);
        this.type = "Type : " + typetype;
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }
    
}
