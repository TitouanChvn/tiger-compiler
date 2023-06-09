package ast;

import java.util.ArrayList;

public class RecCreate implements Ast {
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public ArrayList<Ast> liste;
    public int line;
    public int charPos;
    public String type;
    public RecCreate(int line, int charPos)
    {
        this.liste = new ArrayList<Ast>();
        this.line=line;
        this.charPos=charPos;
    }

    public void add(Ast ast)
    {
        this.liste.add(ast);
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
