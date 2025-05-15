import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void readInteger() {
        try {
            System.out.print("Baga un int: ");
            Scanner scanner = new Scanner(System.in);
            int x = scanner.nextInt();
            System.out.println("Ai scris numarul " + x);
        } catch (InputMismatchException e1) {
            System.out.println("Exception => " + e1);
        }
    }

    public static void parseAndDivide(String a, String b) {
        try {
            int x = Integer.parseInt(a) / Integer.parseInt(b);
            System.out.println(x);
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Exceptie: " + e);;
        }
    }

    public static class InvalidGradeException extends Exception {
        public void validateGrade(int grade) throws IllegalArgumentException {
            if (grade < 1 | grade > 10)
                throw new IllegalArgumentException("Miau miau");
        }
    }

    public static void main(String[] args) {

        // readInteger();
//        parseAndDivide("9", "0");
//        parseAndDivide("9", "asd");

        try {
            InvalidGradeException i = new InvalidGradeException();
            i.validateGrade(-2);
            System.out.println("Bravo");
        } catch (IllegalArgumentException e) {
            System.out.println("Offffffffffffff " + e);;
        }
    }
}
