package ast;

import java.util.ArrayList;

public class CallExpList implements Ast {

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public ArrayList<Ast> CallExpList;
    public int line;
    public int charPos;
    public String type;


    public CallExpList(int line, int charPos) {
        this.CallExpList = new ArrayList<Ast>();
        this.line = line;
        this.charPos = charPos;
        
    }

    public void add(Ast CallExpList) {
        this.CallExpList.add(CallExpList);
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
