import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String s = "";
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
           if (StdRandom.bernoulli((double) 1/++i)) {
               s = temp;
           }
        }
        if (i > 0) {
            System.out.print(s);
        } else {
            int champion = 0;
            String[] var1 = "heads tails".split(" ");
            System.out.print("heads tails\n");
            if (StdRandom.bernoulli((double) 1/2)) {
                champion = 1;
            }
            System.out.print(var1[champion]);
        }
    }
}
