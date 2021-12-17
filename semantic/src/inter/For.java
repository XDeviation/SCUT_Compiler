package inter;

import symbols.Type;
import inter.Set;

public class For extends Stmt {
    Expr expr;
    Stmt stmt1;
    Stmt stmt2;
    Stmt stmt3;

    public For() {
        expr = null;
        stmt1 = null;
        stmt2 = null;
        stmt3 = null;
    }

    public void init(Stmt s1, Expr x, Stmt s2, Stmt s3) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;
        stmt3 = s3;
        if (expr.type != Type.Bool)
            expr.error("boolean required in for"); // assign x = bool
        if (stmt1.getClass().getName() != Set.class.getName())
            expr.error("assignment required in for");
        if (stmt2.getClass().getName() != Set.class.getName())
            expr.error(
                "assignment required in for"); // assign s1, s2 = assignment
    }

    public void gen(int b, int a) {
    }
}
