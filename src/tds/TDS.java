package tds;

import java.util.ArrayList;

public class TDS {
    public ArrayList<Symbole> table;
    public String nom;
    private int deplacement;
    public TDS parent;
    public int imbrication;
    public String type;
    public String state;
    public ArrayList<Tuple> tuples;

    public TDS(String name, TDS parent, int imbrication, String type, ArrayList<Tuple> tuples) {
        this.nom = name;
        this.parent = parent;
        this.deplacement = 0;
        this.imbrication = imbrication;
        this.table = new ArrayList<Symbole>();
        this.type = type;
        this.tuples = tuples;
    }

    public String getTDSContent(){
        String content = "<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">";
        content += "<TR><TD COLSPAN=\"3\"><B>" + this.nom + "</B></TD></TR>";
        for (Symbole s : table) {
            if (s instanceof Block) {
                continue;
            }
            content += s.getTDSContent();
        }
        content += "</TABLE>";
        return content;
    }

    public int getDeplacementType(String name){
        for (Tuple t : tuples) {
            if (t.name.equals(name)) {
                return t.deplacement;
            }
        }
        return 0;
    }

    
    public TDS(String name, int deplacement, TDS parent, int imbrication, String type) {
        this.nom = name;
        this.parent = parent;
        this.deplacement = deplacement;
        this.imbrication = imbrication;
        this.table = new ArrayList<Symbole>();
        this.type = type;
    }

    public void add(Type t){

        this.deplacement += t.getDeplacement();
        t.deplacement = this.deplacement;
        this.table.add(t);
    }

    public void add(Variable i){
        this.deplacement += i.getDeplacement();
        i.deplacement = this.deplacement;

        this.table.add(i);
    }

    public void add(Fonction f){
        this.deplacement += f.getDeplacement();
        f.deplacement = this.deplacement;

        this.table.add(f);
    }

    public void add(Block b){
        this.table.add(b);
    }
    
    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public int getDeplacement(String name) {
        int dec = -1;
        for(Symbole x : this.table){
            if(x.nom.equals(name)){
                dec = x.getDeplacement();
                break;
            }
        }
        return dec;
    }
    public TDS getChild(String nom) {
        for (Symbole s : this.table) {
            if (s instanceof Fonction) {
                Fonction bt = (Fonction) s;
                if (bt.tds.nom.equals(nom)) {
                    return bt.tds;
                }
            }
            if (s instanceof Block) {
                Block b = (Block) s;
                if(b.tds.nom.equals(nom)){
                    return b.tds;
                }
            }
        }
        return null;
    }

    public ArrayList<TDS> getChilds(String nom) {
        ArrayList<TDS> toReturn = new ArrayList<>();
        for (Symbole s : this.table) {
            if (s instanceof Fonction) {
                Fonction bt = (Fonction) s;
                if (bt.tds.nom.equals(nom)) {
                    toReturn.add(bt.tds);
                }
            }
            if (s instanceof Block) {
                Block b = (Block) s;
                if(b.tds.nom.equals(nom)){
                    toReturn.add(b.tds);
                }
            }
        }
        return toReturn;
    }

    
}
