import main.models.Grafo;

public class Main {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[] {"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        System.out.println(grafo.toString());
    }
}