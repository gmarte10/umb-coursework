// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a for-statement.
 */
class JForStatement extends JStatement {
    // Initialization.
    private ArrayList<JStatement> init;

    // Test expression
    private JExpression condition;

    // Update.
    private ArrayList<JStatement> update;

    // The body.
    private JStatement body;

    /**
     * Constructs an AST node for a for-statement.
     *
     * @param line      line in which the for-statement occurs in the source file.
     * @param init      the initialization.
     * @param condition the test expression.
     * @param update    the update.
     * @param body      the body.
     */
    public JForStatement(int line, ArrayList<JStatement> init, JExpression condition,
                         ArrayList<JStatement> update, JStatement body) {
        super(line);
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    public JForStatement analyze(Context context) {
        // TODO
        LocalContext localContext = new LocalContext(context);
        for (int i = 0; i < init.size(); i++) {
            JStatement in = init.get(i);
            init.set(i, (JStatement) in.analyze(localContext));
        }
        condition = condition.analyze(localContext);
        condition.type.mustMatchExpected(line(), Type.BOOLEAN);
        for (int i = 0; i < update.size(); i++) {
            JStatement up = update.get(i);
            update.set(i, (JStatement) up.analyze(localContext));
        }
        body = (JStatement) body.analyze(localContext);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        // TODO
        String test = output.createLabel();
        String out = output.createLabel();
        for (int i = 0; i < init.size(); i++) {
            JStatement in = init.get(i);
            in.codegen(output);
        }
        output.addLabel(test);
        condition.codegen(output, out, false);
        body.codegen(output);
        for (int i = 0; i < update.size(); i++) {
            JStatement up = update.get(i);
            up.codegen(output);
        }
        output.addBranchInstruction(GOTO, test);
        output.addLabel(out);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JForStatement:" + line, e);
        if (init != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Init", e1);
            for (JStatement stmt : init) {
                stmt.toJSON(e1);
            }
        }
        if (condition != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Condition", e1);
            condition.toJSON(e1);
        }
        if (update != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Update", e1);
            for (JStatement stmt : update) {
                stmt.toJSON(e1);
            }
        }
        if (body != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Body", e1);
            body.toJSON(e1);
        }
    }
}
