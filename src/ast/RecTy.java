package ast;

public class RecTy implements Ast{

    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public Ast ListFD;
    public int line;
    public int charPos;
    public String type;

    public RecTy(Ast ListFD, int line, int charPos)
    {
        this.ListFD=ListFD;
        this.line=line;
        this.charPos=charPos;
    }
    public int getCharPos()
    {
        return this.charPos;
    }

    public int getLine()
    {
        return this.line;
    }

    public String getType()
    {
        return this.type;
    }
    
    
}
