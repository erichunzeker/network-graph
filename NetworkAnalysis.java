import java.util.Scanner;

public class NetworkAnalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Network network = new Network(args[0]);
        boolean run = true;

        while (run) {
            System.out.println(
                    "1.) Find lowest latency path between two vertices" + "\n"
                            + "2.) Connectivity Material" + "\n"
                            + "3.) Find Maximum Data Transfer between two vertices" + "\n"
                            + "4.) Find Lowest Average Latency Spanning tree" + "\n"
                            + "5.) Connection Stability" + "\n"
                            + "6.) Quit");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("1")) {
                System.out.println("Input Vertex 1");
                int v1 = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Input Vertex 2");
                int v2 = scanner.nextInt();
                scanner.nextLine();

                network.lowestPath(v1, v2);
            }

            else if (input.equalsIgnoreCase("2"))
                network.isCopper();

            else if (input.equalsIgnoreCase("3")) {
                System.out.println("Input Vertex 1");
                int v1 = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Input Vertex 2");
                int v2 = scanner.nextInt();
                scanner.nextLine();

                network.maxData(v1, v2);
            }

            else if (input.equalsIgnoreCase("4"))
                network.lowestLatency();

            else if (input.equalsIgnoreCase("5"))
                network.connectivity();

            else if (input.equalsIgnoreCase("6")) {
                run = false;
                System.out.println("Goodbye");
            }

            else
                System.out.println("Invalid Input");

            System.out.println();
        }
    }
}