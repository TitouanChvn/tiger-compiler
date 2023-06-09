package controles_sem;

public class BodyElse extends Error {
    
        public BodyElse(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m Le corps du Then et du Else doivent être de même type.";
            return err;
        }
}
