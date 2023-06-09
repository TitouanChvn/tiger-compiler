package ast;

import java.util.ArrayList;

public class LValue implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public Ast id;
    public ArrayList<Ast> values;
    public int line;
    public int charPos;
    public String type;

    public LValue(Ast id, int line, int charPos)
    {
        this.id=id;
        this.values = new ArrayList<Ast>();
        this.line=line;
        this.charPos=charPos;
    }

    public void add(Ast value)
    {
        this.values.add(value);
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
