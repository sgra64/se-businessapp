package com.businessapp.logic;


import java.util.regex.Pattern;

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
                case K_DIV:
                    if (checkMultiple("/")) {
                        break;
                    } else {
                        appendBuffer(d);
                        //throw new ArithmeticException( "ERR: div by zero" );
                        break;
                    }
                case K_MUL:
                    if (checkMultiple("*")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_PLUS:
                    if (checkMultiple("+")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_MIN:
                    if (checkMultiple("-")) {
                        break;
                    } else {
                        appendBuffer(d);
                        break;
                    }
                case K_EQ:
                    if (checkMultiple("=")) {
                        break;
                    } else {
                        CalculatorLogicIntf.SIDEAREA.set(calculate(dsb.toString()).toString());
                        appendBuffer(d);
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
                    if (checkMultiple(".")) {
                        break;
                    } else {

                        //appendBuffer(d);
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
    }

    /*
     * Private method(s).
     */
    private void appendBuffer(String d) {
        if (dsb.length() <= DISPLAY_MAXDIGITS) {
            dsb.append(d);
        }
    }

    private Boolean checkMultiple(String symbol) {
        //TODO
        String regex = "";
        /*
        String regex = "\\d*\\";
        regex += symbol;
        regex += "+\\d*";
        */
        if (Pattern.matches(regex, dsb.toString())) {
            System.out.println("Warning: '" + symbol + "' exists already in '" + dsb.toString() + "'.");
            CalculatorLogicIntf.SIDEAREA.set("'" + symbol + "' exists already in '" + dsb.toString() + "'.");
            return true;
        } else return false;
    }

    private Double calculate(String in) {
        double a = 0;
        double b = 0;

        int plus = in.indexOf("+");
        int minus = in.indexOf("-");
        int mul = in.indexOf("*");
        int div = in.indexOf("/");
        System.out.println("Indices of Operators for: '" + in + "'");
        //DEBUG: System.out.println("Plus: " + plus + " Minus: " + minus + " Div: " + div + " Multi: " + mul);

        if (plus != -1) {
            a = calculate(in.substring(0, plus));
            b = calculate(in.substring(plus + 1, in.length()));
            return a + b;
        } else if (minus != -1) {
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
            return a / b;
        }
        return Double.parseDouble(in);
    }
}