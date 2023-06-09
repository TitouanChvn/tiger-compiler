package ast;

public class StrictInf implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast exp1;
    public Ast exp2;
    public int line;
    public int charPos;
    public String type;

    public StrictInf(Ast exp1, Ast exp2, int line, int charPos)
    {
        this.exp1=exp1;
        this.exp2=exp2;
        this.line=line;
        this.charPos=charPos;
    }
    
    public int getCharPos(){
        return this.charPos;
    }

    public int getLine(){
        return this.line;
    }

    public String getType(){
        return this.type;
    }
    
}
