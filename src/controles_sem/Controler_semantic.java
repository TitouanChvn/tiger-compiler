package controles_sem;

import java.util.ArrayList;
import java.util.Comparator;

import ast.*;
import tds.*;

public class Controler_semantic implements AstVisitor<Void>{
    public ArrayList<Error> errors;
    public ArrayList<Warning> warnings;
    public TDS currentTDS;
    public int cmptLet = 0;
    public int cmptFor = 0;
    public int cmptWhileFor = 0;
    public boolean b_type_dec = false;

    public ArrayList<NoUseVar> noUseVar = new ArrayList<>();



    public void promp(){
        errors.sort(new Comparator<Error>() {
             @Override
             public int compare(Error o1, Error o2) {
             Integer x = o1.line;
             Integer y = o2.line;
             return x.compareTo(y);
         }
     });
         if (! errors.isEmpty()){
             for(Error e : errors){
                 System.out.println(e.toString());
             }  
             for (Warning w : warnings){
                 System.out.println(w.toString());
             }
             System.exit(1);
         }
         for (Warning w : warnings){
             System.out.println(w.toString());
         }
 
     }

    public Controler_semantic(TDS currentTDS){
        this.errors = new ArrayList<Error>();
        this.warnings = new ArrayList<Warning>();
        this.currentTDS = currentTDS; 
    }


    ///**** VISITEURS ****///


    @Override
    public Void visit(Div div){
        div.left.accept(this);
        div.right.accept(this);
        if (div.right instanceof IntNode){
            IntNode right = (IntNode) div.right;
            if (right.value == 0){
                errors.add(new DivByZero(right.getLine(), right.getCharPos()));  
            }
        }
        else{
            //ça peut quand même être une division par 0
            //avec une variable ou une autre opération 
        }
        if (!convTypeToAtom(div.left.getType()).equals("int")){
            errors.add(new WrongType(div.left.getLine(), div.left.getCharPos(), "/"));
        }
        if (!convTypeToAtom(div.right.getType()).equals("int")){
            errors.add(new WrongType(div.right.getLine(), div.right.getCharPos(), "/"));
        }
        div.type = "int";
        return null;
    }

    @Override
    public Void visit(Mult mult){
        mult.left.accept(this);
        mult.right.accept(this);
        if (!convTypeToAtom(mult.left.getType()).equals("int")){
            errors.add(new WrongType(mult.left.getLine(), mult.left.getCharPos(), "*"));
        }
        if (!convTypeToAtom(mult.right.getType()).equals("int")){
            errors.add(new WrongType(mult.right.getLine(), mult.right.getCharPos(), "*"));
        }
        mult.type = "int";
        return null;
    }

    @Override
    public Void visit(Sup sup){
        sup.left.accept(this);
        sup.right.accept(this);
        if (!convTypeToAtom(sup.left.getType()).equals(convTypeToAtom(sup.right.getType()))){
            errors.add(new WrongType(sup.left.getLine(), sup.left.getCharPos(), ">="));
        }
        sup.type = "int";
        return null;
    }

    @Override
    public Void visit(Egal egal){
        egal.left.accept(this);
        egal.right.accept(this);
        if (!convTypeToAtom(egal.left.getType()).equals(convTypeToAtom(egal.right.getType()))){
            errors.add(new WrongType(egal.left.getLine(), egal.left.getCharPos(), "="));
        }
        egal.type = "int";
        return null;
    }

    @Override
    public Void visit(Diff diff){
        diff.exp1.accept(this);
        diff.exp2.accept(this);
        if (!convTypeToAtom(diff.exp1.getType()).equals(convTypeToAtom(diff.exp2.getType()))){
            errors.add(new WrongType(diff.exp1.getLine(), diff.exp1.getCharPos(), "<>"));
        }
        diff.type = "int";
        return null;
    }

    @Override
    public Void visit(AndExp and){
        and.left.accept(this);
        and.right.accept(this);
        if (!convTypeToAtom(and.left.getType()).equals("int")){
            errors.add(new WrongType(and.left.getLine(), and.left.getCharPos(), "&"));
        }
        if (!convTypeToAtom(and.right.getType()).equals("int")){
            errors.add(new WrongType(and.right.getLine(), and.right.getCharPos(), "&"));
        }
        and.type = "int";
        return null;
    }

