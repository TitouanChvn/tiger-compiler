package ast;

public class FunDecBodyWithoutId implements Ast {
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public Ast expr;
    public int line;
    public int charPos;
    public String type;

    public FunDecBodyWithoutId(Ast expr, int line, int charPos) {
        this.expr = expr;
        this.line = line;
        this.charPos = charPos;
    }
    public int getCharPos() {
        return this.charPos;
    }

    public int getLine() {
        return this.line;
    }

    public String getType() {
        return this.type;
    }
    
    
}
