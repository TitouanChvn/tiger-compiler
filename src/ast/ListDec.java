package ast;

import java.util.ArrayList;

public class ListDec implements Ast{
    public <T> T accept(AstVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
    public ArrayList<Ast> ListDec;
    public int line;
    public int charPos;
    public String type;


    public ListDec(int line, int charPos)
    {
        this.ListDec=new ArrayList<Ast>();
        this.line=line;
        this.charPos=charPos;
    }
    public void add(Ast ListDec)
    {
        this.ListDec.add(ListDec);
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
