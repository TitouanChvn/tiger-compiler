package ast;

public class ArrCreate implements Ast {
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast expLeft;
    public Ast expRight;
    public int line;
    public int charPos;
    public String type;
    
    public ArrCreate(Ast expLeft, Ast expRight, int line, int charPos)
    {
        this.expLeft=expLeft;
        this.expRight=expRight;
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
