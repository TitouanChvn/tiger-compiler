package graphViz;

import java.io.FileOutputStream;
import java.io.IOException;

import ast.Affect;
import ast.AndExp;
import ast.Assign;
import ast.Ast;
import ast.AstVisitor;
import ast.CallExp;
import ast.Create;
import ast.FieldDec;
import ast.For;
import ast.FunDec;
import ast.FunDecBodyWithId;
import ast.FunDecBodyWithoutId;
import ast.Identifier;
import ast.IfThen;
import ast.IfThenElse;
import ast.Neg;
import ast.Program;
import ast.RecCreate;
import ast.StringNode;
import ast.TypeDec;
import ast.VarDec;
import ast.VarDecIdExp;
import ast.While;
import ast.ArrCreate;
import ast.IntNode;
import ast.LValue;
import ast.ListArgs;
import ast.ListrecTyFieldDec;
import ast.RecTy;
import ast.SeqExp;
import ast.CallExpList;
import ast.Let;
import ast.ListDec;
import ast.ListExp;
import ast.Div;
import ast.Egal;
import ast.Add;
import ast.Sub;
import ast.Mult;
import ast.Sup;
import ast.Inf;
import ast.StrictSup;
import ast.StrictInf;
import ast.Diff;
import ast.OrExp;
import ast.Nil;
import ast.ArrTy;
import ast.Break;

public class GraphVizVisitor implements AstVisitor<String> {

    private int state;
    private String nodeBuffer;
    private String linkBuffer;

    public GraphVizVisitor(){
        this.state = 0;
        this.nodeBuffer = "digraph \"ast\"{\n\n\tnodesep=1;\n\tranksep=1;\n\n";
        this.linkBuffer = "\n";
    }

    public void dumpGraph(String filepath) throws IOException{
            
        FileOutputStream output = new FileOutputStream(filepath);

        String buffer = this.nodeBuffer + this.linkBuffer;
        byte[] strToBytes = buffer.getBytes();

        output.write(strToBytes);

        output.close();

    }


    private String nextState(){
        int returnedState = this.state;
        this.state++;
        return "N"+ returnedState;
    }

    private void addTransition(String from,String dest){
        this.linkBuffer += String.format("\t%s -> %s; \n", from,dest);

    }

    private void addNode(String node,String label){
        this.nodeBuffer += String.format("\t%s [label=\"%s\", shape=\"box\"];\n", node,label);

    }

