package ast;

import java.util.ArrayList;

public class Program implements Ast{

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> instrList;
    public int line;
    public int charPos;
    public String type;

    public Program(int line, int charPos) {
        this.instrList = new ArrayList<Ast>();
        this.line = line;
        this.charPos = charPos;
    }
    public void add(Ast instr) {
        this.instrList.add(instr);
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
