package ast;

public class FunDec implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast ListArgs;
    public Ast FunDecBody;
    public int line;
    public int charPos;
    public String type;

    public FunDec(Ast id, Ast ListArgs, Ast FunDecBody, int line, int charPos)
    {
        this.id=id;
        this.ListArgs=ListArgs;
        this.FunDecBody=FunDecBody;
        this.line = line;
        this.charPos = charPos;
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