    @Override
    public Void visit(OrExp or){
        or.left.accept(this);
        or.right.accept(this);
        if (!convTypeToAtom(or.left.getType()).equals("int")){
            errors.add(new WrongType(or.left.getLine(), or.left.getCharPos(), "|"));
        }
        if (!convTypeToAtom(or.right.getType()).equals("int")){
            errors.add(new WrongType(or.right.getLine(), or.right.getCharPos(), "|"));
        }
        or.type = "int";
        return null;
    }

    @Override
    public Void visit(ListExp affect){
        for (Ast el : affect.ListExp){
            el.accept(this);
        }
        if (affect.ListExp.size() != 0){
            affect.type = convTypeToAtom(affect.ListExp.get(affect.ListExp.size()-1).getType());
        } else {
            affect.type = "void";
        }
        return null;
    }

    @Override
    public Void visit(ListDec ld){
        for (Ast el : ld.ListDec){
            el.accept(this);
        }
        ld.type = "void";
        return null;
    }

    @Override
    public Void visit(Inf inf){
        inf.left.accept(this);
        inf.right.accept(this);
        if (!convTypeToAtom(inf.left.getType()).equals(convTypeToAtom(inf.right.getType()))){
            errors.add(new WrongType(inf.left.getLine(), inf.left.getCharPos(), "<="));
        }
        inf.type = "int";
        return null;
    }

    @Override
    public Void visit(StrictInf si){
        si.exp1.accept(this);
        si.exp2.accept(this);
        if (!convTypeToAtom(si.exp1.getType()).equals(convTypeToAtom(si.exp2.getType()))){
            errors.add(new WrongType(si.exp1.getLine(), si.exp1.getCharPos(), "<"));
        }
        si.type = "int";
        return null;
    }

    @Override
    public Void visit(Let l){
        TDS oldTDS = currentTDS;
        currentTDS = currentTDS.getChild("let_"+cmptLet);
        cmptLet++;
        l.ListDec.accept(this);
        l.expr.accept(this);
        currentTDS = oldTDS;
        l.type = convTypeToAtom(l.expr.getType());
        return null;
    }

    public Void visit(StrictSup ss){
        ss.expr1.accept(this);
        ss.expr2.accept(this);
        if (!convTypeToAtom(ss.expr1.getType()).equals(convTypeToAtom(ss.expr2.getType()))){
            errors.add(new WrongType(ss.expr1.getLine(), ss.expr1.getCharPos(), ">"));
        }
        ss.type = "int";
        return null;
    }

    @Override
    public Void visit(Add add){
        add.left.accept(this);
        add.right.accept(this);
        if (!convTypeToAtom(add.left.getType()).equals("int")){
            errors.add(new WrongType(add.left.getLine(), add.left.getCharPos(), "+"));
        }
        if (!convTypeToAtom(add.right.getType()).equals("int")){
            errors.add(new WrongType(add.right.getLine(), add.right.getCharPos(), "+"));
        }
        add.type = "int";
        return null;
    }

    @Override
    public Void visit(Sub sub){
        sub.left.accept(this);
        sub.right.accept(this);
        if (!convTypeToAtom(sub.left.getType()).equals("int")){
            errors.add(new WrongType(sub.left.getLine(), sub.left.getCharPos(), "-"));
        }
        if (!convTypeToAtom(sub.right.getType()).equals("int")){
            errors.add(new WrongType(sub.right.getLine(), sub.right.getCharPos(), "-"));
        }
        sub.type = "int";
        return null;
    }

    @Override
    public Void visit(IntNode in){
        in.type = "int";
        return null;
    }

    @Override
    public Void visit(StringNode sn){
        sn.type = "string";
        return null;
    }

    @Override
    public Void visit(Identifier id){
        TDS c = this.currentTDS;
        boolean trouve = false;
        while (c != null && !trouve){
            for (Symbole el : c.table){
                if (el.nom.equals(id.name) && el instanceof Variable){
                    id.type = ((Variable) el).type;
                }
                if (el.nom.equals(id.name) && el instanceof Type && b_type_dec){
                    id.type = ((Type) el).type.split("Type :")[1].substring(1);
                }
            }
            c = c.parent;
        }
        if (id.type == null) {
            id.type = "void";
        }

        if (id.type.contains("Arg")){
            id.type = convTypeToAtom(id.type.split(":")[1].substring(1));
        }
        return null;
    }

