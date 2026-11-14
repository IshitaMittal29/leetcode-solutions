import java.util.*;

class Solution {
    public double[] calcEquation(
        List<List<String>> equations,
        double[] values,
        List<List<String>> queries
    ) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            graph.putIfAbsent(a, new HashMap<>());
            graph.putIfAbsent(b, new HashMap<>());

            graph.get(a).put(b, values[i]);
            graph.get(b).put(a, 1.0 / values[i]);
        }

        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);

            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                result[i] = -1.0;
            } else if (start.equals(end)) {
                result[i] = 1.0;
            } else {
                Set<String> visited = new HashSet<>();
                result[i] = dfs(graph, start, end, 1.0, visited);
            }
        }

        return result;
    }

    private double dfs(
        Map<String, Map<String, Double>> graph,
        String current,
        String target,
        double product,
        Set<String> visited
    ) {
        if (current.equals(target)) {
            return product;
        }

        visited.add(current);

        for (Map.Entry<String, Double> entry : graph.get(current).entrySet()) {
            String next = entry.getKey();

            if (!visited.contains(next)) {
                double ans = dfs(
                    graph,
                    next,
                    target,
                    product * entry.getValue(),
                    visited
                );

                if (ans != -1.0) {
                    return ans;
                }
            }
        }

        return -1.0;
    }
}