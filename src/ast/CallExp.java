package ast;

public class CallExp implements Ast {
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public Ast id;
    public Ast ListExp;
    public String type;
    public int line;
    public int charPos;


    public CallExp(Ast id, Ast ListExp, int line, int charPos) {
        this.id = id;
        this.ListExp = ListExp;
        this.line = line;
        this.charPos = charPos;
    }

    public int getCharPos() {
        return this.id.getCharPos();
    }

    public int getLine() {
        return this.id.getLine();
    }
    
    public String getType(){
        return this.type;
    }
    
    
}
