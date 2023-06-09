package ast;

public class Affect implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast exp;
    public int line;
    public int charPos;
    public String type;


    public Affect(Ast id, Ast exp, int line, int charPos)
    {
        this.id=id;
        this.exp=exp;
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
