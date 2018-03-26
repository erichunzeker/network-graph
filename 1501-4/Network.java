import java.util.Iterator;

public class Network
{
    EdgeWeightedGraph weightedGraph;
    public Network(String filename)
    {
        weightedGraph = new EdgeWeightedGraph(filename);
    }

    public void lowestPath(int v1, int v2)
    {
        DijkstraSP dijkstraSP = new DijkstraSP(weightedGraph, v2, 0);

        if(dijkstraSP.hasPathTo(v1))
        {
            System.out.print("Path: ");

            Iterable<Edge> edges = dijkstraSP.pathTo(v1);
            Iterator<Edge> edgeIterator = edges.iterator();

            while(edgeIterator.hasNext())
            {
                Edge e = edgeIterator.next();
                if(!e.flipped())
                    System.out.print(e.either() + " - " + e.other(e.either()));
                else
                    System.out.print(e.other(e.either()) + " - " + e.either());

                if(edgeIterator.hasNext())
                    System.out.print(", ");
                else
                    System.out.println();
            }

            Iterable<Edge> bandwithList = dijkstraSP.pathTo(v1);
            Iterator<Edge> edgeIterator1 = bandwithList.iterator();
            double max = Double.MAX_VALUE;

            while(edgeIterator1.hasNext())
            {
                Edge e = edgeIterator1.next();
                if(e.bandwidth() < max)
                    max = e.bandwidth();
            }
            System.out.println("Bandwidth: " + max + " Mbps");
        }

        else
            System.out.println("No Path Found");
    }

    public void isCopper()
    {
        boolean broken = false;

        EdgeWeightedGraph weightedGraph1 = new EdgeWeightedGraph(weightedGraph.V());

        Iterable<Edge> edges = weightedGraph.edges();
        Iterator<Edge> edgeIterator = edges.iterator();

        while (edgeIterator.hasNext())
        {
            Edge e = edgeIterator.next();
            if(e.type().equalsIgnoreCase("copper"))
                weightedGraph1.addEdge(e);
        }

        CC cc = new CC(weightedGraph1);

        for(int i = 0; i < weightedGraph.V(); i++)
        {
            if(cc.size(i) != weightedGraph.V())
                broken = true;
        }


        if(broken)
            System.out.println("Network Depends on Copper Wire");
        else
            System.out.println("Network does not depend on Copper Wire");
    }

    public void maxData(int v1, int v2)
    {
        DijkstraSP dijkstraSP = new DijkstraSP(weightedGraph, v2, 1);
        if(dijkstraSP.hasPathTo(v2))
        {
            Iterable<Edge> bandwithList = dijkstraSP.pathTo(v1);
            Iterator<Edge> edgeIterator1 = bandwithList.iterator();
            double max = Double.MAX_VALUE;
            while(edgeIterator1.hasNext())
            {
                Edge e = edgeIterator1.next();
                if(e.bandwidth() < max)
                    max = e.bandwidth();
            }
            System.out.println("Max Bandwidth: " + max + " Mbps");
        }
        else
            System.out.println("Noo Path Found");
    }

    public void lowestLatency()
    {
        KruskalMST kruskalMST = new KruskalMST(weightedGraph);

        Iterable<Edge> edges = kruskalMST.edges();
        Iterator<Edge> edgeIterator = edges.iterator();

        System.out.println("Lowest Average Latency Spanning Tree: ");

        while(edgeIterator.hasNext())
        {
            Edge e = edgeIterator.next();
            if(!e.flipped())
                System.out.print(e.either() + " - " + e.other(e.either()));
            else
                System.out.print(e.other(e.either()) + " - " + e.either());
            if(edgeIterator.hasNext())
                System.out.print(", ");
            else
                System.out.println();
        }
    }

    public void connectivity()
    {
        boolean broken = false;

        for(int i = 0; i < weightedGraph.V() - 1; i++)
        {
            for (int j = i + 1; j < weightedGraph.V(); j++)
            {
                //remove i and j
                EdgeWeightedGraph weightedGraph1 = new EdgeWeightedGraph(weightedGraph.V());

                Iterable<Edge> edges = weightedGraph.edges();
                Iterator<Edge> edgeIterator = edges.iterator();

                while (edgeIterator.hasNext())
                {
                    Edge e = edgeIterator.next();
                    if(e.either() != i && e.other(e.either()) != i && e.either() != j && e.other(e.either()) != j)
                        weightedGraph1.addEdge(e);
                }

                CC cc = new CC(weightedGraph1);

                for(int k = 0; k < weightedGraph.V(); k++)
                {
                    if(k != i && k != j)
                    {
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


}
