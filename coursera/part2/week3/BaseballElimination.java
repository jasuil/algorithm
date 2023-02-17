import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.HashSet;

public class BaseballElimination {

    private final HashMap<String, Integer> teamName;
    private final int[] win;
    private final int[] lose;
    private final int[] remain;
    private final int[][] game;
    private int totalEliminateGameCount;

    public BaseballElimination(String filename) { // create a baseball division from given filename in format specified below
        In in = new In(filename);
        int teamCount = in.readInt();
        teamName = new HashMap<>();
        win = new int[teamCount+1];
        lose = new int[teamCount+1];
        remain = new int[teamCount+1];
        game = new int[teamCount+1][teamCount+1];

        for (int i = 0; i < teamCount; i++) {
            teamName.put(in.readString(), i+1);
            win[i+1] = in.readInt();
            lose[i+1] = in.readInt();
            remain[i+1] = in.readInt();
            for (int i2 = 0; i2 < teamCount; i2++) {
                game[i+1][i2+1] = in.readInt();
            }
        }

        for (int i = 1; i < teamCount-1; i++) {
            totalEliminateGameCount += i;
        }

    }

    public int numberOfTeams() { // number of teams
        return teamName.size();
    }

    public Iterable<String> teams() { // all teams
        LinkedList<String> result = new LinkedList<>();
        for (String name : teamName.keySet()) {
            result.add(name);
        }
        return result;
    }

    public int wins(String team) { // number of wins for given team
        if (teamName.get(team) == null) throw new IllegalArgumentException();
        return win[teamName.get(team)];
    }

    public int losses(String team) { // number of losses for given team
        if (teamName.get(team) == null) throw new IllegalArgumentException();
        return lose[teamName.get(team)];
    }

    public int remaining(String team) { // number of remaining games for given team
        if (teamName.get(team) == null) throw new IllegalArgumentException();
        return remain[teamName.get(team)];
    }

    public int against(String team1, String team2) { // number of remaining games between team1 and team2
        if (teamName.get(team1) == null || teamName.get(team2) == null) throw new IllegalArgumentException();
        return game[teamName.get(team1)][teamName.get(team2)];
    }

    public boolean isEliminated(String team) { // is given team eliminated?

        // with any try, cannot eliminate team exists? https://en.wikipedia.org/wiki/Maximum_flow_problem#Baseball_elimination
        if (teamName.get(team) == null) throw new IllegalArgumentException();
        int teamIndex = teamName.get(team);
        FlowNetwork flowNetwork = new FlowNetwork(totalEliminateGameCount + teamName.size() + 1);
        int teamLeftIndex = 1;
        int gameIndex = teamName.size();
        int teamMaxWins = win[teamIndex] + remain[teamIndex];
        int tIndex = flowNetwork.V() - 1;

        for (; teamLeftIndex <= teamName.size(); teamLeftIndex++) {
            if (teamIndex == teamLeftIndex) continue;
            if (teamMaxWins - win[teamLeftIndex] < 0) {
                return true;
            }
            int leftSearchIndex = teamLeftIndex;
            if (teamLeftIndex > teamIndex) leftSearchIndex--;
            FlowEdge flowEdge = new FlowEdge(leftSearchIndex, tIndex, teamMaxWins - win[teamLeftIndex]);
            flowNetwork.addEdge(flowEdge);
            for (int teamRightIndex = 1 + teamLeftIndex; teamRightIndex <= teamName.size(); teamRightIndex++) {
                if (teamIndex == teamRightIndex) continue;
                int rightSearchIndex = teamRightIndex;
                if (teamRightIndex > teamIndex) rightSearchIndex--;
                int capacity = game[teamLeftIndex][teamRightIndex];
                flowEdge = new FlowEdge(0, gameIndex, capacity);
                flowNetwork.addEdge(flowEdge);
                flowEdge = new FlowEdge(gameIndex, leftSearchIndex, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(flowEdge);
                flowEdge = new FlowEdge(gameIndex, rightSearchIndex, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(flowEdge);
                gameIndex++;
            }
        }
        FordFulkerson fordFulkerson = null;
        if (teamName.size() > 1) fordFulkerson = new FordFulkerson(flowNetwork, 0, tIndex);

        int maxFlowSum = 0;
        for (int i = 0; i < game.length; i++) {
            if (i == teamIndex) continue;
            for (int i2 = 0; i2 <= i; i2++) {
                if (i2 == teamIndex) continue;
                maxFlowSum += game[i][i2];
            }
        }

        return fordFulkerson == null || maxFlowSum != fordFulkerson.value();
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        if (!isEliminated(team)) return null;
        HashSet<String> result = new HashSet<>();

        // mathematically eliminated
        int teamIndex = teamName.get(team);
        int teamMaxWins = win[teamIndex] + remain[teamIndex];

        for (String name : teamName.keySet()) {
            if (name.equals(team)) continue;
            if (win[teamName.get(name)] > teamMaxWins) result.add(name);
        }

        if (!result.isEmpty()) return result;

        for (String name : teamName.keySet()) {
            if (name.equals(team)) continue;
            if (win[teamName.get(name)] + remain[teamName.get(name)] > teamMaxWins) result.add(name);
        }

        return result;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                System.out.println(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    System.out.println(t + " ");
                }
                System.out.println("}");
            }
            else {
                System.out.println(team + " is not eliminated");
            }
        }
    }

}
