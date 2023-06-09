package ast;

import java.util.ArrayList;

public class ListExp implements Ast {
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Ast> ListExp;
    public int line;
    public int charPos;
    public String type;

    public ListExp(int line, int charPos) {
        this.ListExp = new ArrayList<Ast>();
        this.line=line;
        this.charPos=charPos;
    }

    public void add(Ast ListExp) {
        this.ListExp.add(ListExp);
    }
    public int getLine()
    {
        return this.line;
    }
    public int getCharPos()
    {
        return this.charPos;
    }
    public String getType()
    {
        return this.type;
    }
}
