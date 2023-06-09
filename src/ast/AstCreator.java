package ast;


import parser.tigerBaseVisitor;
import parser.tigerParser;

public class AstCreator extends tigerBaseVisitor<Ast> {

    @Override
    public Ast visitInteger(tigerParser.IntegerContext ctx) {
        int value = Integer.parseInt(ctx.getText());
        return new IntNode(value,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitString(tigerParser.StringContext ctx) {
        String value = ctx.getText();
        String tmp = value.substring(1, value.length()-1);
        return new StringNode(tmp, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitIdAtom(tigerParser.IdAtomContext ctx) {
        String id = ctx.getText();
        return new Identifier(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitSeqExpAtom(tigerParser.SeqExpAtomContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitCallExpAtom(tigerParser.CallExpAtomContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitLValueAtom(tigerParser.LValueAtomContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitProgram(tigerParser.ProgramContext ctx) {
        int n = ctx.getChildCount();
        Program program = new Program(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);

        for (int i = 0; i < n; i++) {
            program.add(ctx.getChild(i).accept(this));
        }
        return program;
    }

    @Override
    public Ast visitDec(tigerParser.DecContext ctx) {
        Ast dec = ctx.getChild(0).accept(this);
        return dec;
    }

    @Override
    public Ast visitTypeDec(tigerParser.TypeDecContext ctx) { 
        String idstr = ctx.getChild(1).getText();     
        Ast id = new Identifier(idstr, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast ty = ctx.getChild(3).accept(this);
        return new TypeDec(id, ty, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitIdTy(tigerParser.IdTyContext ctx) {
        String id = ctx.getText();
        return new Identifier(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitArrTyTy(tigerParser.ArrTyTyContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitRecTyTy(tigerParser.RecTyTyContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitArrTy(tigerParser.ArrTyContext ctx) {
        String id = ctx.getChild(1).getText();
        return new ArrTy(new Identifier(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitRecTy(tigerParser.RecTyContext ctx) {
        return new RecTy(ctx.getChild(1).accept(this), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitListrecTyFieldDec(tigerParser.ListrecTyFieldDecContext ctx) {
        int n = ctx.getChildCount();
        ListrecTyFieldDec listrecTyFieldDec = new ListrecTyFieldDec(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 0; 2*i<n; i++) {
            listrecTyFieldDec.add(ctx.getChild(2*i).accept(this));
        }
        return listrecTyFieldDec;
    }

    @Override
    public Ast visitFieldDec(tigerParser.FieldDecContext ctx){
        String id1str = ctx.getChild(0).getText();
        String id2str = ctx.getChild(2).getText();
        return new FieldDec(new Identifier(id1str, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1), new Identifier(id2str, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1),ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitFunDec(tigerParser.FunDecContext ctx) {
        String idstr = ctx.getChild(1).getText();
        Ast id = new Identifier(idstr, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast params = ctx.getChild(3).accept(this);
        Ast body = ctx.getChild(5).accept(this);
        return new FunDec(id, params, body, ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()+1 );
    }

    @Override
    public Ast visitListArgs(tigerParser.ListArgsContext ctx) {
        int n = ctx.getChildCount();
        ListArgs listArgs = new ListArgs(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 0; 2*i<n; i++) {
            listArgs.add(ctx.getChild(2*i).accept(this));
        }
        return listArgs;
    }


    @Override
    public Ast visitFunDecBodyWithoutId(tigerParser.FunDecBodyWithoutIdContext ctx) {
        Ast body = ctx.getChild(1).accept(this);
        return new FunDecBodyWithoutId(body, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }


    @Override
    public Ast visitFunDecBodyWithId(tigerParser.FunDecBodyWithIdContext ctx) {
        String idstr = ctx.getChild(1).getText();
        Ast id = new Identifier(idstr, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast body = ctx.getChild(3).accept(this);

        return new FunDecBodyWithId(id, body, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }



    @Override
    public Ast visitVarDec(tigerParser.VarDecContext ctx) {
        String id = ctx.getChild(1).getText();
        Ast varDeca = ctx.getChild(2).accept(this);
        return new VarDec(new Identifier(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1), varDeca, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);

    }

    @Override
    public Ast visitVarDecExp(tigerParser.VarDecExpContext ctx) {
        return ctx.getChild(1).accept(this);
    }

    @Override
    public Ast visitVarDecIdExp(tigerParser.VarDecIdExpContext ctx) {
        Ast id = new Identifier(ctx.getChild(1).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast exp = ctx.getChild(3).accept(this);
        return new VarDecIdExp(id, exp,ctx.getStart().getLine(),ctx.getStart().getCharPositionInLine()+1);
    } 

    @Override
    public Ast visitLValue(tigerParser.LValueContext ctx) {
        int n = ctx.getChildCount();
        Ast id = new Identifier(ctx.getChild(0).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        LValue lValuelist = new LValue(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i=1; i <n; i++){
            if (ctx.getChild(i).getText().equals("[")) {
                lValuelist.add(ctx.getChild(i+1).accept(this));
            } else if (ctx.getChild(i).getText().equals(".")) {
                Identifier a = new Identifier("."+ctx.getChild(i+1).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                lValuelist.add(a);
            } else {
                continue;
            }

        }
        return lValuelist;
    }

    @Override
    public Ast visitNil(tigerParser.NilContext ctx) {
        return new Nil(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitExpinfixExp(tigerParser.ExpinfixExpContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpcreate(tigerParser.ExpcreateContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpassignment(tigerParser.ExpassignmentContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpIfThenElse(tigerParser.ExpIfThenElseContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpIfThen(tigerParser.ExpIfThenContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpwhileExp(tigerParser.ExpwhileExpContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitExpforExp(tigerParser.ExpforExpContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitBreak(tigerParser.BreakContext ctx) {
        return new Break(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitExpletExp(tigerParser.ExpletExpContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitSeqExp(tigerParser.SeqExpContext ctx) {
        int n = ctx.getChildCount();
        SeqExp seqExp = new SeqExp(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        if (n == 2) {
            return seqExp;
        } else {
            for (int i = 1; i<n-1; i++) {
                if (ctx.getChild(i).getText().equals(";")) {
                    continue;
                }
                seqExp.add(ctx.getChild(i).accept(this));
            }
            return seqExp;
        }
    }

    @Override
    public Ast visitCallExp(tigerParser.CallExpContext ctx) {
        Ast id = new Identifier(ctx.getChild(0).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast listArgs = ctx.getChild(2).accept(this);
        return new CallExp(id, listArgs, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitCallExpList(tigerParser.CallExpListContext ctx) {
        int n = ctx.getChildCount();
        CallExpList listArgs = new CallExpList(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 0; 2*i<n; i++) {
            listArgs.add(ctx.getChild(2*i).accept(this));
        }
        return listArgs;
    }

    @Override
    public Ast visitCreate(tigerParser.CreateContext ctx){
        Ast id = new Identifier(ctx.getChild(0).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast createa = ctx.getChild(1).accept(this);
        return new Create(id, createa, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }


    @Override
    public Ast visitCreateA(tigerParser.CreateAContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public Ast visitArrCreate(tigerParser.ArrCreateContext ctx) {
        Ast expr1 = ctx.getChild(1).accept(this);
        Ast expr2 = ctx.getChild(4).accept(this);
        return new ArrCreate(expr1, expr2, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);   
    }

    @Override
    public Ast visitRecCreate(tigerParser.RecCreateContext ctx) {
        RecCreate rc = new RecCreate(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 1; i < ctx.getChildCount()-1; i++) {
            if (ctx.getChild(i).getText().equals(",")) {
                continue;
            }
            rc.add(ctx.getChild(i).accept(this));
        }
        return rc;
    }

    @Override
    public Ast visitAffect(tigerParser.AffectContext ctx) {
        Ast id = new Identifier(ctx.getChild(0).getText(), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        Ast exp = ctx.getChild(2).accept(this);
        return new Affect(id, exp, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitAssign(tigerParser.AssignContext ctx) {
        Ast lvalue = ctx.getChild(0).accept(this);
        Ast exp = ctx.getChild(2).accept(this);
        return new Assign(lvalue, exp, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitIfThenElse(tigerParser.IfThenElseContext ctx) {
        Ast exp1 = ctx.getChild(1).accept(this);
        Ast exp2 = ctx.getChild(3).accept(this);
        Ast exp3 = ctx.getChild(5).accept(this);
        return new IfThenElse(exp1, exp2, exp3, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitIfThen(tigerParser.IfThenContext ctx) {
        Ast exp1 = ctx.getChild(1).accept(this);
        Ast exp2 = ctx.getChild(3).accept(this);
        return new IfThen(exp1, exp2, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitWhileExp(tigerParser.WhileExpContext ctx) {
        Ast exp1 = ctx.getChild(1).accept(this);
        Ast exp2 = ctx.getChild(3).accept(this);
        return new While(exp1, exp2, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitForExp(tigerParser.ForExpContext ctx) {
        String id = ctx.getChild(1).toString();
        Ast exp1 = ctx.getChild(3).accept(this);
        Ast exp2 = ctx.getChild(5).accept(this);
        Ast exp3 = ctx.getChild(7).accept(this);
        Ast idf = new Identifier(id, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);

        return new For(idf, exp1, exp2, exp3, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitLetExp(tigerParser.LetExpContext ctx) {
        Ast decs = ctx.getChild(1).accept(this);
        Ast exp = ctx.getChild(3).accept(this);
        return new Let(decs, exp, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    @Override
    public Ast visitListDec(tigerParser.ListDecContext ctx) {
        int n = ctx.getChildCount();
        ListDec listDec = new ListDec(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 0; i<n; i++) {
            listDec.add(ctx.getChild(i).accept(this));
        }
        return listDec;
    }

    @Override
    public Ast visitListExp(tigerParser.ListExpContext ctx) {
        int n = ctx.getChildCount();
        ListExp listExp = new ListExp(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        for (int i = 0; 2*i<n; i++) {
            listExp.add(ctx.getChild(2*i).accept(this));
        }
        return listExp;
    }

    @Override
    public Ast visitOrExp(tigerParser.OrExpContext ctx) {
        Ast left = ctx.getChild(0).accept(this);

        for (int i = 0; 2*i<ctx.getChildCount()-1; i++){
            Ast right = ctx.getChild(2*i+2).accept(this);
            left = new OrExp(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        }
        return left;
    }

    @Override
    public Ast visitAndExp(tigerParser.AndExpContext ctx) {
        Ast left = ctx.getChild(0).accept(this);
        for (int i = 0; 2*i<ctx.getChildCount()-1; i++){
            Ast right = ctx.getChild(2*i+2).accept(this);
            left = new AndExp(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
        }
        return left;
    }

    @Override
    public Ast visitCmpExp(tigerParser.CmpExpContext ctx) {
        Ast left = ctx.getChild(0).accept(this);
        for (int i = 0; 2*i<ctx.getChildCount()-1; i++){
            Ast right = ctx.getChild(2*i+2).accept(this);
            String op = ctx.getChild(2*i+1).getText();
            switch (op) {
                case "<":
                    left = new StrictInf(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case ">":
                    left = new StrictSup(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case "<=":
                    left = new Inf(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case ">=":
                    left = new Sup(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case "=":
                    left = new Egal(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case "<>":
                    left = new Diff(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                default:
                    break;
            }
        }
        return left;
    }

    @Override
    public Ast visitAsExp(tigerParser.AsExpContext ctx) {
        Ast left = ctx.getChild(0).accept(this);
        for (int i = 0; 2*i<ctx.getChildCount()-1; i++){
            Ast right = ctx.getChild(2*i+2).accept(this);
            String op = ctx.getChild(2*i+1).getText();
            switch (op) {
                case "+":
                    left = new Add(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case "-":
                    left = new Sub(left, right, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                default:
                    break;
            }
        }
        return left;
    }

    @Override
    public Ast visitMdExp(tigerParser.MdExpContext ctx) {
        Ast left = ctx.getChild(0).accept(this);
        for (int i = 0; 2*i<ctx.getChildCount()-1; i++){
            Ast right = ctx.getChild(2*i+2).accept(this);
            String op = ctx.getChild(2*i+1).getText();
            switch (op) {
                case "*":
                    left = new Mult(left, right,ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                case "/":
                    left = new Div(left, right,ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
                    break;
                default:
                    break;
            }
        }
        return left;
    }

    public Ast visitNegation(tigerParser.NegationContext ctx) {
        return new Neg(ctx.getChild(1).accept(this), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()+1);
    }

    public Ast visitAtomN(tigerParser.AtomNContext ctx) {
        return ctx.getChild(0).accept(this);
    }
   


}


