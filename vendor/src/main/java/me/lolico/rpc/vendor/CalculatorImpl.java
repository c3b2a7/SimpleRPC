package me.lolico.rpc.vendor;

import me.lolico.rpc.api.Calculator;

/**
 * @author Lolico li
 */
public class CalculatorImpl implements Calculator {

    @Override
    public double add(double l, double r) {
        return l + r;
    }

    @Override
    public double sub(double l, double r) {
        return l - r;
    }

    @Override
    public double multiply(double l, double r) {
        return l * r;
    }

    @Override
    public double divide(double l, double r) {
        if (r == 0) {
            throw new ArithmeticException("/ by zero");
        }
        return l / r;
    }
}
