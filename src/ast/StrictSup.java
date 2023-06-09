package ast;

public class StrictSup implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast expr1;
    public Ast expr2;
    public int line;
    public int charPos;
    public String type;
    public StrictSup(Ast expr1, Ast expr2, int line, int charPos)
    {
        this.expr1=expr1;
        this.expr2=expr2;
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
