package ast;

public class TypeDec implements Ast {
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast ty;
    public int line;
    public int charPos;
    public String type;
    public TypeDec(Ast id, Ast ty, int line, int charPos)
    {
        this.id=id;
        this.ty=ty;
        this.charPos = charPos;
        this.line = line;
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
