package controles_sem;

public class BodyIf extends Error {
    
        public BodyIf(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le corps du Then doit être de type void.";
            return err;
        }
}
