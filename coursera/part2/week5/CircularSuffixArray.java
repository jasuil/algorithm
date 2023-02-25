import java.util.ArrayList;

public class CircularSuffixArray {

    private final char[] word;
    private final ArrayList<CArray> cArrayArrayList;
    private class CArray implements Comparable<CArray> {

        private final int index;
        CArray(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(CArray other) {
            if (other.index == index) return 0;
            int thisIndex = index;
            int thatIndex = other.index;
            for (int i = 0; i < word.length; i++) {
                if (thisIndex >= word.length) thisIndex = 0;
                if (thatIndex >= word.length) thatIndex = 0;
                if (word[thisIndex] == word[thatIndex]) {
                    thisIndex++;
                    thatIndex++;
                    continue;
                } else {
                    return word[thisIndex] > word[thatIndex] ? 1 : -1;
                }
            }

            return index > other.index ? -1 : 1;
        }
    }


    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        cArrayArrayList = new ArrayList<>();
        word = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            word[i] = s.charAt(i);
            CArray cArray = new CArray(i);
            cArrayArrayList.add(cArray);
        }

        cArrayArrayList.sort(CArray::compareTo);
    }

    // length of s
    public int length() {
        return word.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= word.length) throw new IllegalArgumentException();
        return cArrayArrayList.get(i).index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("BANANA");
        circularSuffixArray.index(0);
    }

}