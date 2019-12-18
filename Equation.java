package edu.ResourcesPool;

import java.util.concurrent.Callable;

import static java.lang.Math.sqrt;

public class Equation implements Callable<String> {
    private String id;
    private double first_coefficient;
    private double second_coefficient;
    private double third_coefficient;

    //i) a * x^2 + b * x + c = 0
    Equation(String equation) {
        this.id = equation.substring(0, equation.lastIndexOf(") ") + 2);
        this.first_coefficient = Double.valueOf(equation.substring(equation.lastIndexOf(") ") + 2,
                equation.lastIndexOf(" * x^2")).replaceAll(" ", ""));
        this.second_coefficient = Double.valueOf(equation.substring(equation.lastIndexOf(" * x^2 + ") + 9,
                equation.lastIndexOf(" * x ")).replaceAll(" ", ""));
        String s = equation.substring(equation.lastIndexOf(" * x + ") + 7,
                equation.lastIndexOf(" ="));
        this.third_coefficient = Double.valueOf(equation.substring(equation.lastIndexOf(" * x + ") + 7,
                equation.lastIndexOf(" =")).replaceAll(" ", ""));
    }

    Equation(double first_coefficient, double second_coefficient, double third_coefficient) {
        this.id = "test";
        this.first_coefficient = first_coefficient;
        this.second_coefficient = second_coefficient;
        this.third_coefficient = third_coefficient;
    }

    public Solution getSolution() {
        if (first_coefficient == 0) {
            if (second_coefficient == 0) {
                if (third_coefficient == 0) {
                    return new Solution(0, 0, 3);
                } else {
                    return new Solution(0, 0, 2);
                }
            } else {
                return new Solution(-third_coefficient / second_coefficient, 0, 1);
            }
        } else {
            double discriminant = second_coefficient * second_coefficient - 4 * first_coefficient * third_coefficient;
            if (discriminant < 0) {
                return new Solution(0, 0, 2);
            } else {
                return new Solution((-second_coefficient - sqrt(discriminant)) / (2 * first_coefficient),
                        (-second_coefficient + sqrt(discriminant)) / (2 * first_coefficient), 0);
            }
        }
    }

    @Override
    public String call() {
        return this.id + this.getSolution().toString();
    }

    public static void main(String[] args){
        Equation equation = new Equation(1, 2, 1);
        System.out.println(equation.getSolution().toString());
    }
}
