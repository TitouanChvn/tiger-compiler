package ast;

public class Identifier implements Ast {

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public String name;
    public String type;
    public int line;
    public int charPos;

    public Identifier(String name, int line, int charPos){
        this.name = name;
        this.line=line;
        this.charPos=charPos;
    }
  
    public int getCharPos()
    {
        return this.charPos;

    }
    public String getType()
    {
        return this.type;
    }
    public int getLine()
    {
        return this.line;
    }


}
