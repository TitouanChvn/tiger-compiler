package ast;

public interface AstVisitor<T> {
    // Manque les noeuds de atom et de expr
    public T visit(IntNode affect);
    public T visit(StringNode affect);
    public T visit(Program affect);
    public T visit(TypeDec affect);
    public T visit(Identifier affect);
    public T visit(FieldDec affect);
    public T visit(VarDec affect);
    public T visit(VarDecIdExp affect);
    public T visit(Create affect);
    public T visit(ArrCreate affect);
    public T visit(RecCreate affect);
    public T visit(Affect affect);
    public T visit(Assign affect);
    public T visit(IfThenElse affect);
    public T visit(IfThen affect);
    public T visit(While affect);
    public T visit(For affect);
    public T visit(Neg affect);
    public T visit(RecTy affect);
    public T visit(ListrecTyFieldDec affect);
    public T visit(FunDecBodyWithId affect);
    public T visit(FunDecBodyWithoutId affect);
    public T visit(FunDec affect);
    public T visit(ListArgs affect);
    public T visit(LValue affect);
    public T visit(SeqExp affect);
    public T visit(CallExp affect);
    public T visit(CallExpList affect);
    public T visit(Let affect);
    public T visit(ListDec affect);
    public T visit(ListExp affect);
    public T visit(OrExp affect);
    public T visit(AndExp affect);
    public T visit(Egal affect);
    public T visit(Diff affect);
    public T visit(StrictInf affect);
    public T visit(StrictSup affect);
    public T visit(Inf affect);
    public T visit(Sup affect);
    public T visit(Add affect);
    public T visit(Sub affect);
    public T visit(Mult affect);
    public T visit(Div affect);
    public T visit(Nil affect);
    public T visit(ArrTy affect);
    public T visit(Break affect);
   

}