    @Override
    public String visit(Div affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "/");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Mult affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "*");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Sup affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, ">=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Egal affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Diff affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.exp1.accept(this);
        String rightState = affect.exp2.accept(this);

        this.addNode(nodeIdentifier, "<>");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }
    
    @Override
    public String visit(AndExp affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "&");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(OrExp affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "|");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(ListExp affect){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "List expressions");
        for (Ast exp : affect.ListExp) {
            String state = exp.accept(this);
            this.addTransition(nodeIdentifier, state);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(ListDec affect){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "List declarations");
        for (Ast dec : affect.ListDec) {
            String state = dec.accept(this);
            this.addTransition(nodeIdentifier, state);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(Inf affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "<=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(StrictInf affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.exp1.accept(this);
        String rightState = affect.exp2.accept(this);

        this.addNode(nodeIdentifier, "<");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Let affect){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "let");
        this.addTransition(nodeIdentifier, affect.ListDec.accept(this));
        this.addTransition(nodeIdentifier, affect.expr.accept(this));
        return nodeIdentifier;
    }

    @Override
    public String visit(StrictSup affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.expr1.accept(this);
        String rightState = affect.expr2.accept(this);

        this.addNode(nodeIdentifier, ">");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Add affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "+");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Sub affect){
        String nodeIdentifier = this.nextState();
        String leftState = affect.left.accept(this);
        String rightState = affect.right.accept(this);

        this.addNode(nodeIdentifier, "-");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);
        return nodeIdentifier;
    }

    @Override
    public String visit(IntNode affect) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, affect.value+"");
        return nodeIdentifier;
    }

    @Override
    public String visit(StringNode affect) {
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "\\\""+affect.value+"\\\"");
        return nodeIdentifier;
    }

    @Override
    public String visit(Identifier affect) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, affect.name);
        return nodeIdentifier;
    }


    @Override
    public String visit(Program program) {
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "Program");
        for (Ast ast : program.instrList) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;
    }

    @Override   
    public String visit(RecTy recTy) {
        String nodeIdentifier = this.nextState();
        String idfState = recTy.ListFD.accept(this);
        
        this.addNode(nodeIdentifier, "Type Record");
        this.addTransition(nodeIdentifier, idfState);
        return nodeIdentifier;
    }

    @Override
    public String visit(ListrecTyFieldDec listFD){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "List fields Type record");
        for (Ast ast : listFD.ListFD) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(FunDec funDec) {
        String nodeIdentifier = this.nextState();
        String idfState = funDec.id.accept(this);
        String listargs = funDec.ListArgs.accept(this);
        String body = funDec.FunDecBody.accept(this);
        
        this.addNode(nodeIdentifier, "Function Declaration");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, listargs);
        this.addTransition(nodeIdentifier, body);
        return nodeIdentifier;
    }

    @Override
    public String visit(ListArgs listArgs) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "List Arguments");
        for (Ast ast : listArgs.ListArgs) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(FunDecBodyWithoutId funDecBodyWithoutId) {
        String nodeIdentifier = this.nextState();

        String expState = funDecBodyWithoutId.expr.accept(this);
        
        this.addNode(nodeIdentifier, "Function Body");
        this.addTransition(nodeIdentifier, expState);
        return nodeIdentifier;
    }

    @Override
    public String visit(FunDecBodyWithId funDecBodyWithId) {
        String nodeIdentifier = this.nextState();

        String idfState = funDecBodyWithId.id.accept(this);
        String expState = funDecBodyWithId.expr.accept(this);
        
        this.addNode(nodeIdentifier, "Function Body");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expState);
        return nodeIdentifier;
    }

    @Override
    public String visit(LValue affect){
        String nodeIdentifier = this.nextState();
        String idfState = affect.id.accept(this);
        this.addNode(nodeIdentifier, "LValue");
        this.addTransition(nodeIdentifier, idfState);
        for (Ast ast : affect.values) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }


        return nodeIdentifier;
    }

    @Override
    public String visit(SeqExp affect){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Sequence Expressions");
        for (Ast ast : affect.seqExpList) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(CallExp affect) {
        String nodeIdentifier = this.nextState();
        String idfState = affect.id.accept(this);
        String listExpState = affect.ListExp.accept(this);
        this.addNode(nodeIdentifier, "Function Call");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, listExpState);

        return nodeIdentifier;
    }

    @Override
    public String visit(CallExpList affect) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "List Parameters");
        for (Ast ast : affect.CallExpList) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(Affect affect) {

        String nodeIdentifier = this.nextState();
        String idfState = affect.id.accept(this);
        String expressionState = affect.exp.accept(this);

        this.addNode(nodeIdentifier, "Affectation");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expressionState);

        return nodeIdentifier;

    }

    @Override
    public String visit(ArrCreate arrCreate) {
        String nodeIdentifier = this.nextState();

        String expLeftState = arrCreate.expLeft.accept(this);
        String expRightState = arrCreate.expRight.accept(this);

        this.addNode(nodeIdentifier, "Creation Array");
        this.addTransition(nodeIdentifier, expLeftState);
        this.addTransition(nodeIdentifier, expRightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Assign assign) {
        String nodeIdentifier = this.nextState();

        String lvalState = assign.lValue.accept(this);
        String expressionState = assign.exp.accept(this);

        this.addNode(nodeIdentifier, "Assignment");
        this.addTransition(nodeIdentifier, lvalState);
        this.addTransition(nodeIdentifier, expressionState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Create create) {
        String nodeIdentifier = this.nextState();

        String idfState = create.id.accept(this);
        String expListState = create.createA.accept(this);

        this.addNode(nodeIdentifier, "Create");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expListState);

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldDec fieldDec) {
        String nodeIdentifier = this.nextState();

        String idLState = fieldDec.idLeft.accept(this);
        String idRState = fieldDec.idRight.accept(this);

        this.addNode(nodeIdentifier, "Argument Declaration");
        this.addTransition(nodeIdentifier, idLState);
        this.addTransition(nodeIdentifier, idRState);

        return nodeIdentifier;
    }

    @Override
    public String visit(For forA) {
        String nodeIdentifier = this.nextState();

        String idfState = forA.id.accept(this);
        String expLeftState = forA.exp1.accept(this);
        String expRightState = forA.exp2.accept(this);
        String exp3State = forA.exp3.accept(this);

        this.addNode(nodeIdentifier, "For");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expLeftState);
        this.addTransition(nodeIdentifier, expRightState);
        this.addTransition(nodeIdentifier, exp3State);

        return nodeIdentifier;
    }

    @Override
    public String visit(IfThen ifthen) {
        String nodeIdentifier = this.nextState();

        String exp1State = ifthen.exp1.accept(this);
        String exp2State = ifthen.exp2.accept(this);

        this.addNode(nodeIdentifier, "IfThen");
        this.addTransition(nodeIdentifier, exp1State);
        this.addTransition(nodeIdentifier, exp2State);

        return nodeIdentifier;
    }

    @Override
    public String visit(IfThenElse ifthenelse) {
        String nodeIdentifier = this.nextState();

        String exp1State = ifthenelse.exp1.accept(this);
        String exp2State = ifthenelse.exp2.accept(this);
        String exp3State = ifthenelse.exp3.accept(this);

        this.addNode(nodeIdentifier, "IfThenElse");
        this.addTransition(nodeIdentifier, exp1State);
        this.addTransition(nodeIdentifier, exp2State);
        this.addTransition(nodeIdentifier, exp3State);

        return nodeIdentifier;
    }



    @Override
    public String visit(Neg neg) {
        String nodeIdentifier = this.nextState();

        String expState = neg.NegExp.accept(this);

        this.addNode(nodeIdentifier, "Negation");
        this.addTransition(nodeIdentifier, expState);

        return nodeIdentifier;
    }

    @Override
    public String visit(RecCreate recCreate) {
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "Creation Record");
        for (Ast ast : recCreate.liste) {
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(TypeDec typeDec) {
        String nodeIdentifier = this.nextState();

        String idfState = typeDec.id.accept(this);
        String typeState = typeDec.ty.accept(this);

        this.addNode(nodeIdentifier, "Type declaration");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, typeState);

        return nodeIdentifier;
    }

    @Override
    public String visit(VarDec varDec) {
        String nodeIdentifier = this.nextState();

        String idfState = varDec.id.accept(this);
        String expState = varDec.varDecA.accept(this);

        this.addNode(nodeIdentifier, "Variable declaration");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expState);

        return nodeIdentifier;
    }

    @Override
    public String visit(VarDecIdExp varDecType) {
        String nodeIdentifier = this.nextState();

        String idFState = varDecType.id.accept(this);
        String expState = varDecType.exp.accept(this);

        this.addNode(nodeIdentifier, "Variable declaration type");
        this.addTransition(nodeIdentifier, idFState);
        this.addTransition(nodeIdentifier, expState);

        return nodeIdentifier;
    }

    @Override
    public String visit(While whileExp) {
        String nodeIdentifier = this.nextState();

        String expState = whileExp.exp1.accept(this);
        String exp2State = whileExp.exp2.accept(this);

        this.addNode(nodeIdentifier, "While");
        this.addTransition(nodeIdentifier, expState);
        this.addTransition(nodeIdentifier, exp2State);

        return nodeIdentifier;
    }

    @Override
    public String visit(Nil affect){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Nil");
        return nodeIdentifier;
    }

    @Override
    public String visit(ArrTy affect){
        String nodeIdentifier = this.nextState();
        String typeState = affect.type.accept(this);
        this.addNode(nodeIdentifier, "Array of ");
        this.addTransition(nodeIdentifier, typeState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Break affect){
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Break");
        return nodeIdentifier;
    }
}