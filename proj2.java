import java.io.*;
import java.util.Scanner;

public class proj2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("            **************");
        System.out.println("*  Welcome to the Student Database   *");
        System.out.println("*        Information Tracker         *");
        System.out.println("            **************");

        // Get the number of students as user input
        System.out.print("\nEnter the number of students: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Initialize arrays to store student names, ages, contact numbers, blood groups, addresses, and grades
        String[] studentNames = new String[numStudents];
        int[] studentAges = new int[numStudents];
        String[] contactNumbers = new String[numStudents];
        String[] bloodGroups = new String[numStudents];
        String[] addresses = new String[numStudents];
        int[][] studentGrades = new int[numStudents][6]; // Fixed 6 subjects per student

        // Fixed subjects
        String[] subjects = {
                "Programming Fundamentals",
                "Ideology and Constitution of Pakistan",
                "Islamic Studies",
                "Functional English",
                "Application of ICT",
                "Linear Algebra"
        };

        // Check if a file exists for student data
        File file = new File("student_data.txt");
        if (file.exists()) {
            loadStudentData(file, studentNames, studentAges, contactNumbers, bloodGroups, addresses, studentGrades);
            System.out.println("\nStudent data loaded from file.");
        } else {
            // Input student information
            for (int i = 0; i < studentNames.length; i++) {
                System.out.print("\nEnter student name #" + (i + 1) + ": ");
                studentNames[i] = scanner.nextLine();

                System.out.print("Enter student age for " + studentNames[i] + ": ");
                studentAges[i] = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.print("Enter contact number for " + studentNames[i] + ": ");
                contactNumbers[i] = scanner.nextLine();

                System.out.print("Enter blood group for " + studentNames[i] + ": ");
                bloodGroups[i] = scanner.nextLine();

                System.out.print("Enter address for " + studentNames[i] + ": ");
                addresses[i] = scanner.nextLine();
            }

            // Input grades for each student
            for (int i = 0; i < studentNames.length; i++) {
                System.out.println("\nEnter grades for " + studentNames[i] + ":");
                for (int j = 0; j < studentGrades[i].length; j++) {
                    System.out.print("Enter grade for " + subjects[j] + " (out of 100): ");
                    int grade = scanner.nextInt();
                    studentGrades[i][j] = grade;
                }
                scanner.nextLine(); // Consume the newline character
            }

            // Save student data to file
            saveStudentData(file, studentNames, studentAges, contactNumbers, bloodGroups, addresses, studentGrades);
            System.out.println("\nStudent data saved to file.");
        }

        // Menu loop
        int choice;
        do {
            printMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    displayStudentInformation(studentNames, studentAges, contactNumbers, bloodGroups, addresses, subjects, scanner);
                    break;
                case 2:
                    updateStudentInformation(scanner, studentNames, studentAges, contactNumbers, bloodGroups, addresses, subjects);
                    break;
                case 3:
                    displayAverageGradesAndGPA(studentNames, studentGrades, subjects, scanner);
                    break;
                case 4:
                    displayGrades(studentNames, studentGrades, subjects, scanner);
                    break;
                case 5:
                    System.out.println("\nExiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter a valid option.");
            }

        } while (choice != 5);

        // Close the scanner
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n***********");
        System.out.println("*           Menu            *");
        System.out.println("***********");
        System.out.println("1. Display Student Information");
        System.out.println("2. Update Student Information");
        System.out.println("3. Display GPA");
        System.out.println("4. Display Grades");
        System.out.println("5. Exit");
    }

    private static void displayStudentInformation(String[] names, int[] ages, String[] contactNumbers, String[] bloodGroups, String[] addresses, String[] subjects, Scanner scanner) {
        System.out.print("\nEnter the student name: ");
        String studentName = scanner.nextLine();

        int studentIndex = findStudentIndex(names, studentName);

        if (studentIndex != -1) {
            System.out.println("\nStudent Information for " + studentName + ":");
            System.out.println("Age: " + ages[studentIndex]);
            System.out.println("Contact Number: " + contactNumbers[studentIndex]);
            System.out.println("Blood Group: " + bloodGroups[studentIndex]);
            System.out.println("Address: " + addresses[studentIndex]);
        } else {
            System.out.println("\nStudent not found. Please check the name.");
        }
    }

    private static void updateStudentInformation(Scanner scanner, String[] names, int[] ages, String[] contactNumbers, String[] bloodGroups, String[] addresses, String[] subjects) {
        System.out.print("\nEnter the student name: ");
        String studentName = scanner.nextLine();

        int studentIndex = findStudentIndex(names, studentName);

        if (studentIndex != -1) {
            System.out.println("\nEnter new information for " + studentName + ":");

            System.out.print("Enter new age for " + studentName + ": ");
            ages[studentIndex] = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter new contact number for " + studentName + ": ");
            contactNumbers[studentIndex] = scanner.nextLine();

            System.out.print("Enter new blood group for " + studentName + ": ");
            bloodGroups[studentIndex] = scanner.nextLine();

            System.out.print("Enter new address for " + studentName + ": ");
            addresses[studentIndex] = scanner.nextLine();

            System.out.println("\nStudent information updated successfully.");
        } else {
            System.out.println("\nStudent not found. Please check the name.");
        }
    }
    // Add this method to calculate GPA based on the average
    private static double calculateGPA(double average) {
        if (average >= 85) {
            return 4.0;
        } else if (average >= 71) {
            return 3.0;
        } else if (average >= 60) {
            return 2.0;
        } else if (average >= 50) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private static void displayAverageGradesAndGPA(String[] names, int[][] grades, String[] subjects, Scanner scanner) {
        System.out.print("\nEnter the student name: ");
        String studentName = scanner.nextLine();

        int studentIndex = findStudentIndex(names, studentName);

        if (studentIndex != -1) {
            double average = calculateAverage(grades[studentIndex]);
            double gpa = calculateGPA(average);

            System.out.println("\nAverage Grades for " + studentName + ": " + average);
            System.out.println("GPA for " + studentName + ": " + gpa);
        } else {
            System.out.println("\nStudent not found. Please check the name.");
        }
    }


    private static void displayGrades(String[] names, int[][] grades, String[] subjects, Scanner scanner) {
        System.out.print("\nEnter the student name: ");
        String studentName = scanner.nextLine();

        int studentIndex = findStudentIndex(names, studentName);

        if (studentIndex != -1) {
            System.out.println("\nGrades for " + studentName + ":");
            for (int j = 0; j < grades[studentIndex].length; j++) {
                String subject = subjects[j];
                int grade = grades[studentIndex][j];
                String letterGrade = getLetterGrade(grade);

                System.out.println(subject + ": " + grade + " (Grade: " + letterGrade + ")");
            }
        } else {
            System.out.println("\nStudent not found. Please check the name.");
        }
    }

    private static String getLetterGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    private static int findStudentIndex(String[] names, String studentName) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(studentName)) {
                return i;
            }
        }
        return -1;
    }

    private static void saveStudentData(File file, String[] names, int[] ages, String[] contactNumbers, String[] bloodGroups, String[] addresses, int[][] grades) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < names.length; i++) {
                writer.println(names[i]);
                writer.println(ages[i]);
                writer.println(contactNumbers[i]);
                writer.println(bloodGroups[i]);
                writer.println(addresses[i]);

                for (int j = 0; j < grades[i].length; j++) {
                    writer.print(grades[i][j] + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadStudentData(File file, String[] names, int[] ages, String[] contactNumbers, String[] bloodGroups, String[] addresses, int[][] grades) {
        try (Scanner scanner = new Scanner(file)) {
            for (int i = 0; i < names.length; i++) {
                if (scanner.hasNextLine()) {
                    names[i] = scanner.nextLine();
                }

                if (scanner.hasNextInt()) {
                    ages[i] = scanner.nextInt();
                }
                scanner.nextLine(); // Consume the newline character

                if (scanner.hasNextLine()) {
                    contactNumbers[i] = scanner.nextLine();
                }

                if (scanner.hasNextLine()) {
                    bloodGroups[i] = scanner.nextLine();
                }

                if (scanner.hasNextLine()) {
                    addresses[i] = scanner.nextLine();
                }

                if (scanner.hasNextLine()) {
                    String[] gradeTokens = scanner.nextLine().split(" ");

                    for (int j = 0; j < Math.min(grades[i].length, gradeTokens.length); j++) {
                        grades[i][j] = Integer.parseInt(gradeTokens[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double calculateAverage(int[] grades) {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.length;
    }
}