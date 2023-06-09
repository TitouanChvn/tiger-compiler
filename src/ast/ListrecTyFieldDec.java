package ast;

import java.util.ArrayList;

public class ListrecTyFieldDec implements Ast {

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public ArrayList<Ast> ListFD;
    public int line;
    public int charPos;
    public String type;

    public ListrecTyFieldDec(int line, int charPos) {
        this.ListFD = new ArrayList<Ast>();
        this.line = line;
        this.charPos = charPos;
    }

    public void add(Ast ListFD) {
        this.ListFD.add(ListFD);
    }

    public int getCharPos() {
        return this.charPos;
    }

    public int getLine() {
        return this.line;
    }

    public String getType() {
        return this.type;
    }
    
}
