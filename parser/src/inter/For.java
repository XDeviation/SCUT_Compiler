package inter;

import symbols.Type;

public class For extends Stmt {

    Expr expry;
    Stmt stmt;
    Stmt stmtx;
    Stmt stmtz;

    public For() {
        expry = null;
        stmt = null;
        stmtx = null;
        stmtz = null;
    }

    public void init(Stmt x, Expr y, Stmt z, Stmt s) {
        stmtx = x;
        expry = y;
        stmtz = z;
        stmt = s;

        if (expry.type != Type.Bool) expry.error("boolean required in For");
    }
    public void gen(int b, int a) {
    }

    public void display() {
        emit("stmt : for begin");
        stmt.display();
        emit("stmt : for end");
    }
}
