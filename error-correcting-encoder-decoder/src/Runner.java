package correcter;

import java.io.*;
import java.util.*;

enum Mode {
    ENCODE("encode"), DECODE("decode"), SEND("send");

    private final String value;

    Mode (String s) {
        this.value = s;
    }

    public static Mode findMode(String s) {
        switch(s) {
            case "encode":
                return Mode.ENCODE;
            case "decode":
                return Mode.DECODE;
            case "send":
                return Mode.SEND;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Mode m = null;
        while (m == null) {
            System.out.print("Enter a mode: ");
            m = Mode.findMode(in.nextLine().trim());
        }
        in.close();
        System.out.println();

        switch (m) {
            case SEND:
                send();
                break;
            case DECODE:
                decode();
                break;
            case ENCODE:
                encode();
                break;
        }
    }

    private static void encode() {
        try (FileReader fr = new FileReader("send.txt"); FileOutputStream fos = new FileOutputStream("encoded.txt")) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> encoding = new ArrayList<>();
            System.out.println("send.txt:");
            System.out.print("text view: ");
            int c;
            short bit_written = 0, bit_to_set = 5;
            int curr_byte = 0;
            // Read bits till finished
            while ((c = fr.read()) != -1) {
                // Prep display for user
                System.out.print((char) c);
                String binary = Integer.toBinaryString(c);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));
                hex.append(String.format(" %x", c));

                for (int i = 7; i >= 0; i--) {
                    if ((c & (1 << i)) != 0) {
                        curr_byte |= 1 << bit_to_set;
                        curr_byte ^= bit_to_set == 5 ? 192 :
                                bit_to_set == 3 ? 144 :
                                        bit_to_set == 2 ? 80 : 208;
                        //curr_byte ^= bit_to_set == 2 ? bit_to_set + 1 : bit_to_set + 5;
                    }
                    bit_written++;
                    bit_to_set -= bit_written == 1 ? 2 : 1;
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

            hex.setLength(0);
            bin.setLength(0);
            StringBuilder expand = new StringBuilder();
            for (int b : encoding) {
                String binary = Integer.toBinaryString(b);
                while (binary.length() != 8) binary = "0" + binary;
                hex.append(String.format(" %x", b));
                expand.append(String.format(" ..%d.%d%d%d.", (b >>> 5) & 1, (b >>> 3) & 1, (b >>> 2) & 1, (b >>> 1) & 1));
                bin.append(String.format(" %s", binary));
                fos.write(b);
            }
            System.out.println("\nencoded.txt:\nexpand:" + expand.toString());
            System.out.println("parity:" + bin.toString());
            System.out.println("hex view:" + hex.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void send() {
        try (FileInputStream fr = new FileInputStream("encoded.txt"); FileOutputStream fos = new FileOutputStream("received.txt")) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> bytes = new ArrayList<>();
            int c;
            while ((c = fr.read()) != -1) {
                hex.append(String.format(" %x", c));
                String binary = Integer.toBinaryString(c);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));
                int bit_to_flip = (int) (Math.random() * 7);
                fos.write(c ^ (1 << bit_to_flip));
                bytes.add(c ^ (1 << bit_to_flip));
            }
            System.out.println();
            System.out.println("encoded.txt:");
            System.out.println("hex view:" + hex.toString());
            System.out.println("bin view:" + bin.toString());

            hex.setLength(0);
            bin.setLength(0);

            for (int b : bytes) {
                hex.append(String.format(" %x", b));
                String binary = Integer.toBinaryString(b);
                while (binary.length() != 8) binary = "0" + binary;
                bin.append(String.format(" %s", binary));
            }
            System.out.println("\nreceived.txt");
            System.out.println("bin view:" + bin.toString());
            System.out.println("hex view:" + hex.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decode() {
        try (FileInputStream fr = new FileInputStream("received.txt"); FileOutputStream fos = new FileOutputStream("decoded.txt")) {
            StringBuilder hex = new StringBuilder(), bin = new StringBuilder();
            ArrayList<Integer> bytes = new ArrayList<>();
            int c;
            while ((c = fr.read()) != -1) {
                hex.append(String.format(" %x", c));
                String number = Integer.toBinaryString(c);
                while (number.length() < 8) number = "0" + number;
                bin.append(" ").append(number);
                int problem_bit = 0;
                if (((c >>> 7 & 1) + (c >>> 5 & 1) + (c >>> 3 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 1;
                if (((c >>> 6 & 1) + (c >>> 5 & 1) + (c >>> 2 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 2;
                if (((c >>> 4 & 1) + (c >>> 3 & 1) + (c >>> 2 & 1) + (c >>> 1 & 1)) % 2 != 0) problem_bit += 4;
                bytes.add(c ^ (1 << (8 - problem_bit)));
            }
            System.out.println("received.txt:");
            System.out.println("hex view:" + hex.toString());
            System.out.println("bin view:" + bin.toString());

            hex.setLength(0);
            bin.setLength(0);
            StringBuilder initial = new StringBuilder(),
                    text = new StringBuilder();
            int curr = 0;
            byte bits_written = 0;
            for (int b : bytes) {
                String binary = Integer.toBinaryString(b);
                while (binary.length() < 8) binary = "0" + binary;
                initial.append(" ").append(binary);

                byte bit_to_read = 5;
                while (bit_to_read >= 1) {
                    curr <<= 1;
                    curr += (b >>> bit_to_read) & 1;
                    bits_written++;
                    bit_to_read -= bit_to_read == 5 ? 2 : 1;
                }
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
            System.out.println("\ndecoded.txt");
            System.out.println("correct:" + initial.toString());
            System.out.println("decode: " + bin.toString());
            System.out.println("hex view:" + hex.toString());
            System.out.println("text view: " + text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}