    @Override
    public Void visit(Program prog){
        for (Ast el : prog.instrList){
            el.accept(this);
        }
        prog.type = "void";
        return null;
    }

    @Override
    public Void visit(RecTy rec){
        rec.ListFD.accept(this);
        rec.type = "void";
        return null;
    }

    @Override
    public Void visit(ListrecTyFieldDec lfd){
        for (Ast el : lfd.ListFD){
            el.accept(this);
        }
        lfd.type = "void";
        return null;
    }

    @Override
    public Void visit(FunDec fd){
        fd.id.accept(this);
        TDS oldTDS = currentTDS;
        currentTDS = currentTDS.getChild(((Identifier)fd.id).name);
        fd.ListArgs.accept(this);
        fd.FunDecBody.accept(this);
        currentTDS = oldTDS;
        if (!IsRecord(fd.FunDecBody.getType())){
            fd.type = convTypeToAtom(fd.FunDecBody.getType());
        }
        TDS myTDS = currentTDS;
        int cpt = 0;
        Identifier id = (Identifier) fd.id;
        while (myTDS != null) {
            for (Symbole s : myTDS.table) {
                if (s.nom.equals(id.name) && s instanceof Fonction) {
                    cpt++;
                }
            }
            myTDS = myTDS.parent;
        }
        if (cpt > 1) {
            FunDoubleDecla error = new FunDoubleDecla(id.name, id.line, id.charPos);
            this.errors.add(error);
            
        }
        return null;
    }

    @Override
    public Void visit(ListArgs la){
        for (Ast el : la.ListArgs){
            el.accept(this);
        }
        la.type = "void";
        return null;
    }

    @Override
    public Void visit(FunDecBodyWithoutId fdbwi){
        fdbwi.expr.accept(this);
        if (!fdbwi.expr.getType().equals("void")){
            errors.add(new WrongReturnType(fdbwi.getLine(), fdbwi.getCharPos(), fdbwi.expr.getType(), "void"));
        }
        fdbwi.type = "void";
        return null;
    }

    @Override
    public Void visit(FunDecBodyWithId fdbwi){
        fdbwi.expr.accept(this);
        fdbwi.id.accept(this);
        fdbwi.type = ((Identifier)fdbwi.id).name;
        if (!IsRecord(fdbwi.type)){
            fdbwi.type = convTypeToAtom(fdbwi.type);
        }
        String bodytype = fdbwi.expr.getType();
        if (IsRecord(bodytype)){
            bodytype = convTypeToAtom(bodytype);
        }
        if (!fdbwi.type.equals(fdbwi.expr.getType())){
            errors.add(new WrongReturnType(fdbwi.getLine(), fdbwi.getCharPos(), fdbwi.expr.getType(), fdbwi.type));
        }
        return null;
    }

