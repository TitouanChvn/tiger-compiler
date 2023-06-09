package controles_sem;

public class NotARecord extends Error{
        
        public NotARecord(int line,int charPos){
            super(line,charPos);
        }
        
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  L'expression n'est pas un record.";
            return err;
        }
    
}
