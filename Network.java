import java.util.Iterator;

public class Network {
    EdgeWeightedGraph weightedGraph;

    public Network(String filename) {
        weightedGraph = new EdgeWeightedGraph(filename);
    }

    public void lowestPath(int v1, int v2) {
        DijkstraSP dijkstraSP = new DijkstraSP(weightedGraph, v2);

        if(dijkstraSP.hasPathTo(v1)) {
            System.out.print("Path: ");

            Iterable<Edge> edges = dijkstraSP.pathTo(v1);
            Iterator<Edge> edgeIterator = edges.iterator();

            printPath(edgeIterator);

            edgeIterator = edges.iterator();

            double max = Double.MAX_VALUE;

            while(edgeIterator.hasNext()) {
                Edge e = edgeIterator.next();
                if(e.bandwidth() < max)
                    max = e.bandwidth();
            }
            System.out.println("Bandwidth: " + max + " Mbps");
        }

        else
            System.out.println("No Path Found");
    }

    public void isCopper() {
        boolean broken = false;

        EdgeWeightedGraph weightedGraph1 = new EdgeWeightedGraph(weightedGraph.V());

        Iterable<Edge> edges = weightedGraph.edges();
        Iterator<Edge> edgeIterator = edges.iterator();

        while (edgeIterator.hasNext()) {
            Edge e = edgeIterator.next();
            if(e.type().equalsIgnoreCase("copper"))
                weightedGraph1.addEdge(e);
        }

        CC cc = new CC(weightedGraph1);

        for(int i = 0; i < weightedGraph.V(); i++) {
            if(cc.size(i) != weightedGraph.V())
                broken = true;
        }

        if(broken)
            System.out.println("Network is not copper-only connected");
        else
            System.out.println("Network is copper-only connected");
    }

    public void maxData(int v1, int v2) {
        FlowNetwork flowNetwork = new FlowNetwork(weightedGraph.V());

        Iterable<Edge> edges = weightedGraph.edges();
        Iterator<Edge> edgeIterator = edges.iterator();

        while (edgeIterator.hasNext()) {
            Edge e = edgeIterator.next();
            FlowEdge flowEdge = new FlowEdge(e.either(), e.other(e.either()), e.bandwidth());
            flowNetwork.addEdge(flowEdge);
        }

        FordFulkerson fordyBoi;

        if(v1 < v2)
            fordyBoi = new FordFulkerson(flowNetwork, v1, v2);
        else
            fordyBoi = new FordFulkerson(flowNetwork, v2, v1);

        System.out.println(fordyBoi.value() + " Mbps");

    }

    public void lowestLatency() {
        KruskalMST kruskalMST = new KruskalMST(weightedGraph);

        Iterable<Edge> edges = kruskalMST.edges();
        Iterator<Edge> edgeIterator = edges.iterator();

        System.out.println("Lowest Average Latency Spanning Tree: ");
        printPath(edgeIterator);
    }

    public void connectivity() {
        boolean broken = false;

        for(int i = 0; i < weightedGraph.V() - 1; i++) {
            for (int j = i + 1; j < weightedGraph.V(); j++) {
                EdgeWeightedGraph weightedGraph1 = new EdgeWeightedGraph(weightedGraph.V());

                Iterable<Edge> edges = weightedGraph.edges();
                Iterator<Edge> edgeIterator = edges.iterator();

                while (edgeIterator.hasNext()) {
                    Edge e = edgeIterator.next();
                    if(e.either() != i && e.other(e.either()) != i && e.either() != j && e.other(e.either()) != j)
                        weightedGraph1.addEdge(e);
                }

                CC cc = new CC(weightedGraph1);

                for(int k = 0; k < weightedGraph.V(); k++) {
                    if(k != i && k != j) {
                        if(cc.size(k) != weightedGraph.V() - 2)
                            broken = true;
                    }
                }
            }
        }

        if(broken)
            System.out.println("Removal of at least two vertices results in disconnection");
        else
            System.out.println("Removal of any two vertices does not result in disconnection");
    }

    private void printPath(Iterator<Edge> edgeIterator) {
        while(edgeIterator.hasNext()) {
            Edge e = edgeIterator.next();
            if (!e.flipped())
                System.out.print(e.either() + " - " + e.other(e.either()));
            else
                System.out.print(e.other(e.either()) + " - " + e.either());

            if (edgeIterator.hasNext())
                System.out.print(", ");
            else
                System.out.println();
        }
    }
}