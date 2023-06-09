package controles_sem;

public class NegNotInt extends Error {
    
        public NegNotInt(int line,int charPos){
            super(line,charPos);
        }
    
        @Override
        public String toString(){
            String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m  Seul un int peut être négatif";
            return err;
        }
}
