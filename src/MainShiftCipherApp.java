import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MainShiftCipherApp {
    public static void main(String[] args) {
        MainShiftCipherApp shiftCipher = new MainShiftCipherApp();
        shiftCipher.runMenu();
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Encrypt plaintext");
            System.out.println("2. Decrypt ciphertext with known key");
            System.out.println("3. Decrypt ciphertext with exhaustive key search");
            System.out.println("4. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    encryptPlaintext();
                    break;
                case "2":
                    decryptKnownKey();
                    break;
                case "3":
                    decryptExhaustiveKeySearch();
                    break;
                case "4":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }
        }

        scanner.close();
    }

    public void encryptPlaintext() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the plaintext: ");
            String plaintext = scanner.nextLine();

            System.out.print("Enter the shift key: ");
            int key = scanner.nextInt();
            scanner.nextLine();

            String ciphertext = encrypt(plaintext, key);

            System.out.println("Plaintext: " + plaintext);
            System.out.println("Shift Key: " + key);
            System.out.println("Ciphertext: " + ciphertext);
        }
    }

    public void decryptKnownKey() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ciphertext.txt"));
            String line = reader.readLine();

            int key = 3;
            String plaintext = decrypt(line, key);

            System.out.println("Decrypted plaintext: " + plaintext);
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading ciphertext file: " + e.getMessage());
        }
    }

    public void decryptExhaustiveKeySearch() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ciphertext.txt"));
            String line = reader.readLine();

            for (int key = 0; key < 26; key++) {
                String plaintext = decrypt(line, key);
                if (plaintext.contains("DONE")) {
                    System.out.println("Discovered key: " + key);
                    System.out.println("Decrypted plaintext: " + plaintext);
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading ciphertext file: " + e.getMessage());
        }
    }

    public String encrypt(String plaintext, int key) {
        StringBuilder ciphertext = new StringBuilder();

        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char encryptedChar = (char) (((c - base + key) % 26) + base);
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(c);
            }
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char decryptedChar = (char) (((c - base - key + 26) % 26) + base);
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(c);
            }
        }

        return plaintext.toString();
    }
}
