package mathutils;

public class CubeCalculator {
    public int calculateCube(int n) {
        return n * n * n;
    }

    public long calculateCube(long n) {
        return n * n * n;
    }

    public float calculateCube(float n) {
        return n * n * n;
    }

    public double calculateCube(double n) {
        return n * n * n;
    }

    public double calculateCubeOfSquare(double side) {
        return Math.pow(side, 3);
    }

    public double calculateCubeOfRectangle(double length, double width, double height) {
        return length * width * height;
    }
}
