package ast;

public class Let implements Ast{

    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public Ast ListDec;
    public Ast expr;
    public int line;
    public int charPos;
    public String type;

    public Let(Ast ListDec, Ast expr, int line,int charPos)
    {
        this.ListDec=ListDec;
        this.expr=expr;
        this.line=line;
        this.charPos=charPos;
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
