package tds;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

import ast.*;

public class TDSCreator implements AstVisitor<TDS> {

    private int state = 0;
    private String nodeBuffer;
    private String linkBuffer;

    private ArrayList<TDS> pile = new ArrayList<TDS>();
    private ArrayList<TDS> depile = new ArrayList<TDS>();
    private ArrayList<Tuple> types = new ArrayList<Tuple>();

    private int imbrication_counter = 0;
    private int cmpt_let = 0;
    private int cmpt_for = 0;


    public ArrayList<TDS> getDepile(){
        return this.depile;
    }


    public TDSCreator(){
        this.state = 0;
        this.nodeBuffer = "digraph \"tds\"{\n\n\tnodesep=1;\n\tranksep=1;\n\n";
        this.linkBuffer = "\n";
        types.add(new Tuple("int", 4));
        types.add(new Tuple("string", 4));
    }

    private String nextState(){
        int returnedState = this.state;
        this.state++;
        return "N" + returnedState;
    } 


    public void dumpGraph(String filepath) throws IOException {

        FileOutputStream output = new FileOutputStream(filepath);
        dessinerTDS();

        String buffer = this.nodeBuffer + this.linkBuffer + "}";
        byte[] strToBytes = buffer.getBytes();

        output.write(strToBytes);

        output.close();

    }

    public int getDeplacementType(String name){
        for (Tuple t : types) {
            if (t.name.equals(name)) {
                return t.deplacement;
            }
        }
        return 0;
    }


    private void addTransition(String from, String dest) {
        this.linkBuffer += String.format("\t%s -> %s; \n", from, dest);

    }

    private void addNode(String node, String label) {
        this.nodeBuffer += String.format("\t%s [label=<%s>, shape=\"plaintext\"];\n", node, label);

    }

    public void dessinerTDS() {
        for (TDS tds : depile) {
            String state = nextState();
            tds.state = state;
            this.addNode(state, tds.getTDSContent());
        }

        for (TDS tds : depile) {
            if (tds.parent != null)
                addTransition(tds.parent.state, tds.state);
            else
                ;
        }
    }

