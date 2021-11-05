package lexer;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    public static int column = 0;
    char peek = ' ';
    Hashtable words = new Hashtable();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Word.Int);
        reserve(Word.Double);
        reserve(Word.String);
        reserve(Word.Bool);
        reserve(Word.Void);
        reserve(Word.Return);
        reserve(Word.Class);
        reserve(Word.Public);
        reserve(Word.Static);
        reserve(Word.Extends);
        reserve(Word.New);
        reserve(Word.Null);
        reserve(Word.This);
        reserve(Word.If);
        reserve(Word.Else);
        reserve(Word.While);
        reserve(Word.NewArray);
        reserve(Word.Print);
        reserve(Word.ReadInteger);
        reserve(Word.ReadLine);
    }

    public void readch() throws IOException {
        peek = (char) System.in.read();
        column++;
    }

    boolean readch(char c) throws IOException {
        readch();
        if (peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        // ignore ' ', '\n', '\t'
        for (;; readch()) {
            if (peek == ' ' || peek == '\t')
                continue;
            else if (peek == '\n') {
                line += 1;
                column = 0;
            } else {
                break;
            }
        }

        // @
        if (peek == '@') {
            throw new Error(
                String.format("ERROR %d:%d: unsupport char", line, column));
        }

        // comment
        if (peek == '/') {
            readch();
            if (peek == '/') {
                while (peek != '\n' && peek != '\r' && peek != '\0') {
                    readch();
                }
            } else if (peek == '*') {
                while (true) {
                    readch();
                    if (peek == '\0') {
                        throw new Error(
                            String.format("ERROR %d:%d: comment not terminated",
                                          line, column));
                    }
                    if (peek == '*') {
                        if (readch('/')) {
                            return Word.Comment;
                        }
                    }
                    if (peek == '/') {
                        if (readch('*')) {
                            throw new Error(String.format(
                                "ERROR %d:%d: comment not terminated", line,
                                column));
                        }
                    }
                    if (peek == '\n') {
                        line++;
                        column = 0;
                    }
                }
            }
            if (peek == '\n' || peek == '\r') {
                return Word.Comment;
            }
        }

        // compound lexical unit
        switch (peek) {
        case '&':
            if (readch('&'))
                return Word.and;
            else
                return new Token('&');
        case '|':
            if (readch('|'))
                return Word.or;
            else
                return new Token('|');
        case '=':
            if (readch('='))
                return Word.eq;
            else
                return new Token('=');
        case '!':
            if (readch('='))
                return Word.ne;
            else
                return new Token('!');
        case '<':
            if (readch('='))
                return Word.le;
            else
                return new Token('<');
        case '>':
            if (readch('='))
                return Word.ge;
            else
                return new Token('>');
        }
        // string
        if (peek == '"') {
            StringBuffer b = new StringBuffer();
            for (;;) {
                if (readch('"')) {
                    String s = b.toString();
                    Word w = (Word) words.get(s);
                    if (w != null) return w;
                    w = new Word(s, Tag.STRING);
                    words.put(s, w);
                    return w;
                } else if (peek == '\n') {
                    throw new Error(String.format("Error: %d:%d: Missing \"",
                                                  line, column));
                } else {
                    b.append(peek);
                }
            }
        }
        // number
        if (Character.isDigit(peek)) {
            int v = 0;
            if (peek == 0) {
                // hex number
                readch();
                if (peek == 'x' || peek == 'X') {
                    for (;;) {
                        readch();
                        if (peek >= '0' && peek <= '9')
                            v = v * 16 + peek - '0';
                        else if (peek >= 'a' && peek <= 'f')
                            v = v * 16 + peek - 'a' + 10;
                        else if (peek >= 'A' && peek <= 'F')
                            v = v * 16 + peek - 'A' + 10;
                        else
                            break;
                    }
                    return new HexNum(v);
                } else if (peek == '.') {
                    double x = v;
                    double d = 10;
                    for (;;) {
                        readch();
                        if (peek == 'e' || peek == 'E') {
                            // double number
                            readch();
                            int sym = 1;
                            if (peek == '+' || peek == '-') {
                                if (peek == '-') sym = -1;
                                readch();
                            } else {
                                throw new Error(String.format(
                                    "ERROR: %d:%d: Invalid double number", line,
                                    column));
                            }
                            int exp = 0;
                            while (Character.isDigit(peek)) {
                                exp = 10 * exp + Character.digit(peek, 10);
                                readch();
                            }
                            return new Real(x * Math.pow(10, exp * sym));
                        } else {
                            if (!Character.isDigit(peek)) break;
                            x = x + Character.digit(peek, 10) / d;
                            d = d * 10;
                        }
                    }
                    return new Real(x);
                }
            } else {
                do {
                    v = 10 * v + Character.digit(peek, 10);
                    readch();
                } while (Character.isDigit(peek));
                if (peek != '.') return new Num(v);
                double x = v;
                double d = 10;
                for (;;) {
                    readch();
                    if (peek == 'e' || peek == 'E') {
                        // double number
                        readch();
                        int sym = 1;
                        if (peek == '+' || peek == '-') {
                            if (peek == '-') sym = -1;
                            readch();
                        } else {
                            throw new Error(String.format(
                                "ERROR: %d:%d: Invalid double number", line,
                                column));
                        }
                        int exp = 0;
                        while (Character.isDigit(peek)) {
                            exp = 10 * exp + Character.digit(peek, 10);
                            readch();
                        }
                        return new Real(x * Math.pow(10, exp * sym));
                    } else {
                        if (!Character.isDigit(peek)) break;
                        x = x + Character.digit(peek, 10) / d;
                        d = d * 10;
                    }
                }
                return new Real(x);
            }
        }
        // identifier or keyword
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek) ||
                     peek == '_'); // '_' is allowed
            String s = b.toString();
            Word w = (Word) words.get(s);
            if (w != null) return w;
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        Token tok = new Token(peek);
        peek = ' ';
        return tok;
    }

    public void out() {
        System.out.println(words.size());
    }

    public char getPeek() {
        return peek;
    }

    public void setPeek(char peek) {
        this.peek = peek;
    }
}
