package ast;

public interface Ast {
    public <T> T accept (AstVisitor<T> visitor);
    public String getType();
    public int getLine();
    public int getCharPos();
}