    @Override
    public Void visit(LValue lv){
        lv.id.accept(this);
        for (Ast el : lv.values){
            el.accept(this);
        }
        String type = ((Identifier)lv.id).type;
        type = convTypeToAtom(type);
        for (Ast el : lv.values){

            if (el instanceof Identifier){

                if (((Identifier) el).name.contains(".")){

                    String nom_champ = ((Identifier) el).name.substring(1);
                    if (!type.contains(";")){
                        errors.add(new NotARecord(lv.getLine(), lv.getCharPos()));
                    } else {
                        String[] champs = type.split(";");

                        int taille = champs.length;
                        int cpt = 0;
                        boolean debut = true;
                        for (String champ : champs){
                            if (debut){
                                debut = false;
                                if (!champ.split(":")[0].equals(nom_champ)){
                                    cpt++;
                                    continue;
                                } else {
                                    type = convTypeToAtom(champ.split(":")[1]);
                                }
                            } else {
                                champ = champ.substring(1);
                                if (!champ.split(":")[0].equals(nom_champ)){
                                    cpt++;
                                    continue;
                                } else {
                                    type = convTypeToAtom(champ.split(":")[1]);
                                }
                            }
                        }
                        if (cpt == taille){
                            errors.add(new FieldNotExist(lv.getLine(), lv.getCharPos(), nom_champ));
                        }

                    }

                    
                    
                } else {
                    if (!convTypeToAtom(el.getType()).equals("int")){
                        errors.add(new NotAnIndice(lv.getLine(), lv.getCharPos()));
                    }
                    if (type.contains("Array")){
                        type = convTypeToAtom(type.split("of")[1].substring(1));

                    } else {
                        errors.add(new NonArray(lv.getLine(), lv.getCharPos()));
                    }
                }
            } else {
                if (!convTypeToAtom(el.getType()).equals("int")){
                    errors.add(new NotAnIndice(lv.getLine(), lv.getCharPos()));
                }
                if (type.contains("Array")){
                    type = convTypeToAtom(type.split("of")[1].substring(1));
                } else {
                    errors.add(new NonArray(lv.getLine(), lv.getCharPos()));
                }
            }
            
        }

        lv.type = type;
        return null;
    }

    @Override
    public Void visit(SeqExp seq){
        for (Ast el : seq.seqExpList){
            el.accept(this);
        }
        if (seq.seqExpList != null) {
            seq.type = convTypeToAtom(seq.seqExpList.get(seq.seqExpList.size() - 1).getType());
        } else {
            seq.type = "void";
        }
        return null;
    }

    @Override
    public Void visit(CallExp callExp){
        callExp.id.accept(this);
        callExp.ListExp.accept(this);
        TDS myTDS=this.currentTDS;
        boolean trouve=false;

        int fo=0;
        //int curr = 0;
        ArrayList<Ast> param = new ArrayList<Ast>();
        ArrayList<String> donnes = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();
        TDS tds = null;

        if (callExp.id instanceof Identifier){
            Identifier ident = (Identifier) callExp.id;
            while (myTDS!=null && !trouve){
                for (Symbole el : myTDS.table){
                    if (el instanceof Fonction && el.nom.equals(ident.name)){
                        trouve = true;
                        tds = ((Fonction) el).tds;
                    }
                }
                if (!trouve){
                    myTDS=myTDS.parent;
                }
            }
            if (!trouve){
                if (ident.name.equals("print")){
                    callExp.type = "void";
                } else {
                    errors.add(new UnknownFunction(ident.line, ident.charPos, ident.name));
                    callExp.type = "";
                }
            } else {

                CallExpList callexplist = (CallExpList) callExp.ListExp;
                param = callexplist.CallExpList;


                for (Symbole el : myTDS.table){
                    if (el instanceof Fonction && el.nom.equals(ident.name)){
                        callExp.type = convTypeToAtom(((Fonction) el).typeRetour);
                        

                        for (Symbole el2 : tds.table){
                            if (el2 instanceof Variable){
                                types.add(((Variable) el2).type);
                            }
                        }

                        for (Ast el2 : param){
                            el2.accept(this);
                            donnes.add(el2.getType());
                        }
                    }
                }
                if (donnes.size() != types.size()) {
                    fo++;
                }
                for (int i=0;i<donnes.size();i++){
                    String varType = types.get(i);
                    varType = varType.substring(6);

                    if (!donnes.get(i).equals(varType)) {
                        fo++;
                    }
                }
                if (fo>0) {
                errors.add(new ArgCall(callExp.line,callExp.charPos, ident.name, types));
                }
            }
        }
        return null;
    }

    @Override
    public Void visit(CallExpList cel){
        for (Ast el : cel.CallExpList){
            el.accept(this);
        }
        cel.type = "void";
        return null;
    }


    @Override
    public Void visit(Affect aff){
        aff.id.accept(this);
        aff.exp.accept(this);
        return null;
    }

    @Override
    public Void visit(ArrCreate ar){
        ar.expLeft.accept(this);
        ar.expRight.accept(this);
        ar.type = "void";
        return null;
    }

