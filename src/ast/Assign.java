package ast;

public class Assign implements Ast {
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast lValue;;
    public Ast exp;
    public int line;
    public int charPos;
    public String type;
    public Assign(Ast lValue, Ast exp, int line,int charPos)
    {
        this.lValue=lValue;
        this.exp=exp;
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
