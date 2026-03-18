import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class FileLocker {

    private static final int ENCRYPTION_KEY = 100;
    private static final int BUFFER_SIZE = 1024;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            clearScreen();
            displayMenu();
            System.out.print("\nEnter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> encryptFile();
                case 2 -> decryptFile();
                case 3 -> {
                    System.out.println("\n\nExiting File Locker...");
                    System.out.println("Thank you for using File Locker!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("\n\nInvalid choice! Please try again.");
                    pause();
                }
            }
        }
    }

    static void displayMenu() {
        System.out.println("\n+----------------------------------------+");
        System.out.println("|        FILE LOCKER SYSTEM              |");
        System.out.println("+----------------------------------------+");
        System.out.println("|  1. Lock/Encrypt a File                |");
        System.out.println("|  2. Unlock/Decrypt a File              |");
        System.out.println("|  3. Exit                               |");
        System.out.println("+----------------------------------------+");
        System.out.println("| Supports: TXT, PDF, DOCX, Images,     |");
        System.out.println("| Videos, ZIP, EXE, and ALL file types   |");
        System.out.println("+----------------------------------------+");
    }

    static void clearScreen() {
        // Cross-platform clear screen
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print blank lines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    static void encryptFile() {
        clearScreen();
        System.out.println("\n+----------------------------------------+");
        System.out.println("|        ENCRYPT FILE                    |");
        System.out.println("+----------------------------------------+");

        System.out.print("\nEnter the file path to encrypt (with extension): ");
        String filename = scanner.nextLine().trim();

        File sourceFile = new File(filename);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.out.printf("\n Error: File '%s' not found or cannot be opened!%n", filename);
            System.out.println(" Make sure the file is in the same directory.");
            pause();
            return;
        }

        System.out.printf("\n File found! Size: %d bytes%n", sourceFile.length());

        System.out.println("\n+----------------------------------------+");
        System.out.println("| SET YOUR PASSWORD TO LOCK THIS FILE    |");
        System.out.println("+----------------------------------------+");

        System.out.print("\nEnter your password (max 50 characters): ");
        String password = scanner.nextLine().trim();

        if (password.length() > 50) {
            System.out.println("\n Error: Password exceeds 50 characters!");
            pause();
            return;
        }

        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.nextLine().trim();

        if (!password.equals(confirmPassword)) {
            System.out.println("\n Error: Passwords do not match!");
            pause();
            return;
        }

        System.out.println("\n Password set successfully!");

        File tempFile = new File("temp_encrypt.tmp");

        try (
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream tempOut = new FileOutputStream(tempFile)
        ) {
            // Write password length and password bytes
            DataOutputStream dos = new DataOutputStream(tempOut);
            byte[] passwordBytes = password.getBytes("UTF-8");
            dos.writeInt(passwordBytes.length);
            dos.write(passwordBytes);

            // Encrypt and write file content
            System.out.println("\n Encrypting file...");
            System.out.print(" Progress: ");

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int chunkCount = 0;
            while ((bytesRead = fis.read(buffer)) > 0) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer[i] = (byte) (buffer[i] + ENCRYPTION_KEY);
                }
                tempOut.write(buffer, 0, bytesRead);
                chunkCount++;
                if (chunkCount % 10 == 0) {
                    System.out.print("#");
                }
            }
            if (chunkCount % 10 != 0) System.out.print("#"); // always show at least final #

        } catch (IOException e) {
            System.out.println("\n Error during encryption: " + e.getMessage());
            tempFile.delete();
            pause();
            return;
        }

        // Overwrite original file with encrypted content
        try {
            Files.copy(tempFile.toPath(), sourceFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("\n Error overwriting original file: " + e.getMessage());
            tempFile.delete();
            pause();
            return;
        }

        tempFile.delete();

        System.out.printf("%n%n File '%s' has been successfully encrypted!%n", filename);
        System.out.println(" Your file is now secure and locked.");
        System.out.println(" Remember your password to decrypt this file!");
        pause();
    }

    static void decryptFile() {
        clearScreen();
        System.out.println("\n+----------------------------------------+");
        System.out.println("|        DECRYPT FILE                    |");
        System.out.println("+----------------------------------------+");

        System.out.print("\nEnter the file path to decrypt (with extension): ");
        String filename = scanner.nextLine().trim();

        File sourceFile = new File(filename);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.out.printf("\n Error: File '%s' not found or cannot be opened!%n", filename);
            System.out.println(" Make sure the file is in the same directory.");
            pause();
            return;
        }

        System.out.printf("\n File found! Size: %d bytes%n", sourceFile.length());

        String storedPassword;
        long dataStartOffset;

        // Read stored password from the file header
        try (FileInputStream fis = new FileInputStream(sourceFile);
             DataInputStream dis = new DataInputStream(fis)) {

            int passwordLength = dis.readInt();
            byte[] passwordBytes = new byte[passwordLength];
            dis.readFully(passwordBytes);
            storedPassword = new String(passwordBytes, "UTF-8");

            // Header size: 4 bytes (int) + passwordLength bytes
            dataStartOffset = 4 + passwordLength;

        } catch (IOException e) {
            System.out.println("\n Error reading file header. File may not be encrypted by this tool.");
            pause();
            return;
        }

        System.out.print("\nEnter password to unlock this file: ");
        String password = scanner.nextLine().trim();

        if (!password.equals(storedPassword)) {
            System.out.println("\n Incorrect password! Access denied.");
            pause();
            return;
        }

        System.out.println("\n Password verified!");

        File tempFile = new File("temp_decrypt.tmp");

        try (
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream tempOut = new FileOutputStream(tempFile)
        ) {
            // Skip the header (password length int + password bytes)
            long skipped = fis.skip(dataStartOffset);
            if (skipped != dataStartOffset) {
                throw new IOException("Could not skip file header.");
            }

            System.out.println("\n Decrypting file...");
            System.out.print(" Progress: ");

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int chunkCount = 0;
            while ((bytesRead = fis.read(buffer)) > 0) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer[i] = (byte) (buffer[i] - ENCRYPTION_KEY);
                }
                tempOut.write(buffer, 0, bytesRead);
                chunkCount++;
                if (chunkCount % 10 == 0) {
                    System.out.print("#");
                }
            }
            if (chunkCount % 10 != 0) System.out.print("#"); // always show at least final #

        } catch (IOException e) {
            System.out.println("\n Error during decryption: " + e.getMessage());
            tempFile.delete();
            pause();
            return;
        }

        // Overwrite original file with decrypted content
        try {
            Files.copy(tempFile.toPath(), sourceFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("\n Error overwriting original file: " + e.getMessage());
            tempFile.delete();
            pause();
            return;
        }

        tempFile.delete();

        System.out.printf("%n%n File '%s' has been successfully decrypted!%n", filename);
        System.out.println(" Your file is now unlocked and readable.");
        System.out.println(" You can now open and use your file normally.");
        pause();
    }
}