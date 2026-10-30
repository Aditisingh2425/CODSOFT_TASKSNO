import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CODSOFT TASK 2 - STUDENT GRADE CALCULATOR
 * Accepts marks, calculates total and average, and displays grade and result.
 */
public class StudentGradeCalculator {
    private static final Scanner sc = new Scanner(System.in);

    private static class Subject {
        String name;
        double marks;

        Subject(String name, double marks) {
            this.name = name;
            this.marks = marks;
        }
    }

    public static void main(String[] args) {
        System.out.println("======= STUDENT GRADE CALCULATOR =======");

        System.out.print("Enter student name: ");
        String studentName = readNonEmpty();

        int count = readInt("Enter number of subjects (1-15): ", 1, 15);
        List<Subject> subjects = new ArrayList<>();
        double total = 0;

        for (int i = 1; i <= count; i++) {
            System.out.print("Enter subject " + i + " name: ");
            String subjectName = readNonEmpty();
            double marks = readMarks("Enter marks out of 100: ");
            subjects.add(new Subject(subjectName, marks));
            total += marks;
        }

        double average = total / count;
        String grade = getGrade(average);
        long passed = subjects.stream().filter(subject -> subject.marks >= 33).count();

        System.out.println("\n=============== REPORT CARD ===============");
        System.out.println("Student name: " + studentName);
        System.out.println("-------------------------------------------");
        System.out.printf("%-25s %10s%n", "SUBJECT", "MARKS");
        for (Subject subject : subjects) {
            System.out.printf("%-25s %10.2f%n", subject.name, subject.marks);
        }
        System.out.println("-------------------------------------------");
        System.out.printf("Total marks: %.2f / %.2f%n", total, count * 100.0);
        System.out.printf("Average percentage: %.2f%%%n", average);
        System.out.println("Grade: " + grade);
        System.out.println("Subjects passed: " + passed + "/" + count);
        System.out.println("Final result: " + (passed == count ? "PASS" : "FAIL"));
        System.out.println("===========================================");
    }

    private static String getGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        if (percentage >= 33) return "E";
        return "F";
    }

    private static String readNonEmpty() {
        while (true) {
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.print("Value cannot be empty. Enter again: ");
        }
    }

    private static int readInt(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.printf("Enter a whole number from %d to %d.%n", min, max);
        }
    }

    private static double readMarks(String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                if (Double.isFinite(value) && value >= 0 && value <= 100) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter valid marks between 0 and 100.");
        }
    }
}
