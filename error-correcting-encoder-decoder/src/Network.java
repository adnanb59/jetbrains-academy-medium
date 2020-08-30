import java.io.*;
import java.util.*;

public class Network {
    /**
    * Encode data from <inFile> and write it to <outFile>, output relevant information while processing 
    * 
    * @param inFile     File to read from
    * @param outFile    File to write to
    * @return           Status of encoding
    */
    public static boolean encode (String inFile, String outFile) {
        if (!inFile.endsWith(".txt") || !outFile.endsWith(".txt")) return false; // Quick error check to make sure correct file types are passed in
        boolean success = true;

        try (FileReader fr = new FileReader(inFile); FileOutputStream fos = new FileOutputStream(outFile)) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> encoding = new ArrayList<>();

            System.out.println(inFile + ":");
            System.out.print("text view: ");
            
            int c;
            short bit_written = 0, bit_to_set = 5;
            int curr_byte = 0;
            // Read bytes till finished
            while ((c = fr.read()) != -1) {
                // Prep display for user
                System.out.print((char) c);
                String binary = Integer.toBinaryString(c);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));
                hex.append(String.format(" %x", c));
                // Go through bits
                for (int i = 7; i >= 0; i--) {
                    // If bit is set, set <bit_to_set> bit for encoded byte
                    // Per Hamming Code, you set bits 5, 3, 2 & 1 
                    if ((c & (1 << i)) != 0) {
                        curr_byte |= 1 << bit_to_set;
                        // Rather than check in the end for specific bits,
                        // flip parity bits as you go.
                        // 192 sets bits 1, 2; 144 - 1, 4; 80 - 2, 4; 208 - 1, 2 & 4
                        curr_byte ^= bit_to_set == 5 ? 192 :
                                    bit_to_set == 3 ? 144 :
                                    bit_to_set == 2 ? 80 : 208;
                    }
                    bit_written++;
                    bit_to_set -= bit_written == 1 ? 2 : 1;
                    // Once encoded byte gets created add to byte list and reset counter values
                    if (bit_written == 4) {
                        encoding.add(curr_byte);
                        bit_written = 0;
                        curr_byte = 0;
                        bit_to_set = 5;
                    }
                }
            }
            
            System.out.println();
            System.out.println("\nhex view:" + hex.toString());
            System.out.println("bin view:" + bin.toString());
            // Reset StringBuilders for later use
            hex.setLength(0);
            bin.setLength(0);
            StringBuilder expand = new StringBuilder();

            // Prep encoded bytes to be written to output file
            for (int b : encoding) {
                String binary = Integer.toBinaryString(b);
                while (binary.length() != 8) binary = "0" + binary;
                hex.append(String.format(" %x", b));
                expand.append(String.format(" ..%d.%d%d%d.", (b >>> 5) & 1, (b >>> 3) & 1, (b >>> 2) & 1, (b >>> 1) & 1));
                bin.append(String.format(" %s", binary));
                fos.write(b);
            }

            System.out.println("\n" + outFile + ":\nexpand:" + expand.toString());
            System.out.println("parity:" + bin.toString());
            System.out.println("hex view:" + hex.toString());
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
    * Send data from <inFile> to <outFile>.
    * While sending data, simulate a network error by changing one bit per byte and send the manipulated data to be written.
    * 
    * @param inFile     File to read from
    * @param outFile    File to write to
    * @return           status of sending data
    */
    public static boolean send (String inFile, String outFile) {
        if (!inFile.endsWith(".txt") || !outFile.endsWith(".txt")) return false; // Quick error check to make sure correct file types are passed in
        boolean success = true;

        try (FileInputStream fr = new FileInputStream(inFile); FileOutputStream fos = new FileOutputStream(outFile)) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> bytes = new ArrayList<>();
            int c;
            // Read bytes from <inFile>
            while ((c = fr.read()) != -1) {
                hex.append(String.format(" %x", c));
                String binary = Integer.toBinaryString(c);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));

                // Calculate random position of bit to flip and flip the bit in the given byte
                // The writing to the output happens earlier than expected (here), no need to write it as the output is being printed
                int bit_to_flip = (int) (Math.random() * 7);
                fos.write(c ^ (1 << bit_to_flip));
                bytes.add(c ^ (1 << bit_to_flip));
            }
            
            System.out.println();
            System.out.println(inFile + ":");
            System.out.println("hex view:" + hex.toString());
            System.out.println("bin view:" + bin.toString());

            hex.setLength(0);
            bin.setLength(0);

            // Go through manipulated bytes and display information to user
            for (int b : bytes) {
                hex.append(String.format(" %x", b));
                String binary = Integer.toBinaryString(b);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));
            }

            System.out.println("\n" + outFile + ":");
            System.out.println("bin view:" + bin.toString());
            System.out.println("hex view:" + hex.toString());
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
    * Decode data from <inFile>, fix errors caused during sending process and write fixed data to <outFile>.
    * Data gets fixed using the (7, 4)-Hamming code
    * 
    * @param inFile     File to read from
    * @param outFile    File to write to
    * @return           status of decoding process
    */
    public static boolean decode (String inFile, String outFile) {
        if (!inFile.endsWith(".txt") || !outFile.endsWith(".txt")) return false; // Quick error check to make sure correct file types are passed in
        boolean success = true;

        try (FileInputStream fr = new FileInputStream(inFile); FileOutputStream fos = new FileOutputStream(outFile)) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> bytes = new ArrayList<>();
            int c;
            // Go through bytes in <inFile> and fix errors
            while ((c = fr.read()) != -1) {
                hex.append(String.format(" %x", c));
                String number = Integer.toBinaryString(c);
                while (number.length() < 8) number = "0" + number;
                bin.append(" ").append(number);
                int problem_bit = 0;
                // Find error bit:
                // Check parity bits (and their associated data bits)
                // If the sum is not even then add parity bit position to <problem_bit>
                if (((c >>> 7 & 1) + (c >>> 5 & 1) + (c >>> 3 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 1;
                if (((c >>> 6 & 1) + (c >>> 5 & 1) + (c >>> 2 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 2;
                if (((c >>> 4 & 1) + (c >>> 3 & 1) + (c >>> 2 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 4;
                bytes.add(c ^ (1 << (8 - problem_bit))); // Fix the problem bit by flipping it
            }
            System.out.println(inFile + ":");
            System.out.println("hex view:" + hex.toString());
            System.out.println("bin view:" + bin.toString());

            hex.setLength(0);
            bin.setLength(0);
            StringBuilder initial = new StringBuilder(), text = new StringBuilder();
            int curr = 0;
            byte bits_written = 0;

            // Go through fixed bytes and decode them
            for (int b : bytes) {
                String binary = Integer.toBinaryString(b);
                while (binary.length() < 8) binary = "0" + binary;
                initial.append(" ").append(binary);

                // Per each byte, read the data bits to construct original byte
                byte bit_to_read = 5;
                while (bit_to_read >= 1) {
                    curr <<= 1;
                    curr += (b >>> bit_to_read) & 1;
                    bits_written++;
                    bit_to_read -= bit_to_read == 5 ? 2 : 1;
                }

                // Once enough bits (8) have been read, you have the original byte, write it to <outFile> and display info to user
                if (bits_written == 8) {
                    binary = Integer.toBinaryString(curr);
                    while (binary.length() < 8) binary = "0" + binary;
                    bin.append(" ").append(binary);
                    hex.append(String.format(" %x", curr));
                    text.append((char) curr);
                    fos.write(curr);
                    curr = 0;
                    bits_written = 0;
                }
            }

            System.out.println("\n" + outFile + ":");
            System.out.println("correct:" + initial.toString());
            System.out.println("decode: " + bin.toString());
            System.out.println("hex view:" + hex.toString());
            System.out.println("text view: " + text.toString());
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }
}