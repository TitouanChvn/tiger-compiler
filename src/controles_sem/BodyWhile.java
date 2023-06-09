package controles_sem;

public class BodyWhile extends Error {
    
        public BodyWhile(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Le corps d'une boucle While doit être de type void.";
            return err;
        }
}
