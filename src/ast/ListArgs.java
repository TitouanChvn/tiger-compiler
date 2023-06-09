package ast;

import java.util.ArrayList;

public class ListArgs implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }

    public ArrayList<Ast> ListArgs;
    public int line;
    public int charPos;
    public String type;

    public ListArgs(int line, int charPos)
    {
        this.ListArgs=new ArrayList<Ast>();
        this.line=line;
        this.charPos=charPos;
    }
    
    public void add(Ast ListArgs)
    {
        this.ListArgs.add(ListArgs);
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