    @Override
    public Void visit(Assign a){
        a.lValue.accept(this);
        if (a.lValue.getType().equals("")){
            ((LValue)a.lValue).type = "void";
        }
        a.exp.accept(this);
        if (a.exp.getType() == null){
            errors.add(new WrongType(a.exp.getLine(), a.exp.getCharPos(), ":="));
        } else {
            if(!convTypeToAtom(a.lValue.getType()).equals(convTypeToAtom(a.exp.getType()))){
                errors.add(new WrongTypeAssignment(a.getLine(), a.getCharPos(), a.exp.getType(), a.lValue.getType()));
            }
        }


        a.type = "void";
        return null;
    }

    @Override
    public Void visit(Create create){
        create.id.accept(this);
        create.createA.accept(this);
        create.type = ((Identifier) create.id).name;
        if (create.createA instanceof RecCreate){
            RecCreate rc = (RecCreate) create.createA;
            int n = rc.liste.size();
            int t = convTypeToAtom(create.type).split(";").length;
            if (n != t){
                errors.add(new WrongNumberOfFields(create.getLine(), create.getCharPos(), t, n));
            }
        }
        return null;
    }

    @Override 
    public Void visit(FieldDec fieldDec){
        fieldDec.idLeft.accept(this);
        fieldDec.idRight.accept(this);
        TDS myTDS=this.currentTDS;
        boolean trouve=false;
        if (b_type_dec){
            if (!typeExist(((Identifier)fieldDec.idRight).name)) {
                errors.add(new UnknownType(fieldDec.line, fieldDec.charPos));
            }
            trouve = true;
        }
        while(myTDS!=null && !trouve){
            if (!(fieldDec.idLeft instanceof Identifier)){
                //erreur
            }
            Identifier ident = (Identifier) fieldDec.idLeft;
            String nom = ident.name;  

     
            for (Symbole s : myTDS.table){
                if (s.nom.equals(nom)){
                    trouve = true;
                }
            }
            if (!trouve){
                myTDS=myTDS.parent;
            }
        }
        
        if (!trouve){
            errors.add(new UnknownType(fieldDec.line, fieldDec.charPos));
        }
        fieldDec.type = "void";
        return null;
    }

    @Override
    public Void visit(For f){
        TDS oldTDS = currentTDS;
        currentTDS = currentTDS.getChild("For_"+cmptFor);
        cmptFor++;
        cmptWhileFor++;
        f.id.accept(this);
        f.exp1.accept(this);
        f.exp2.accept(this);
        f.exp3.accept(this);
        currentTDS = oldTDS;
        cmptWhileFor--;
        f.type = "void";
        return null;
    }

    @Override
    public Void visit(IfThen it){
        it.exp1.accept(this);
        it.exp2.accept(this);
        if (!convTypeToAtom(it.exp1.getType()).equals("int")){
            errors.add(new ConditionIf(it.exp1.getLine(), it.exp1.getCharPos()));
        }
        if (!convTypeToAtom(it.exp2.getType()).equals("void")){
            errors.add(new BodyIf(it.exp2.getLine(), it.exp2.getCharPos()));
        }
        it.type = "void";
        return null;
    }

    @Override
    public Void visit(IfThenElse ite){
        ite.exp1.accept(this);
        ite.exp2.accept(this);
        ite.exp3.accept(this);
        if (!convTypeToAtom(ite.exp1.getType()).equals("int")){
            errors.add(new ConditionIf(ite.exp1.getLine(), ite.exp1.getCharPos()));
        }
        if (!convTypeToAtom(ite.exp2.getType()).equals(convTypeToAtom(ite.exp3.getType()))){
            errors.add(new BodyElse(ite.exp2.getLine(), ite.exp2.getCharPos()));
        }
        ite.type = convTypeToAtom(ite.exp2.getType());
        return null;
    }

    @Override
    public Void visit(Neg n){
        n.NegExp.accept(this);
        if (!convTypeToAtom(n.NegExp.getType()).equals("int")){
            errors.add(new NegNotInt(n.NegExp.getLine(), n.NegExp.getCharPos()));
        }
        n.type = "int";
        return null;
    }

    @Override
    public Void visit(RecCreate rc){
        for (Ast el : rc.liste){
            el.accept(this);
        }
        rc.type = "void";
        return null;
    }

