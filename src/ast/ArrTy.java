package ast;

public class ArrTy implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public Ast type;
    public int line;
    public int charPos;
    public String tipe;


    public ArrTy(Ast type, int line, int charPos){
        this.type = type;
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
        return this.tipe;
    }
    
}
