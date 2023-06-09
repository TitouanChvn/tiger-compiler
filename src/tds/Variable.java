package tds;



public class Variable extends Symbole{

    public Variable(String name, String type, int deplacement) {
        super(name);
        this.deplacement = deplacement;
        this.type = type;
    }

    public int getDeplacement() {
        return deplacement;
    }
    
}
