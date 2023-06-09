package ast;

public class For implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast exp1;
    public Ast exp2;
    public Ast exp3;
    public int line;
    public int charPos;
    public String type;
    public For(Ast id,Ast exp1, Ast exp2, Ast exp3, int line, int charPos)
    {
        this.id=id;
        this.exp1=exp1;
        this.exp2=exp2;
        this.exp3=exp3;
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
