import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static char[] sequential;
    private static final int EXTENDED_ASCII_SIZE = 257;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        sequential = new char[256];
        for (int i = 0; i < 256; i++) {
            sequential[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char c =  BinaryStdIn.readChar();

            char temp = sequential[0];
            char nextTemp = 0;
            for (int i = 0; i < sequential.length; i++) {
                nextTemp = sequential[i];
                sequential[i] = temp;
                temp = nextTemp;
                if (c == nextTemp) {
                    BinaryStdOut.write((char) i);
                    break;
                }
            }
            sequential[0] = nextTemp;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

        sequential = new char[EXTENDED_ASCII_SIZE];
        for (int i = 0; i < EXTENDED_ASCII_SIZE; i++) {
            sequential[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char c =  BinaryStdIn.readChar();

            char temp = sequential[0];
            char nextTemp = 0;
            for (int i = 0; i < sequential.length; i++) {
                nextTemp = sequential[i];
                sequential[i] = temp;
                temp = nextTemp;
                if ((int) c == i) {
                    BinaryStdOut.write(nextTemp);
                    break;
                }
            }
            sequential[0] = nextTemp;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else {
            decode();
        }
    }

}