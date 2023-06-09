package controles_sem;

public class BreakOutsideLoop extends Error {
    
        public BreakOutsideLoop(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le break doit être dans une boucle.";
            return err;
        }
}
