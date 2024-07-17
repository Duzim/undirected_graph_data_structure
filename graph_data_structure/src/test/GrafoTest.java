package test;

import main.models.Grafo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrafoTest {
    @Test
    void adicionarVertice() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");

        assertTrue(grafo.toString().contains("Vertices: A B C D"));

        grafo.adicionarVertice("E");

        assertTrue(grafo.toString().contains("Vertices: A B C D E"));
    }

    @Test
    void naoAdicionaVerticePoisODadoEhNulo() {
        Grafo<String> grafo = new Grafo<>();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> grafo.adicionarVertice(null));

        assertEquals("Não é possível inserir um vértice com dado nulo", exception.getMessage());
    }

    @Test
    void adicionarMuitosVertices() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        assertTrue(grafo.toString().contains("Vertices: A B C D E F G"));

        grafo.adicionarVertice("H");
        assertTrue(grafo.toString().contains("Vertices: A B C D E F G H"));
    }

    @Test
    void naoAdicionaMuitosVerticesPoisOArrayDeEntradaEhNuloOuVazio(){
        Grafo<String> grafo = new Grafo<>();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> grafo.adicionarVertices(null));
        assertEquals("Não é possível inserir vértices com um array de dados nulos", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> grafo.adicionarVertices(new String[]{}));
        assertEquals("Não é possível inserir vértices com um array de dados vazio", exception.getMessage());
    }

    @Test
    void adicionarArestasSemDirecao() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        String grafoString = grafo.toString();

        assertTrue(grafoString.contains("Aresta: A B"));
        assertTrue(grafoString.contains("Aresta: A D"));
        assertTrue(grafoString.contains("Aresta: A C"));
        assertTrue(grafoString.contains("Aresta: C D"));
        assertTrue(grafoString.contains("Aresta: B D"));
    }

    @Test
    void naoAdicionaArestaPoisVerticeInformadoNaoExiste(){
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        RuntimeException exception = assertThrows(RuntimeException.class, () -> grafo.adicionarAresta(1d, "A", "G"));
        assertEquals("Não é possível inserir uma aresta com um destino inválido", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> grafo.adicionarAresta(1d, "F", "B"));
        assertEquals("Não é possível inserir uma aresta com uma origem inválida", exception.getMessage());
    }

    @Test
    void contarQuantidadeDeLacos() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        assertEquals(0, grafo.obterQuantidadeLacos());

        grafo.adicionarAresta(1d, "B", "B");

        assertEquals(1, grafo.obterQuantidadeLacos());

        grafo.adicionarAresta(1d, "A", "A");
        grafo.adicionarAresta(1d, "A", "A");

        assertEquals(3, grafo.obterQuantidadeLacos());
    }

    @Test
    void contarQuantidadeDeArestasMultiplas() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        assertEquals(0, grafo.obterQuantidadeArestasMultiplas());

        grafo.adicionarAresta(1d, "A", "B");
        assertEquals(1, grafo.obterQuantidadeArestasMultiplas());

        grafo.adicionarAresta(1d, "B", "A");
        assertEquals(2, grafo.obterQuantidadeArestasMultiplas());

        grafo.adicionarAresta(1d, "D", "A");
        assertEquals(3, grafo.obterQuantidadeArestasMultiplas());
    }

    @Test
    void verificarSeOGrafoEhEcompleto() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "B", "C");
        grafo.adicionarAresta(1d, "B", "D");
        grafo.adicionarAresta(1d, "C", "D");

        assertTrue(grafo.verificarSeGrafoEhCompleto());

        grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        assertFalse(grafo.verificarSeGrafoEhCompleto());

        grafo = new Grafo<>();
        assertFalse(grafo.verificarSeGrafoEhCompleto());

        grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A"});
        assertFalse(grafo.verificarSeGrafoEhCompleto());
    }

    @Test
    void verificarGrauDeUmVertice() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        assertEquals(3, grafo.obterGrauDoVertice("A"));
        assertEquals(2, grafo.obterGrauDoVertice("B"));
        assertEquals(2, grafo.obterGrauDoVertice("C"));
        assertEquals(3, grafo.obterGrauDoVertice("D"));

        grafo.adicionarAresta(1d, "B", "C");
        assertEquals(3, grafo.obterGrauDoVertice("B"));
        assertEquals(3, grafo.obterGrauDoVertice("C"));

        grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A"});
        assertEquals(0, grafo.obterGrauDoVertice("A"));
    }

    @Test
    void verificarCaminhoEntreDoisVertices() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"A", "B", "C", "D"});

        grafo.adicionarAresta(1d, "A", "B");
        grafo.adicionarAresta(1d, "A", "D");
        grafo.adicionarAresta(1d, "A", "C");
        grafo.adicionarAresta(1d, "C", "D");
        grafo.adicionarAresta(1d, "B", "D");

        String caminhos = grafo.obterCaminhoEntreVertices("A", "D");

        boolean caminhoValido = caminhos.contains("Inicio -> A -> D -> Fim")
                || caminhos.contains("Inicio -> A -> B -> D -> Fim")
                || caminhos.contains("Inicio -> A -> C -> D -> Fim");

        assertTrue(caminhoValido);

        grafo = new Grafo<>();
        grafo.adicionarVertices(new String[]{"1", "2", "3", "4", "5", "6"});

        grafo.adicionarAresta(1d, "1", "3");
        grafo.adicionarAresta(1d, "2", "3");
        grafo.adicionarAresta(1d, "2", "5");
        grafo.adicionarAresta(1d, "3", "4");
        grafo.adicionarAresta(1d, "3", "5");
        grafo.adicionarAresta(1d, "4", "3");
        grafo.adicionarAresta(1d, "4", "6");
        grafo.adicionarAresta(1d, "5", "6");

        caminhos = grafo.obterCaminhoEntreVertices("1", "6");

        caminhoValido = caminhos.contains("Inicio -> 1 -> 3 -> 2 -> 5 -> 6 -> Fim")
                || caminhos.contains("Inicio -> 1 -> 3 -> 5 -> 6 -> Fim")
                || caminhos.contains("Inicio -> 1 -> 3 -> 4 -> 6 -> Fim");

        assertTrue(caminhoValido);
    }

    @Test
    void testVerticeOrigemOuDestinoNaoEncontrado() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");

        String resultado = grafo.obterCaminhoEntreVertices("C", "D");
        assertEquals("Vértices origem ou destino não encontrados!", resultado);


    }
    @Test
    void testNenhumCaminhoEncontrado() {
        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");

        String resultado = grafo.obterCaminhoEntreVertices("A", "B");
        assertEquals("O caminho entre A e B não foi encontrado.", resultado);
    }
}