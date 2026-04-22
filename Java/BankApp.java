interface Bank {
    double rate_of_interest();
}

class SBI implements Bank {
    String branch;
    double interestRate;

    SBI(String branch, double interestRate) {
        this.branch = branch;
        this.interestRate = interestRate;
    }

    public double rate_of_interest() {
        return interestRate;
    }

    void display() {
        System.out.println("SBI Branch: " + branch);
        System.out.println("SBI Interest Rate: " + rate_of_interest() + "%");
    }
}

class PNB implements Bank {
    String branch;
    double interestRate;

    PNB(String branch, double interestRate) {
        this.branch = branch;
        this.interestRate = interestRate;
    }

    public double rate_of_interest() {
        return interestRate;
    }

    void display() {
        System.out.println("PNB Branch: " + branch);
        System.out.println("PNB Interest Rate: " + rate_of_interest() + "%");
    }
}

public class BankApp {
    public static void main(String[] args) {
        SBI sbi = new SBI("Delhi", 6.5);
        PNB pnb = new PNB("Mumbai", 7.0);

        sbi.display();
        System.out.println();
        pnb.display();
    }
}