    @Override
    public TDS visit(Div affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(Mult affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(Sup affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(Egal affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(Diff affect){
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        return null;
    }

    @Override
    public TDS visit(AndExp affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(OrExp affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(ListExp affect){
        for (int i = 0; i<affect.ListExp.size(); i++){
            affect.ListExp.get(i).accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(ListDec affect){
        for (int i = 0; i<affect.ListDec.size(); i++){
            affect.ListDec.get(i).accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(Inf affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(StrictInf affect){
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        return null;
    }

    @Override
    public TDS visit(Let affect){
        TDS fils = new TDS("let_"+this.cmpt_let, pile.isEmpty() ? null : pile.get(pile.size() -1), imbrication_counter+1, "let", types);
        Block b = new Block("let_"+this.cmpt_let, fils);
        pile.get(pile.size()-1).add(b);
        this.cmpt_let++;
        pile.add(fils);
        affect.ListDec.accept(this);
        affect.expr.accept(this);
        depile.add(pile.get(pile.size() -1));
        pile.remove(pile.size() -1);
        return null;
    }

    @Override
    public TDS visit(StrictSup affect){
        affect.expr1.accept(this);
        affect.expr2.accept(this);
        return null;
    }

    @Override
    public TDS visit(Add affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(Sub affect){
        affect.left.accept(this);
        affect.right.accept(this);
        return null;
    }

    @Override
    public TDS visit(IntNode affect){
        //DO NOTHING
        return null;
    }

    @Override
    public TDS visit(StringNode affect){
        //DO NOTHING
        return null;
    }

    @Override
    public TDS visit(Identifier affect){
        //DO NOTHING
        return null;
    }

    @Override
    public TDS visit(Program affect){
        pile.add(new TDS("Program", pile.isEmpty() ? null : pile.get(pile.size() -1), 0, "Program", types));

        for (Ast inst : affect.instrList) {
            inst.accept(this);
        }
        depile.add(pile.get(pile.size() -1));
        pile.remove(pile.size() -1);
        return depile.get(depile.size() -1);
    }

    @Override
    public TDS visit(RecTy recTy){
        recTy.ListFD.accept(this);
        return null;
    }

    @Override
    public TDS visit(ListrecTyFieldDec listrecTyFieldDec){
        for (int i = 0; i<listrecTyFieldDec.ListFD.size(); i++){
            listrecTyFieldDec.ListFD.get(i).accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(FunDec fundec){
        String tyn = "";
        if (fundec.FunDecBody instanceof FunDecBodyWithId){
            tyn = ((Identifier)((FunDecBodyWithId) fundec.FunDecBody).id).name;
        } else {
            tyn = "void";
        }
        TDS fils = new TDS(((Identifier)(fundec.id)).name, pile.isEmpty() ? null : pile.get(pile.size() -1), imbrication_counter+1, tyn, types);
        Fonction f = new Fonction(((Identifier) fundec.id).name, tyn, tyn);
        f.tds = fils;
        pile.get(pile.size() -1).add(f);
        pile.add(fils);
        fundec.ListArgs.accept(this);
        fundec.FunDecBody.accept(this);
        depile.add(pile.get(pile.size() -1));
        pile.remove(pile.size() -1);
        return null;
    }

    @Override
    public TDS visit(ListArgs listArgs){
        for (Ast el : listArgs.ListArgs) {
            el.accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(FunDecBodyWithoutId fundecBodyWithoutId){
        fundecBodyWithoutId.expr.accept(this);
        return null;
    }

    @Override
    public TDS visit(FunDecBodyWithId fundecBodyWithId){
        fundecBodyWithId.expr.accept(this);
        return null;
    }

    @Override
    public TDS visit(LValue lValue){
        for (Ast el : lValue.values) {
            el.accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(SeqExp affect){
        for (Ast el : affect.seqExpList) {
            el.accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(CallExp affect){
        affect.ListExp.accept(this);
        return null;
    }

    @Override
    public TDS visit(CallExpList affect){
        for (Ast el : affect.CallExpList) {
            el.accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(Affect affect){
        affect.exp.accept(this);
        return null;  
    }

    @Override
    public TDS visit(ArrCreate affect){
        affect.expLeft.accept(this);
        affect.expRight.accept(this);
        return null;
    }

    @Override
    public TDS visit(Assign assign){
        assign.lValue.accept(this);
        assign.exp.accept(this);
        return null;
    }

    @Override
    public TDS visit(Create affect){
        affect.createA.accept(this);
        return null;
    }

    @Override
    public TDS visit(FieldDec fieldDec){
        String n = ((Identifier)fieldDec.idLeft).name;
        String t = ((Identifier)fieldDec.idRight).name;
        int taille = getDeplacementType(t);
        Variable v = new Variable(n, "Arg : " + t, taille);

        pile.get(pile.size() -1).add(v);
        return null;
    }

    @Override
    public TDS visit(For affect){
        TDS fils = new TDS("For_"+cmpt_for, pile.isEmpty() ? null : pile.get(pile.size() -1), imbrication_counter+1, "For", types);
        Block b = new Block("For_"+cmpt_for, fils);
        pile.get(pile.size()-1).add(b);
        cmpt_for++;
        pile.add(fils);
        String n = ((Identifier)affect.id).name;
        Variable v = new Variable(n, "int", 4);

        pile.get(pile.size() -1).add(v);
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        affect.exp3.accept(this);
        depile.add(pile.get(pile.size() -1));
        pile.remove(pile.size() -1);
        return null;
    }

    @Override
    public TDS visit(IfThen affect){
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        return null;
    }

    @Override
    public TDS visit(IfThenElse affect){
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        affect.exp3.accept(this);
        return null;
    }

    @Override
    public TDS visit(Neg affect){
        affect.NegExp.accept(this);
        return null;
    }

    @Override
    public TDS visit(RecCreate affect){
        for (Ast el : affect.liste) {
            el.accept(this);
        }
        return null;
    }

    @Override
    public TDS visit(TypeDec typedec){
        String n = ((Identifier)typedec.id).name;
        String tyn = "";
        int nb = 0;
        if (typedec.ty instanceof Identifier){
            tyn = ((Identifier)typedec.ty).name;
            nb = 4;
        } else if (typedec.ty instanceof ArrTy){
            tyn = "Array of " +((Identifier)((ArrTy)typedec.ty).type).name;
            nb = 4;
        } else if (typedec.ty instanceof RecTy){
            if (((ListrecTyFieldDec)((RecTy)typedec.ty).ListFD).ListFD.size() == 0){
                tyn = "";
            } else {
                for (Ast el : ((ListrecTyFieldDec)((RecTy)typedec.ty).ListFD).ListFD){
                    tyn = tyn + ((Identifier)((FieldDec)el).idLeft).name + ":" + ((Identifier)((FieldDec)el).idRight).name +"; ";
                    nb = nb + getDeplacementType(((Identifier)((FieldDec)el).idRight).name);
                }
                tyn = tyn.substring(0, tyn.length()-1);
            }
           
        }
        Type s = new Type(n, 0, tyn);
        types.add(new Tuple(n, nb));
        for (TDS t: pile){
            t.tuples = types;
        }
        for (TDS t: depile){
            t.tuples = types;
        }


        pile.get(pile.size()-1).add(s);
        return null;
    }

    @Override
    public TDS visit(VarDec affect){
        String n = ((Identifier)affect.id).name;
        String tyn = "";
        int taille = 0;
        if (affect.varDecA instanceof VarDecIdExp) {
            tyn = ((Identifier)((VarDecIdExp)affect.varDecA).id).name;
            taille = getDeplacementType(tyn);
        } 
        if (taille == 0){
            taille = 0;
        }
        Variable v = new Variable(n, tyn, taille);
        pile.get(pile.size()-1).add(v);
        return null;
    }

    @Override
    public TDS visit(VarDecIdExp affect){
        affect.exp.accept(this);
        return null;
    }

    @Override
    public TDS visit(While affect){
        affect.exp1.accept(this);
        affect.exp2.accept(this);
        //DO NOTHING
        return null;
    }

    @Override
    public TDS visit(Nil affect){
        //DO NOTHING
        return null;
    }

    @Override
    public TDS visit(ArrTy affect){
        affect.type.accept(this);
        // DO NOTHING
        return null;
    }

    @Override
    public TDS visit(Break affect){
        //DO NOTHING
        return null;
    }
    

}