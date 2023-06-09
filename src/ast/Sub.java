package ast;

public class Sub implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;
    public int line;
    public int charPos;
    public String type;

    public Sub(Ast left, Ast right, int line, int charPos)
    {
        this.left=left;
        this.right=right;
        this.line=line;
        this.charPos=charPos;
    }

    public Sub(Ast left, Ast right)
    {
        this.left=left;
        this.right=right;
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
