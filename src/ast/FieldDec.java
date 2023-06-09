package ast;

public class FieldDec implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast idLeft;
    public Ast idRight;
    public int line;
    public int charPos;
    public String type;

    public FieldDec(Ast idLeft, Ast idRight, int line, int charPos)
    {
        this.idLeft=idLeft;
        this.idRight=idRight;
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
