package ast;

public class Nil implements Ast {

    public int line;
    public int charPos;
    public String type;

    public Nil(int line, int charPos) {
        this.line = line;
        this.charPos = charPos;
    }

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
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
