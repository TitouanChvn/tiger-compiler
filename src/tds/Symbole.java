package tds;

public abstract class Symbole {

    public String nom;
    public String type;
    public int deplacement;

    public Symbole(String name) {
        this.nom = name;
    }

    public String getTDSContent(){
        return "<TR><TD>" + this.nom + "</TD><TD>" + this.type + "</TD><TD>" + this.getTextDeplacement() + "</TD></TR>";
    }

    public int getDeplacement() {
        return deplacement;
    }

    public String getTextDeplacement(){
        return Integer.toString(deplacement);
    }



}
