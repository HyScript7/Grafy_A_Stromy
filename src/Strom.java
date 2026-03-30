import java.util.*;
import java.util.function.BiConsumer;

public class Strom {
    private final Map<CastMise, List<CastMise>> castiMise;
    private final Map<CastMise, List<CastMise>> reverseLookup;
    private CastMise rootNode;

    public Strom() {
        this.castiMise = new HashMap<>();
        this.reverseLookup = new HashMap<>();
        this.rootNode = null;
    }

    public List<CastMise> getChildren(CastMise parent) {
        return castiMise.computeIfAbsent(parent, key -> new ArrayList<>());
    }

    private List<CastMise> getReverse(CastMise parent) {
        return  reverseLookup.computeIfAbsent(parent, key -> new ArrayList<>());
    }

    public void add(CastMise parent, CastMise child) {
        if (rootNode == null) {
            rootNode = parent;
        }
        getChildren(parent).add(child);
        getChildren(child);
        getReverse(child).add(parent);
    }

    public void print() {
        dfs(rootNode, new HashSet<>(), 0, (node, depth) -> {
            System.out.println("  ".repeat(depth) + "- " + node);
        });
    }

    private void dfs(CastMise node, Set<CastMise> visited, int depth, BiConsumer<CastMise, Integer> consumer) {
        consumer.accept(node, depth);
        visited.add(node);
        for (CastMise child : getChildren(node)) {
            if (visited.contains(child)) continue;
            dfs(child, visited, depth+1, consumer);
        }
    }

    public int countAllNodes() {
        return castiMise.size();
    }

    public int countAllLeafs() {
        return Long.valueOf(castiMise.entrySet().stream().filter(kv -> kv.getValue().isEmpty()).count()).intValue();
    }

    public int calculateDepth() {
        return calculateDepthDfs(rootNode,  new HashSet<>(), 1);
    }

    public int calculateDepthDfs(CastMise node, Set<CastMise> visited, int depth) {
        int maxDepth = depth;
        for (CastMise child : getChildren(node)) {
            if (visited.contains(child)) continue;
            visited.add(child);
            int childDepth = calculateDepthDfs(child, visited, depth+1);
            if (childDepth > maxDepth) maxDepth = childDepth;
        }
        return maxDepth;
    }

    public int celkovyCas() {
        int soucet = 0;
        for (CastMise node : castiMise.keySet()) {
            soucet += node.casTrvaniVMinutach();
        }
        return soucet;
    }

    public int nejdelsiCastMise() {
        int nejdelsi = 0;
        for (CastMise node : castiMise.keySet()) {
            if (node.casTrvaniVMinutach() > nejdelsi) {
                nejdelsi = node.casTrvaniVMinutach();
            }
        }
        return nejdelsi;
    }

    public boolean jdeOList(String nazevMise) {
        for (CastMise node : castiMise.keySet()) {
            if (node.nazev().equalsIgnoreCase(nazevMise)) {
                return getChildren(node).isEmpty();
            }
        }
        throw new IllegalArgumentException("Taková mise v stromě není!");
    }

    public void vypisCestuKeKorenu(String nazevMise) {
        for (CastMise node : castiMise.keySet()) {
            if (node.nazev().equalsIgnoreCase(nazevMise)) {
                List<CastMise> cesta = getPath(node);
                for (CastMise child : cesta) {
                    System.out.print(child);
                    if (!child.equals(rootNode)) {
                        System.out.print(" -> ");
                    }
                }
                return;
            }
        }
        throw new IllegalArgumentException("Taková mise v stromě není!");
    }

    private List<CastMise> getPath(CastMise node) {
        List<CastMise> cesta = new ArrayList<>();
        cesta.add(node);
        CastMise cestaNode = node;
        while (!cestaNode.equals(rootNode)) {
            cestaNode = getReverse(cestaNode).getFirst();
            cesta.add(cestaNode);
        }
        return cesta;
    }

    public int urovenSNejviceUzly() {
        CastMise mostChildren = bfs(rootNode, new HashSet<>());
        // Až v tomto bodě po dopsání bfs mi došlo, že tady zase míchám češtinu s angličtinou... nu co se dá dělat.
        return getPath(mostChildren).size();
    }

    private CastMise bfs(CastMise node, Set<CastMise> queue) {
        CastMise current = node;
        CastMise mostChildren = null;
        int mostChildrenValue = -1;
        while (current != null) {
            if (getChildren(current).size() > mostChildrenValue) {
                mostChildrenValue = getChildren(current).size();
                mostChildren = current;
            }
            queue.addAll(getChildren(current).reversed());
            current = queue.stream().findFirst().orElse(null);
            queue.remove(current);
        }
        return mostChildren;
    }
}
