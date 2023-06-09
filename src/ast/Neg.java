package ast;

public class Neg implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast NegExp;
    public int line;
    public int charPos;
    public String type;

    public Neg(Ast NegExp, int line, int charPos){
        this.NegExp = NegExp;
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

    


