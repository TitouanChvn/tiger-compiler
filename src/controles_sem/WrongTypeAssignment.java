package controles_sem;

public class WrongTypeAssignment extends Error{

    
    private String et;
    private String rt;

    public WrongTypeAssignment(int line, int charPos, String expectedType, String realType) {
        super(line, charPos);
        et = expectedType;
        rt = realType;
    }

    @Override
    public String toString() {
        String err = "Ligne "+line+":"+charPos+" : \u001B[31merreur: \u001B[0m Le type de la valeur affectée est : " + et + " alors qu'il est attendu qu'il soit de type : " + rt;
        return err;
    }
}
