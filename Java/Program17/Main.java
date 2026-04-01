import mathutils.CubeCalculator;

public class Main {
    public static void main(String[] args) {
        CubeCalculator calculator = new CubeCalculator();

        int intNum = 5;
        long longNum = 10L;
        float floatNum = 2.5f;
        double doubleNum = 4.2;

        System.out.println("--- Results using CubeCalculator ---");
        System.out.println("Cube of " + intNum + " (int): " + calculator.calculateCube(intNum));
        System.out.println("Cube of " + longNum + " (long): " + calculator.calculateCube(longNum));
        System.out.println("Cube of " + floatNum + " (float): " + calculator.calculateCube(floatNum));
        System.out.println("Cube of " + doubleNum + " (double): " + calculator.calculateCube(doubleNum));

        double side = 3.0;
        double l = 4.0, w = 5.0, h = 6.0;

        System.out.println("\n--- Geometric 'Cube' Results ---");
        System.out.println("Cube (Volume) of Square with side " + side + ": " + calculator.calculateCubeOfSquare(side));
        System.out.println("Cube (Volume) of Rectangle (" + l + "x" + w + "x" + h + "): " + calculator.calculateCubeOfRectangle(l, w, h));
    }
}
