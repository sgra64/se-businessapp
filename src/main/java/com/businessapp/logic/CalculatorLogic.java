package com.businessapp.logic;


/**
 * ********************************************************************************
 * Local implementation class (unfinished) of calculator logic.
 * <p>
 * Instance is invoked with public interface method nextToken( Token tok ) passing
 * an input token that is created at the UI from a key event. Input tokens are defined
 * in CalculatorIntf and comprised of digits [0-9,.], numeric operators [+,-,*,/,VAT]
 * and control tokens [\backspace,=,C,CE,K_1000].
 * <p>
 * Outputs are are passed through display properties:<p>
 * - CalculatorIntf.DISPLAY for numbers and<p>
 * - CalculatorIntf.SIDEAREA for VAT calculations.
 * <p>
 * Method(s):
 * - public void nextToken( Token tok );	;process next token from UI controller
 */
class CalculatorLogic implements CalculatorLogicIntf {

    private StringBuffer dsb = new StringBuffer();

    /**
     * Local constructor.
     */
    CalculatorLogic() {
        nextToken(Token.K_C);        // reset buffers
    }

    /**
     * Process next token from UI controller. Tokens are defined in CalculatorIntf.java
     * <p>
     * Outputs are are passed through display properties:
     * - CalculatorIntf.DISPLAY for numbers and
     * - CalculatorIntf.SIDEAREA for VAT calculations.
     * <p>
     *
     * @param tok the next Token passed from the UI, CalculatorViewController.
     */
    public void nextToken(Token tok) {
        // switch , or . always to .
        String d = tok == Token.K_DOT ? "." : CalculatorLogicIntf.KeyLabels[tok.ordinal()];
        try {
            switch (tok) {
                case K_0:
                case K_1:
                case K_2:
                case K_3:
                case K_4:
                case K_5:
                case K_6:
                case K_7:
                case K_8:
                case K_9:
                    appendBuffer(d);
                    break;
                case K_1000:
                    nextToken(Token.K_0);
                    nextToken(Token.K_0);
                    nextToken(Token.K_0);
                    break;
                case K_BROP:
                    if (comparePrev("(")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_BRCL:
                    if (comparePrev(")")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_DIV:
                    if (comparePrev("/")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_MUL:
                    if (comparePrev("*")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_PLUS:
                    if (comparePrev("+")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_MIN:
                    if (comparePrev("-")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_EQ:
                    if (comparePrev("=")) {
                        break;
                    } else {
                        String math = dsb.toString();
                        dsb.setLength(0);
                        appendBuffer(calculate(math).toString());
                        CalculatorLogicIntf.SIDEAREA.set(CalculatorLogicIntf.SIDEAREA.getValue() + math + "\n");
                        break;
                    }
                case K_VAT:
                    double vat = Double.parseDouble(dsb.toString());
                    double net = Math.round((vat / (100 + VAT_RATE) * 100) * 100.0) / 100.0;
                    double vatnet = Math.round((vat - net) * 100.0) / 100.0;

                    CalculatorLogicIntf.SIDEAREA.set(
                            "Brutto:  " + vat + "\n"
                                    + VAT_RATE + "% MwSt:  " + vatnet + "\n"
                                    + "Netto:  " + net);
                    break;
                case K_DOT:
                    if (comparePrev(".")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_BACK:
                    dsb.setLength(Math.max(0, dsb.length() - 1));
                    break;
                case K_C:
                    CalculatorLogicIntf.SIDEAREA.set("");
                    break;
                case K_CE:
                    dsb.delete(0, dsb.length());
                    break;
                default:
            }

            String display = dsb.length() == 0 ? "0" : dsb.toString();
            CalculatorLogicIntf.DISPLAY.set(display);

        } catch (ArithmeticException e) {
            CalculatorLogicIntf.DISPLAY.set(e.getMessage());
        }
        System.out.println("Buffer reads: [" + dsb.toString() + "]");
    }

    /*
     * Private method(s).
     */
    private void appendBuffer(String d) {
        if (dsb.length() <= DISPLAY_MAXDIGITS) {
            dsb.append(d);
        }
    }

    private Boolean comparePrev(String input) {
        String prev;

        if (dsb.length() == 0) {
            prev = "";
        } else {
            prev = dsb.substring(dsb.length() - 1);
        }

        if (prev.matches("\\d") || prev.equals("")) {
            return false;
        } else {
            String[][] rules = new String[][]{
                /*    INPUT  //           PREVIOUS SYMBOL                */
                /* 0: empty */{"", "+", "-", "*", "/", ".", "(", ")", "="},
                /* 1:   +   */{"", "+", "-", "*", "/", ".", "(", "="},
                /* 2:   -   */{"-", ".", "="},
                /* 3:   *   */{"", "*", "+", "-", "/", ".", "(", "="},
                /* 4:   /   */{"", "/", "+", "-", "*", ".", "(", "="},
                /* 5:   .   */{"", ".", "+", "-", "*", "/", "(", ")", "="},
                /* 6:   (   */{".", ")", "="},
                /* 7:   )   */{"", "+", "-", "*", "/", ".", "(", "="},
                /* 8:   =   */{"", "=", "+", "-", "*", "/", ".", "("}
            };

            // define consequence of input-symbol, depending on previous input
            switch (input) {
                case "":
                    // rules[0] is "" row
                    for (int i = 0; i < rules[0].length; i++) {
                        if (prev.equals(rules[0][i])) {
                            return true;
                        }
                    }
                    break;
                case "+":
                    // rules[1] is "+" row
                    for (int i = 0; i < rules[1].length; i++) {
                        if (prev.equals(rules[1][i])) {
                            return true;
                        }
                    }
                    break;
                case "-":
                    // rules[2] is "-" row
                    for (int i = 0; i < rules[2].length; i++) {
                        if (prev.equals(rules[2][i])) {
                            return true;
                        }
                    }
                    break;
                case "*":
                    // rules[3] is "*" row
                    for (int i = 0; i < rules[3].length; i++) {
                        if (prev.equals(rules[3][i])) {
                            return true;
                        }
                    }
                    break;
                case "/":
                    // rules[4] is "/" row
                    for (int i = 0; i < rules[4].length; i++) {
                        if (prev.equals(rules[4][i])) {
                            return true;
                        }
                    }
                    break;
                case ".":
                    // rules[5] is "." row
                    for (int i = 0; i < rules[5].length; i++) {
                        if (prev.equals(rules[5][i])) {
                            return true;
                        }
                    }
                    break;
                case "(":
                    // rules[6] is "(" row
                    for (int i = 0; i < rules[6].length; i++) {
                        if (prev.equals(rules[6][i])) {
                            return true;
                        }
                    }
                    break;
                case ")":
                    // rules[7] is ")" row
                    for (int i = 0; i < rules[7].length; i++) {
                        if (prev.equals(rules[7][i])) {
                            return true;
                        }
                    }
                    break;
                case "=":
                    // rules[8] is "=" row
                    for (int i = 0; i < rules[8].length; i++) {
                        if (prev.equals(rules[8][i])) {
                            return true;
                        }
                    }
                    break;
                default:
            }
        }
        return false;
    }

    private Double calculate(String in) {
        double a;
        double b;

        int plus = in.indexOf("+");
        int minus = in.indexOf("-");
        int mul = in.indexOf("*");
        int div = in.indexOf("/");
        int brop = in.indexOf("(");
        int brcl = in.indexOf(")");

        if (brop >= 0 && brcl >= 0) {
            double c = calculate(in.substring(0, brop) + calculate(in.substring(brop + 1, brcl)) + in.substring(brcl + 1, in.length()));
            return c;
        } else if (plus != -1) {
            a = calculate(in.substring(0, plus));
            b = calculate(in.substring(plus + 1, in.length()));
            return a + b;
        } else if (minus != -1) {
            // TODO make negative numbers work
            a = calculate(in.substring(0, minus));
            b = calculate(in.substring(minus + 1, in.length()));
            return a - b;
        } else if (mul != -1) {
            a = calculate(in.substring(0, mul));
            b = calculate(in.substring(mul + 1, in.length()));
            return a * b;
        } else if (div != -1) {
            a = calculate(in.substring(0, div));
            b = calculate(in.substring(div + 1, in.length()));

            // special case for Divison by Zero
            if (b == 0) {
                throw new ArithmeticException("ERR: div by zero");
            }

            return a / b;
        }
        return Double.parseDouble(in);
    }
}