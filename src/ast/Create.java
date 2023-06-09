package ast;

public class Create implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast createA;
    public int line;
    public int charPos;
    public String type;

    public Create(Ast id, Ast createA, int line, int charPos)
    {
        this.id=id;
        this.createA=createA;
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
