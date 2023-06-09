package controles_sem;

public class Error {
    public int line;
    public int charPos;

    public Error(int line,int charPos){
        this.line = line;
        this.charPos = charPos;
    }
}