    @Override
    public Void visit(TypeDec td){
        boolean oldTd = b_type_dec;
        b_type_dec = true;
        if (td.ty instanceof Identifier){
            Identifier ident = (Identifier) td.ty;
            if (ident.name.equals("int") || ident.name.equals("string")){
                ident.type = ident.name;
            }
        }
        td.id.accept(this);
        td.ty.accept(this);
        b_type_dec = oldTd;
        td.type = "void";
        return null;
    }


    @Override
    public Void visit(VarDec vd){
        vd.id.accept(this);
        vd.varDecA.accept(this);

        TDS myTDS = currentTDS;
        int cpt = 0;
        while(myTDS!=null && cpt < 2){
            if (!(vd.id instanceof Identifier)){
                //erreur
            }
            Identifier ident = (Identifier) vd.id;
            String nom = ident.name;       
            for (Symbole s : myTDS.table){
                if (s.nom.equals(nom) && s instanceof Variable){
                    cpt++;
                }
            }
            if (cpt < 2){
                myTDS=myTDS.parent;
            }
        }
        if (cpt > 1){
            errors.add(new VarDoubleDecla(vd.getLine(), vd.getCharPos(), ((Identifier)vd.id).name));
        }

        vd.type = convTypeToAtom(vd.varDecA.getType());
        return null;
    }

    @Override
    public Void visit(VarDecIdExp vardec){
        vardec.exp.accept(this);
        vardec.id.accept(this);
        vardec.type = ((Identifier)vardec.id).name;
        if (!typeExist(vardec.getType())) {
            errors.add(new UnknownType(vardec.line, vardec.charPos));
        }
        vardec.type = convTypeToAtom(vardec.getType());
        return null;
        
    }

    @Override
    public Void visit(While w){
        cmptWhileFor++;
        w.exp1.accept(this);
        w.exp2.accept(this);
        w.type = "void";

        if(!convTypeToAtom(w.exp1.getType()).equals("int")){
            errors.add(new ConditionWhile(w.getLine(), w.getCharPos()));
        }

        if(!convTypeToAtom(w.exp2.getType()).equals("void")){
            errors.add(new BodyWhile(w.getLine(), w.getCharPos()));
        }

        cmptWhileFor--;
        return null;
    }

    @Override
    public Void visit(Nil n){
        n.type = "void";
        return null;
    }

    @Override
    public Void visit(ArrTy at){
        at.type.accept(this);
        at.tipe = "void";
        return null;
    }

    @Override
    public Void visit(Break b){
        if (cmptWhileFor == 0){
            errors.add(new BreakOutsideLoop(b.getLine(), b.getCharPos()));
        }
        b.type = "void";
        return null;
    }



    ///************* Fonctions sur les types *************///

    public boolean typeExist(String type){
        TDS myTDS=this.currentTDS;
        boolean trouve=false;
        for (Tuple t : myTDS.tuples){
            if (t.name.equals(type)){
                trouve=true;
            }
        }
        return trouve;
    }

    public Boolean typeVisible(String type){
        TDS myTDS=this.currentTDS;
        boolean trouve=false;
        while(myTDS!=null && !trouve){
            for (Symbole s : myTDS.table){
                if (s instanceof Type){
                    if (((Type)s).nom.equals(type)){
                        trouve=true;
                    }
                }
            }
            myTDS=myTDS.parent;
        }
        return trouve;
    }

    public String convType(String type){
        TDS myTDS=this.currentTDS;
        boolean trouve=false;
        String res = "";
        while(myTDS!=null && !trouve){
            for (Symbole s : myTDS.table){
                if (s instanceof Type){
                    if (((Type)s).nom.equals(type)){
                        trouve=true;
                        res = ((Type)s).type.split("Type :")[1].substring(1);
                    }
                }
            }
            myTDS=myTDS.parent;
        }
        return res;
    }

    public String convTypeToAtom(String type){
        String res = type;
        while (!res.equals("int") && !res.equals("string") && !res.equals("") && !res.contains("Array") && !res.contains(";") && !res.contains("void")){
            res = convType(res);
        }
        return res;
    }


    public boolean IsRecord(String type){
        String res = convTypeToAtom(type);
        return res.contains(";");
    }
}
