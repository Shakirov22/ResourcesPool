package edu.ResourcesPool;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateEquations {

    private static String generateEquation(int id, int maxLengthId) {
        double first_coefficient = Math.random() * 10 - 5;
        double second_coefficient = Math.random() * 10 - 5;
        double third_coefficient = Math.random() * 10 - 5;
        int idLength = Integer.toString(id).length();
        StringBuilder iden = new StringBuilder();
        for (int i = 0; i < maxLengthId - idLength; i++) {
            iden.append("0");
        }
        iden.append(id);
        return iden + ") " + first_coefficient + " * x^2 + " + second_coefficient + " * x + " + third_coefficient + " = 0";
    }

    public static void main(String[] args) throws IOException {
        int equationsCount = 10000;
        int maxLengthId = Integer.toString(equationsCount).length();
        FileWriter writer = new FileWriter("src/resources/equations.txt");
        for (int i = 0; i < equationsCount; i++) {
            writer.write(generateEquation(i + 1, maxLengthId) + System.getProperty("line.separator"));
        }
        writer.close();
    }
}
