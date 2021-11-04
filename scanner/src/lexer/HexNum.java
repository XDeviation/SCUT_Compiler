package lexer;

public class HexNum extends Token {

    public final String value;

    public HexNum(int v) {
        super(Tag.NUM);
        value = Integer.toHexString(v);
    }

    public String toString() {
        return "0x" + value;
    }
}
