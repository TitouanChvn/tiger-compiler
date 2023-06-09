package genCode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.analysis.LeftRecursiveRuleAnalyzer.ASSOC;

import ast.*;
import tds.*;

public class Gencode implements AstVisitor<Void>{

    private TDS currentTDS;
    private Ast tree;
    private File file;
    private FileWriter writer;
    private ArrayList used_registers;
    private int current_let=-1;
    private int indice_mult=0;
    private int indice_div=0;
    private int indice_for=0;
    private int indice_while=0;
    private int indice_if=0;
    private int indice_ifelse=0;
    private int STACK_BASE = 65536;
    // on pourra créer une fct a appeller si on a  besoin de libérer un registre
    //qui empilerai un registre ett faudrait penser a le dépiler
    //mais ça décalerai toute la pile... donc non ? 
    private String funDecBuffer;

    public Gencode(Ast a, TDS t) {
        this.tree = a;
        this.currentTDS = t;
        this.used_registers = new ArrayList<Integer>();
        funDecBuffer = "";
        

        File file = new File("out.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Problème a la création de out.txt");
            }
        }
        else {
            //System.out.println("out.txt existe déja");
        }
        this.file = file ;
        try {
            this.writer = new FileWriter(file);
        } catch (IOException e) {
            System.out.println("Problème a la création du writer");
        }
        
        
    }

    public void generate(){
        tree.accept(this);
        
    }

    public int char_to_int(char c){
        return (int) c;
    }

    

    //Visiteur spécial pour obtenir le nom d'un ID:
    public String get_Id(Identifier id){

        return id.name;
    }


    ///**** VISITEURS ****///


    @Override
    public Void visit(Div div){
        div.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        div.right.accept(this);
        indice_div++;

        try {
            writer.write("\t" + "BL" + "\tdiv_" + indice_div + "\n");
            writer.write("div_"+indice_div+"\t STMFA \t SP!, {R2-R5}\n");
            writer.write("\tMOV \t R0, #0\n");
            writer.write("\tMOV \t R3, #0\n");
            writer.write("\t CMP \t R1, #0\n");
            writer.write("\t RSBLT \t R1, R1, #0\n");
            writer.write("\t EORLT \t R3, R3, #1\n");
            writer.write("\t CMP \t R2, #0\n");
            writer.write("\t RSBLT \t R2, R2, #0\n");
            writer.write("\t EORLT \t R3, R3, #1\n");
            writer.write("\t MOV \t R4, R2\n");
            writer.write("\t MOV \t R5, #1\n");
            writer.write("Mdiv_"+indice_div+"\t LSL \t R4, R4, #1\n");
            writer.write("\t LSL \t R5, R5, #1\n");
            writer.write("\t CMP \t R4, R1\n");
            writer.write("\t BLE Mdiv_"+ indice_div + "\n");
            writer.write("Ldiv_"+indice_div+"\t LSR \t R4, R4, #1\n");
            writer.write("\t LSR \t R5, R5, #1\n");
            writer.write("\t CMP \t R4, R1\n");
            writer.write("\t BGT Ldiv_"+ indice_div + "\n");
            writer.write("\t ADD \t R0, R0, R5\n");
            writer.write("\t SUB \t R1, R1, R4\n");
            writer.write("\t CMP \t R1, R2\n");
            writer.write("\t BGE Ldiv_"+ indice_div + "\n");
            writer.write("\t CMP R3, #1\n");
            writer.write("\t BNE \t Ediv_"+ indice_div + "\n");
            writer.write("\t CMP \t R1, #0\n");
            writer.write("\t ADDNE \t R0, R0, #1 \n");
            writer.write("\t RSB R0, R0, #0\n");
            writer.write("\t RSB R1, R1, #0\n");
            writer.write("\t ADDNE \t R1, R1, R2 \n");
            writer.write("Ediv_"+indice_div+"\t LDMFA \t SP!, {R2-R5}\n");
            writer.write("\t MOV \t R2, R0\n");

        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
      
        return null;
    }

    @Override
    public Void visit(Mult mult){
        mult.left.accept(this);

        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        mult.right.accept(this);
        indice_mult++;

        try {
            writer.write("\t" + "BL" + "\tmult_" + indice_mult + "\n");
            writer.write("mult_"+indice_mult+"\t STMFA \t SP!, {R1,R2}\n");
            writer.write("\tMOV \t R0, #0\n");
            writer.write("Lmult_"+indice_mult+"\t LSRS R2,R2,#1 \n");
            writer.write("\t ADDCS \t R0, R0, R1\n");
            writer.write("\t LSL R1,R1,#1 \n");
            writer.write("\t TST R2,R2 \n");
            writer.write("\t BNE Lmult_"+indice_mult+"\n");
            writer.write("\t LDMFA \t SP!, {R1,R2}\n");
            writer.write("\t MOV \t R2, R0\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        return null;
    }

    @Override
    public Void visit(Sup sup){
        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        sup.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        sup.right.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "SUBS" + "\t" + "R2," + " " + "R1," + " " + "R2" + "\n");
            writer.write("\t" + "MOVMI" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t" + "MOVPL" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(Egal egal){


        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        egal.left.accept(this);

        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        egal.right.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "CMP" + "\t" + "R2," + "\t" + "R1" + "\n");
            writer.write("\t" + "MOVNE" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t" + "MOVEQ" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

      
        return null;
    }

    @Override
    public Void visit(Diff diff){
        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }


        diff.exp1.accept(this);

        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        diff.exp2.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "CMP" + "\t" + "R2," + "\t" + "R1" + "\n");
            writer.write("\t" + "MOVNE" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t" + "MOVEQ" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(AndExp and){

        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        and.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        and.right.accept(this);
        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "AND" + "\t" + "R2," + "\t" + "R2," + "\t" + "R1"+"\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(OrExp or){
        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        or.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        or.right.accept(this);
        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "ORR" + "\t" + "R2," + "\t" + "R2," + "\t" + "R1"+"\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(ListExp affect){
        try {
            writer.write("\n");
            writer.write("let_"+current_let);
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        for (Ast e : affect.ListExp){
            e.accept(this);
        }

        try {
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(ListDec ld){
        for (Ast d : ld.ListDec){
            d.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Inf inf){

        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        inf.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        inf.right.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "SUBS" + "\t" + "R0," + " " + "R2," + " " + "R1" + "\n");
            writer.write("\t" + "MOVMI" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t" + "MOVPL" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(StrictInf si){

        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        si.exp1.accept(this);

        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        si.exp2.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "SUBS" + "\t" + "R0," + " " + "R2," + " " + "R1" + "\n");
            writer.write("\t" + "MOVMI" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t" + "MOVPL" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t"+ "MOVEQ" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }


        
        return null;
    }

    @Override
    public Void visit(Let l){
        current_let++;
        currentTDS = currentTDS.getChild("let_"+current_let);
        l.ListDec.accept(this);
        try{
            writer.write("\t" + "B" + "\t" + "let_"+current_let);
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        l.expr.accept(this);
        

        //en fin de programme on écrit les fonctions déclarées (stockées dans funDecBuffer)
        try{
            writer.write("\n");
            writer.write(";DECLARATIONS DE FONCTIONS");
            writer.write("\n\n");
            writer.write(funDecBuffer);
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        currentTDS = currentTDS.parent;
        return null;
    }

    public Void visit(StrictSup ss){

        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        ss.expr1.accept(this);

        try {

            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");

            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        ss.expr2.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "SUBS" + "\t" + "R0," + " " + "R1," + " " + "R2" + "\n");
            writer.write("\t" + "MOVMI" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t" + "MOVPL" + "\t" + "R2," + "\t" + "#1" + "\n");
            writer.write("\t"+ "MOVEQ" + "\t" + "R2," + "\t" + "#0" + "\n");
            writer.write("\t"+"SUB"+" SP, SP, #12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(Add add){
        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        add.left.accept(this);
        try {
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        
        add.right.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "ADD" + "\t" + "R2," + "\t" + "R1," + "\t" + "R2"+"\n");
            writer.write("\t"+"SUB"+" SP, "+"SP, "+"#12\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(Sub sub){

        try {
            writer.write("\t"+"ADD"+"\t"+"SP,"+"\t"+"SP,"+"\t"+"#12"+"\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }

        sub.left.accept(this);


        try {
        
            writer.write("\t" + "MOV" + "\t" + "R1," + "\t" + "R2" + "\n");
            writer.write("\t"+"STR"+" R1, "+"[R12, #0]");
            writer.write("\n");

            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        sub.right.accept(this);

        try {
            writer.write("\t"+"STR"+" R2, "+"[R12, #4]");
            writer.write("\n");
            writer.write("\t"+"LDR"+" R1, "+"[R12, #0]\n");
            writer.write("\t" + "SUB" + "\t" + "R2," + "\t" + "R1," + "\t" + "R2"+"\n");
            writer.write("\t"+"SUB"+" SP, "+"SP, "+"#12\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(IntNode in){
        try {
            writer.write("\t"+"MOV"+"\t"+"R2, #"+in.value+"\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(StringNode sn){
        //create a list of int from the string
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < sn.value.length(); i++){
            list.add(char_to_int(sn.value.charAt(i)));
        }
        //end the list with special int : 999
        list.add(999);
        //write the list in the file 
        try {
            
            //writer.write("\n");
//
            //writer.write("\t"+"STR"+"\t"+"r12, [SP]");
            //writer.write("\n");
            //writer.write("\t"+"ADD"+"\t"+"SP,SP, #4");
            //writer.write("\n");
            

            for (int i = 0; i < list.size(); i++){
                
                writer.write("\t"+"LDR"+"\t"+"r8, ="+list.get(i)+"");
                writer.write("\n");
                writer.write("\t"+"STR"+"\t"+"r8,"+"[r12]");
                
                writer.write("\n");
                writer.write("\t"+"ADD"+"\t"+"r12, r12, #-4");
                
                writer.write("\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(Identifier id){
        String idName = id.name;
        int d=getTheD(currentTDS)-4;

        int d_svp = 0;
        
        

        for (int i = 0; i  < currentTDS.table.size(); i++){
            if (currentTDS.table.get(i).nom.equals(idName)){
                d_svp = currentTDS.table.get(i).deplacement;
            }
        }

        if (d_svp == 0){
            TDS tmpTDS = currentTDS.parent;
            for (int i = 0; i  < tmpTDS.table.size(); i++){
                if (tmpTDS.table.get(i).nom.equals(idName)){
                    d_svp = tmpTDS.table.get(i).deplacement;
                }
            }
            d = getTheD(tmpTDS)-4;
        }
        
        d += d_svp;

        try {
            writer.write("\t"+"LDR"+"\t"+"R2,"+"[R6, #"+d+"]");
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture de l'id dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(Program prog){
        try{
            writer.write("STACK_BASE"+ "\t" +"EQU" + "\t" + "0x1000");
            writer.write("\n");
            writer.write("HEAP_BASE"+ "\t" +"EQU" + "\t" + "0x10000");
            writer.write("\n");
            writer.write("\t" +"MOV"+ "\t" + "SP," + "\t" + "#STACK_BASE");
            writer.write("\n");
            writer.write("\t" +"MOV"+ "\t" + "R12," + "\t" + "#HEAP_BASE");
            writer.write("\n");
            writer.write("\t" +"MOV"+ "\t" + "R6," + "\t" + "#STACK_BASE");
            writer.write("\n");
            writer.flush();
            //le B main sera après la list de déclarations
            //reste du code d'initialisation
        }catch (IOException e){
            System.out.println("Problème a l'écriture de l'initialisation ");}
        
        for (Ast a : prog.instrList){
            a.accept(this);
        }

        try {
            writer.write("END");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture de l'initialisation ");
        }

        return null;
    }

    @Override
    public Void visit(RecTy rec){
        return null;
    }

    @Override
    public Void visit(ListrecTyFieldDec lfd){
        return null;
    }

    @Override
    public Void visit(FunDec fd){
        //fd.FunDecBody.accept(this);
        //fd.ListArgs.accept(this); 
        //fd.id.accept(this); ok
        String fun_id=this.get_Id((Identifier) fd.id);
        for (int i=0; i<currentTDS.table.size(); i++){
            if (currentTDS.table.get(i).nom.equals(fun_id)){
                currentTDS = currentTDS.getChild(fun_id);
                break;
            }
        }

    
        try {
            writer.write("\t"+"B"+" decla_post_"+fun_id+"\n");
            writer.write("function_"+fun_id+"\n");
            writer.write("\t"+";FUNCTION CONTENT \n");

            fd.FunDecBody.accept(this);
            
            //on dépile les arguments avec le résultat dans R2
            int nb_args = ((ListArgs) fd.ListArgs).ListArgs.size();
            writer.write("\t"+"SUB"+"\t"+"SP,SP, #"+(nb_args)*4+"\n");

            writer.write("\t"+"B"+" suite_"+fun_id+"\n");
            writer.write("decla_post_"+fun_id+"\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture de la fonction dans le fichier");
        }
        //le buffer sera écrit a la fin du programme après le main
        return null;
    }

    @Override
    public Void visit(ListArgs la){
        
        return null;
    }

    @Override
    public Void visit(FunDecBodyWithoutId fdbwi){
        fdbwi.expr.accept(this);

        return null;
    }

    @Override
    public Void visit(FunDecBodyWithId fdbwi){
        //fdbwi.id.accept(this);
        fdbwi.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(LValue lv){
        lv.id.accept(this);
        //for (Ast a : lv.values){
        //    a.accept(this);
        //}
        return null;
    }

    @Override
    public Void visit(SeqExp seq){
        for (Ast a : seq.seqExpList){
            a.accept(this);
        }

        return null;
    }

    @Override
    public Void visit(CallExp callExp){
        //callExp.id.accept(this);


        // pour call une fct : 
        try{
            String funid = this.get_Id((Identifier) callExp.id);
            //On empile les arguments :
            
            callExp.ListExp.accept(this); //cf visit(callexplist) juste en dessous
            


            writer.write("\t"+"B"+"\t"+"function_"+funid+"\n");
            //callExp.id.accept(this);
            writer.write("\n");
            writer.write("suite_"+ funid);  //on mettra un 'B suite_...' en fin de corps de la fct
            //callExp.id.accept(this);
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de la fonction dans le fichier");
        }
        
        return null;
    }

    @Override
    public Void visit(CallExpList cel){
        int i=0;
        for (Ast ex : cel.CallExpList){
            i++;
            ex.accept(this);
            try{

                writer.write("\t"+"STR"+"\t"+"R2,"+"[SP,#"+((i-1)*4)+"]");
                writer.write("\n");
                writer.flush();
            }
            catch (IOException e){
                System.out.println("Problème a l'écriture de la fonction dans le fichier");
            }
        }
        try {
            writer.write("\t"+"ADD"+"\t"+"SP,SP,#"+(i*4));
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de la fonction dans le fichier");
        }

        return null;
    }


    @Override
    public Void visit(Affect aff){
        return null;
    }

    @Override
    public Void visit(ArrCreate ar){
        return null;
    }

    @Override
    public Void visit(Assign a){
        LValue Lval = (LValue)a.lValue;
        String varname = ((Identifier)Lval.id).name;
        int d=getTheD(currentTDS)-4;

        int d_svp=0;

        for (int i = 0; i  < currentTDS.table.size(); i++){
            if (currentTDS.table.get(i).nom.equals(varname)){
                d_svp = currentTDS.table.get(i).deplacement;
            }
        }

        if (d_svp == 0) {
            TDS tmpTds = currentTDS.parent;
            for (int i = 0; i  < tmpTds.table.size(); i++){
                if (tmpTds.table.get(i).nom.equals(varname)){
                    d_svp = tmpTds.table.get(i).deplacement;
                }
            }
            d = getTheD(tmpTds)-4;
        }
        
        d += d_svp;
       

        try{
            if (a.exp instanceof IntNode){
                writer.write("\t"+"MOV"+"\t"+"R2,"+"\t"+"#"+((IntNode)a.exp).value+"\n");
                writer.write("\t"+"ADD"+"\t"+"R0,"+"R6, #"+d+"\n");
                writer.write("\t"+"MOV"+"\t"+"R1,"+"R2\t");
                writer.write("\n");
                writer.write("\t"+"STR"+"\t"+"R1,"+"\t"+"[R0]");
                writer.write("\n");

                writer.flush();
            } else if (a.exp instanceof Identifier){
                a.exp.accept(this);
                writer.write("\t"+"MOV"+"\t"+"R1,"+"R2\t");
                writer.write("\n");
                writer.write("\t"+"ADD"+"\t"+"R0,"+"R6, #"+d+"\n");
                writer.write("\t"+"STR"+"\t"+"R1,"+"\t"+"[R0]");
                writer.write("\n");

                writer.flush();

            } else if (a.exp instanceof StringNode) {
                writer.write("\t"+"ADD"+"\t"+"R0,"+"R6, #"+d+"\n");
                writer.flush();
                //System.out.println(""+d);
                writer.write("\n");
                writer.write("\t"+"STR"+"\t"+"R12,"+"\t"+"[R0]\n");
                a.exp.accept(this);
                
                writer.flush();
            } else {
                a.exp.accept(this);
                writer.write("\t"+"ADD"+"\t"+"R0,"+"R6, #"+d+"\n");
                writer.write("\t"+"MOV"+"\t"+"R1,"+"R2\t");
                writer.write("\n");
                writer.write("\t"+"STR"+"\t"+"R1,"+"\t"+"[R0]");
                writer.write("\n");
                writer.write(";other\n");
                writer.flush();
            }
           
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de l'affectation dans le fichier");
        }        

        return null;
    }

    @Override
    public Void visit(Create create){
        return null;
    }

    @Override 
    public Void visit(FieldDec fieldDec){
        return null;
    }

    @Override
    public Void visit(For f){
        currentTDS = currentTDS.getChild("For_"+indice_for);
        f.exp1.accept(this);
        //on empile la valeur de l'indice de boucle :
        try{
            writer.write("\t"+"STR"+"\t"+"R2 , [SP]");
            writer.write("\n");
            writer.write("\t"+"MOV"+"\t"+"R9, SP\n");
            writer.write("\t"+"ADD"+"\t"+"SP, SP, #4");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de l'empilement de la valeur de l'indice de boucle");
        }
        
        try{
            writer.write("\n");
            writer.write("for_"+indice_for+"\n");
            //f.exp2.accept(this);
            int finboucle= (int)((IntNode)f.exp2).value;
            //System.out.println("final indice"+finboucle);
            writer.write("\t"+"LDR"+"\t"+"R2, [R9]\n");
            writer.write("\t"+"CMP"+"\t"+"R2, #"+finboucle+"\n");
            writer.write("\t"+"BEQ"+"\t"+"endfor_"+indice_for+"\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture du contenu de boucle");
        }
        //f.exp3.accept(this);
        try{
            writer.write(";Contenu de la boucle for_"+indice_for+"\n");
            f.exp3.accept(this);
            writer.write(";fin contenu boucle for\n");

            writer.write("\t"+"LDR"+"\t"+"R2, [R9]\n");//
            writer.write("\t"+"ADD"+"\t"+"R2, R2, #1\n"); //
            writer.write("\t"+"STR"+"\t"+"R2, [R9]\n"); //
            writer.write("\t"+"B"+"\t"+"for_"+indice_for+"\n");
            writer.write("endfor_"+indice_for);
            writer.write("\n");
            writer.write("\t"+"SUB"+"\t"+"SP, SP, #4");
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de la fin de boucle");
        }
        currentTDS=currentTDS.parent;
        return null;
    }

    @Override
    public Void visit(IfThen it){
        it.exp1.accept(this);
        int indice_courrant = indice_if;
        indice_if++;

        try {
            writer.write("\t"+"CMP"+"\t"+"R2,"+"\t"+"#0"+"\n");
            writer.write("\t"+"BEQ"+"\t"+"IF_FIN_"+indice_courrant+"\n");
            it.exp2.accept(this);
            writer.write("IF_FIN_"+indice_courrant +"\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture de l'opération dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(IfThenElse ite){
        ite.exp1.accept(this);
        int indice_courrant = indice_ifelse;
        indice_ifelse++;

        try {
            writer.write("\t"+"CMP"+"\t"+"R2,"+"\t"+"#0"+"\n");
            writer.write("\t"+"BEQ"+"\t"+"ELSE_"+indice_courrant+"\n");
            ite.exp2.accept(this);
            writer.write("\t"+"B"+"\t"+"ELSE_FIN_"+indice_courrant+"\n");
            writer.write("ELSE_"+indice_courrant+"\n");
            ite.exp3.accept(this);
            writer.write("ELSE_FIN_"+indice_courrant+"\n");
            writer.flush();
        }   catch (IOException e) {
            System.out.println("Problème a l'écriture de l'opération dans le fichier");
        }

        return null;
    }

    @Override
    public Void visit(Neg n){
        n.NegExp.accept(this);
        try {
            writer.write("\t"+"MOV"+"\t"+"R0,"+"\t"+"#0"+"\n");
            writer.write("\t"+"SUB"+"\t"+"R2,"+" "+"R0,"+" "+"R2");
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Problème a l'écriture de l'opération dans le fichier");
        }
        return null;
    }

    @Override
    public Void visit(RecCreate rc){
        return null;
    }

    @Override
    public Void visit(TypeDec td){
        return null;
    }


    @Override
    public Void visit(VarDec vd){
        //vd.id.accept(this);
        vd.varDecA.accept(this);
        return null;
    }

    @Override
    public Void visit(VarDecIdExp vardec){
        //vardec.id.accept(this);
        try{
            //if exp is a intnode :

            if (vardec.exp instanceof IntNode){
                writer.write("\t" + "LDR" +"\t"+ "r7, ="); //on stock dans r7 la valeure 
                writer.write(((IntNode)vardec.exp).value+"");                  //l'expression
                writer.write("\n");
                writer.write("\t" + "STR" + "\t" + "r7, [SP]");    //on enrengistre r7 (donc l'expr) dans la pile
                writer.write("\n");
                writer.write("\t" + "ADD" + "\t" + "SP, SP, #4"); // on incrémente le pointeur de pile
                writer.write("\n");
                writer.flush();
            } else if (vardec.exp instanceof StringNode){
                writer.write("\n");

                writer.write("\t"+"STR"+"\t"+"r12, [SP]");
                writer.write("\n");
                writer.write("\t"+"ADD"+"\t"+"SP,SP, #4");
                writer.write("\n");
                vardec.exp.accept(this);
            } else {
                vardec.exp.accept(this);
                writer.write("\t" + "STR" + "\t" + "r2, [SP]");    //on enrengistre r7 (donc l'expr) dans la pile
                writer.write("\n");
                writer.write("\t" + "ADD" + "\t" + "SP, SP, #4"); // on incrémente le pointeur de pile
                writer.write("\n");
                writer.flush();
            }
            
        }catch (IOException e){
            System.out.println("Problème a l'écriture de l'initialisation ");
        }
        
        return null;
        
    }

    @Override
    public Void visit(While w){
        int while_courant=indice_while;
        try{
            writer.write("while_"+while_courant+"\n");
        } catch (IOException e){
            System.out.println("Problème a l'écriture de l'empilement de la valeur de l'indice de boucle");
        }
        w.exp1.accept(this);
        indice_while++; //dans la pile 
        try{
            writer.write("\t"+"CMP"+"\t"+"R2, #0");
            writer.write("\n");
            writer.write("\t"+"BEQ"+"\t"+"endwhile_"+while_courant+"\n");

        } catch (IOException e){
            System.out.println("Problème a l'écriture de l'empilement de la valeur de l'indice de boucle");
        }
        try{
            writer.write(";Contenu de la boucle while"+while_courant+"\n");
            w.exp2.accept(this);
            writer.write(";fin contenu boucle while"+"\n");
            writer.write("\t"+"B"+"\t"+"while_"+while_courant+"\n");
            writer.write("endwhile_"+while_courant);
            writer.write("\n");
            //writer.write("\t"+"SUB"+"\t"+"SP, SP, #4");
            writer.write("\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Problème a l'écriture de la fin de boucle");
        }
       
        return null;
    }

    @Override
    public Void visit(Nil n){
        return null;
    }

    @Override
    public Void visit(ArrTy at){
        return null;
    }

    @Override
    public Void visit(Break b){
        return null;
    }

    public int getTheD(TDS tds){
        if (tds.parent == null) {
            return 0;
        } else {
            int n = 0;
            for (int i = 0; i < tds.parent.table.size(); i++) {
                if (tds.parent.table.get(i).deplacement > n) {
                    n = tds.parent.table.get(i).deplacement;
           }
        }
            return n;
        }
    }

}
