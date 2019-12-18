package edu.ResourcesPool;

public class Solution{
    private Double firstSolution;
    private Double secondSolution;
    /*
     * typeOfSolution:
     * 0 - Обычное квадратное уравнение
     * 1 - Уравнение первой степени
     * 2 - Нет решений
     * 3 - Бесконечное число решений
     */
    private int typeOfSolution;

    Solution(double firstSolution, double secondSolution, int typeOfSolution){
        this.firstSolution = firstSolution;
        this.secondSolution = secondSolution;
        this.typeOfSolution = typeOfSolution;
    }

    @Override
    public String toString() {
        if (typeOfSolution == 0) {
            return firstSolution + " " + secondSolution;
        } else if(typeOfSolution == 1) {
            return firstSolution + " " + "No second solution";
        } else if(typeOfSolution == 2) {
            return "No solutions";
        } else if(typeOfSolution == 3) {
            return "Infinite number of  solutions";
        }
        return super.toString();
    }
}
