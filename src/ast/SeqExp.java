package ast;

import java.util.ArrayList;

public class SeqExp implements Ast {

    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public ArrayList<Ast> seqExpList;
    public int line;
    public int charPos;
    public String type;

    public SeqExp(int line, int charPos) {
        this.seqExpList = new ArrayList<Ast>();
        this.line = line;
        this.charPos = charPos;
    }

    public void add(Ast seqExp) {
        this.seqExpList.add(seqExp);
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
