import java.util.Scanner;

class UnderAge extends Exception {
    int age;

    UnderAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Under Age: " + age;
    }
}

public class ExceptionDemo {
    static void test(int age) throws UnderAge {
        if (age < 18) {
            throw new UnderAge(age);
        } else {
            System.out.println("Eligible for voting. Age: " + age);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        try {
            test(age);
        } catch (UnderAge e) {
            System.out.println(e);
        }

        sc.close();
    }
}