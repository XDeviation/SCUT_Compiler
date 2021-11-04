package lexer;

public class Word extends Token {
    // manage reserved words
    public String lexeme = "";

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    public String toString() {
        return lexeme;
    }

    public static final Word
        and = new Word("&&", Tag.AND),
        or = new Word("||", Tag.OR), eq = new Word("==", Tag.EQ),
        ne = new Word("!=", Tag.NE), le = new Word("<=", Tag.LE),
        ge = new Word(">=", Tag.GE), plus = new Word("+", Tag.PLUS),
        minus = new Word("-", Tag.MINUS), times = new Word("*", Tag.TIMES),
        divide = new Word("/", Tag.DIVIDE), lparen = new Word("(", Tag.LPAREN),
        rparen = new Word(")", Tag.RPAREN), lbrace = new Word("{", Tag.LBRACE),
        rbrace = new Word("}", Tag.RBRACE),
        semicolon = new Word(";", Tag.SEMICOLON),
        comma = new Word(",", Tag.COMMA), dot = new Word(".", Tag.DOT),
        bang = new Word("!", Tag.BANG), mod = new Word("%", Tag.MOD),
        backslach = new Word("\\", Tag.BACKSLASH),
        slash = new Word("/", Tag.SLASH), True = new Word("true", Tag.TRUE),
        False = new Word("false", Tag.FALSE), Void = new Word("void", Tag.VOID),
        Int = new Word("int", Tag.INT), Bool = new Word("bool", Tag.BOOL),
        Double = new Word("double", Tag.DOUBLE),
        String = new Word("string", Tag.STRING), If = new Word("if", Tag.IF),
        Else = new Word("else", Tag.ELSE), While = new Word("while", Tag.WHILE),
        Return = new Word("return", Tag.RETURN),
        Null = new Word("null", Tag.NULL), This = new Word("this", Tag.THIS),
        New = new Word("new", Tag.NEW), Class = new Word("class", Tag.CLASS),
        Extends = new Word("extends", Tag.EXTENDS),
        NewArray = new Word("NewArray", Tag.NEWARRAY),
        Print = new Word("print", Tag.PRINT),
        ReadInteger = new Word("readInteger", Tag.READINTEGER),
        ReadLine = new Word("readLine", Tag.READLINE),
        Comment = new Word("//", Tag.COMMENT),
        Static = new Word("static", Tag.STATIC),
        Public = new Word("public", Tag.PUBLIC),
        Protected = new Word("protected", Tag.PROTECTED);
}
