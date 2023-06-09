package ast;

public class VarDec implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast varDecA;
    public int line;
    public int charPos;
    public String type;

     public VarDec(Ast id,Ast varDecA,int line,int charPos)
    {
        this.id=id;
        this.varDecA=varDecA;
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

