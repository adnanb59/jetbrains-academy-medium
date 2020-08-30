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
        // Have user choose whether to encode, send or decode data
        while (m == null) {
            System.out.print("Enter a mode: ");
            m = Mode.findMode(in.nextLine().trim());
        }
        in.close();
        System.out.println();

        // Specify which file to read from and which file to write to
        // after selecting a mode
        String inFile, outFile;
        switch (m) {
            case SEND:
                System.out.print("Enter file to read: ");
                inFile = in.nextLine().trim();
                System.out.print("Enter file to write: ");
                outFile = in.nextLine().trim();
                Network.send(inFile, outFile);
                break;
            case DECODE:
                System.out.print("Enter file to read: ");
                inFile = in.nextLine().trim();
                System.out.print("Enter file to write: ");
                outFile = in.nextLine().trim();
                Network.decode(inFile, outFile);
                break;
            case ENCODE:
                System.out.print("Enter file to read: ");
                inFile = in.nextLine().trim();
                System.out.print("Enter file to write: ");
                outFile = in.nextLine().trim();
                Network.encode(inFile, outFile);
                break;
        }
    }
}