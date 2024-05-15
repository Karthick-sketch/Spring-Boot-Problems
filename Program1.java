import java.util.Scanner;

public class Program1 {
    public static String convert(String input) {
        //your implementation here
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                output.append((char) (c + 32));
            } else if (c >= 'a' && c <= 'z') {
                output.append((char) (c - 32));
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Please enter the number of inputs you want to try: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            for(int i = 0; i < n; i++) {
                String input = scanner.nextLine();
                System.out.println("Output: " + convert(input));
            }
        }
    }
}