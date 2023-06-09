package controles_sem;

public class ConditionWhile extends Error{
    
        public ConditionWhile(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le type de la condition du while n'est pas int.";
            return err;
        }
    
}
