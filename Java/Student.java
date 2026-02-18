import java.util.Scanner;

class Student {
    String usn;
    String name;
    String branch;
    String phone;
    double percentage;

    void getData(Scanner sc) {
        System.out.print("Enter USN: ");
        usn = sc.nextLine().toUpperCase();

        System.out.print("Enter Name: ");
        name = sc.nextLine().toUpperCase();

        System.out.print("Enter Branch: ");
        branch = sc.nextLine().toUpperCase();

        System.out.print("Enter Phone: ");
        phone = sc.nextLine().toUpperCase();

        System.out.print("Enter Percentage: ");
        percentage = sc.nextDouble();
        sc.nextLine();
    }

    void display() {
        System.out.printf("%-15s %-15s %-15s %-10s %-10.2f\n",
                usn, name, branch, phone, percentage);
    }

    public static void printCentered(String text) {
        int width = 80;
        int padding = (width - text.length()) / 2;
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(text);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        Student[] s = new Student[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details of Student " + (i + 1));
            s[i] = new Student();
            s[i].getData(sc);
        }

        System.out.println("\033[1m");
        printCentered("STUDENT DETAILS");
        System.out.println("\033[0m");

        printCentered("USN            NAME           BRANCH         PHONE          PERCENTAGE");

        for (int i = 0; i < n; i++) {
            printCentered(
                String.format("%-15s %-15s %-15s %-15s %-10.2f",
                        s[i].usn, s[i].name, s[i].branch, s[i].phone, s[i].percentage)
            );
        }

        sc.close();
    }
}
