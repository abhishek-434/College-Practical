import java.util.*;

class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String message) {
        super(message);
    }
}

public class CheckDuplicates {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();

        System.out.print("Enter how many numbers: ");
        int n = sc.nextInt();

        try {
            System.out.println("Enter " + n + " integers:");
            for (int i = 0; i < n; i++) {
                int num = sc.nextInt();

                if (numbers.contains(num)) {
                    throw new DuplicateNumberException("Duplicate number found: " + num);
                }

                numbers.add(num);
            }

            System.out.println("All numbers are unique.");
            System.out.println("Numbers entered: " + numbers);

        } catch (DuplicateNumberException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Program finished.");
        }
    }
}