import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String word = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(word);
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int index = circularSuffixArray.index(i) - 1;
            if (index < 0) index = word.length()-1;
            BinaryStdOut.write(word.charAt(index));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int startIndex = BinaryStdIn.readInt();
        String word = BinaryStdIn.readString();
        int[] asciiExtendCount = new int[257];

        // add count, but do not just now, do it later, but next index why? see below inverse mapper
        // 실제 먼저 제 위치에 더하지 않고 다음 칸부터 넣는다. 이유는 아래 3번째 for 문에서 next index 때문이다.
        for (int i = 0; i < word.length(); i++) {
            asciiExtendCount[word.charAt(i)+1]++;
        }

        // add index to count, this is useful in next array
        for (int i = 1; i < asciiExtendCount.length; i++) {
            asciiExtendCount[i] += asciiExtendCount[i-1];
        }

        // inverse mapper
        int[] next = new int[word.length()];
        for (int i = 0; i < word.length(); i++) {
            char w = word.charAt(i); // for abcaaa, a is 4 in this is how to arrange
            next[asciiExtendCount[w]] = i;
            asciiExtendCount[w]++; // now add count right index
        }

        for (int i = 0; i < next.length; i++) {
            BinaryStdOut.write(word.charAt(next[startIndex]));
            startIndex = next[startIndex];
        }

        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else {
            inverseTransform();
        }
    }

}