public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }     // constructor takes a WordNet object
    public String outcast(String[] nouns) {
        int maxDistSum = 0;
        String result = "";

        for (String noun : nouns) {
            int sum = 0;
            for (String comparator : nouns) {
                if (noun.equals(comparator)) continue;
                sum += wordNet.distance(noun, comparator);
            }
            if (maxDistSum < sum) {
                result = noun;
                maxDistSum = sum;
            }
        }

        return result;
    }  // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        Outcast outcast = new Outcast(new WordNet("synsets.txt", "hypernyms.txt"));
        System.out.println(outcast.outcast(new String[]{"a", "d"}));
    } // see test client below
}
