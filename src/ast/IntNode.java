package ast;

public class IntNode implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public int value;
    public int line;
    public int charPos;
    public String type;
    public IntNode(int value,int line,int charP)
    {
        this.value=value;
        this.line = line;
        this.charPos = charP;
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
