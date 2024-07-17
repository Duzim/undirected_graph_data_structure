package main.models;

import main.models.interfaces.IGrafo;

import java.util.*;

public class Grafo<T> implements IGrafo<T> {
    private ArrayList<Vertice<T>> vertices;
    private ArrayList<Aresta<T>> arestas;

    public Grafo() {
        this.vertices = new ArrayList<Vertice<T>>();
        this.arestas = new ArrayList<Aresta<T>>();
    }

    public void adicionarVertice(T dado) {
        if(dado == null){
            throw new RuntimeException("Não é possível inserir um vértice com dado nulo");
        }

        Vertice<T> novoVertice = new Vertice<T>(dado);
        this.vertices.add(novoVertice);
    }

    public void adicionarVertices(T[] dados) {
        if(dados == null){
            throw new RuntimeException("Não é possível inserir vértices com um array de dados nulos");
        }

        if(dados.length == 0){
            throw new RuntimeException("Não é possível inserir vértices com um array de dados vazio");
        }

        for (T dado : dados) {
            this.adicionarVertice(dado);
        }
    }

    public void adicionarAresta(Double peso, T dadoInicio, T dadoFim) {
        Vertice<T> origem = this.obterVerticePorDado(dadoInicio);
        if (origem == null){
            throw new RuntimeException("Não é possível inserir uma aresta com uma origem inválida");
        }

        Vertice<T> destino = this.obterVerticePorDado(dadoFim);
        if(destino == null){
            throw new RuntimeException("Não é possível inserir uma aresta com um destino inválido");
        }

        Aresta<T> aresta = new Aresta<T>(peso, origem, destino);

        this.arestas.add(aresta);
    }

    @Override
    public Integer obterQuantidadeLacos() {
        int quantidadeLacos = 0;

        for (Aresta aresta : this.arestas) {
            if (aresta.getOrigem().equals(aresta.getDestino())) {
                quantidadeLacos++;
            }
        }

        return quantidadeLacos;
    }

    @Override
    public Integer obterQuantidadeArestasMultiplas() {
        //Na nossa abordagem, ele conta quantas ocorrências múltiplas aconteceram. Ou seja:
        //Se só tem 1 ocorrência de aresta, o método retorna 0
        //Se tem 2 ocorrências da aresta, o método retorna 1
        //Se tem 3, ocorrências da aresta, o método retorna 2
        //Como o grafo ainda não é direcionado, duas arestas são consideradas iguais se elas ligam os mesmos vértices,
        //independente da ordem desses vértices (origem/destino)

        Set<String> arestasUnicas = new HashSet<>();
        int arestasMultiplas = 0;

        for (Aresta<T> aresta : arestas) {
            String chaveAresta = aresta.hashCode() + "";
            if (arestasUnicas.contains(chaveAresta)) {
                arestasMultiplas++;
            } else {
                arestasUnicas.add(chaveAresta);
            }
        }

        return arestasMultiplas;
    }

    @Override
    public Boolean verificarSeGrafoEhCompleto() {
        if (this.vertices.size() <= 1) {
            return false;
        }

        for (int i = 0; i < this.vertices.size(); i++) {
            for (int j = i + 1; j < this.vertices.size(); j++) {
                Vertice<T> vertice1 = this.vertices.get(i);
                Vertice<T> vertice2 = this.vertices.get(j);

                if (!verificarSeExisteArestaEntreVertices(vertice1, vertice2)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean verificarSeExisteArestaEntreVertices(Vertice<T> verticeOrigem, Vertice<T> verticeDestino) {
        for (Aresta<T> aresta : this.arestas) {
            if ((aresta.getOrigem().equals(verticeOrigem) && aresta.getDestino().equals(verticeDestino)) ||
                    (aresta.getOrigem().equals(verticeDestino) && aresta.getDestino().equals(verticeOrigem))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Integer obterGrauDoVertice(T dado) {
        Vertice<T> vertice = obterVerticePorDado(dado);

        int grau = 0;
        for (Aresta aresta : this.arestas) {
            if (aresta.getOrigem().equals(vertice) && aresta.getDestino().equals(vertice)
                    || aresta.getOrigem().equals(vertice) && !aresta.getDestino().equals(vertice)
                    || !aresta.getOrigem().equals(vertice) && aresta.getDestino().equals(vertice)) {
                grau++;
            }
        }

        return grau;
    }

    @Override
    public String obterCaminhoEntreVertices(T dadoOrigem, T dadoDestino) {
        Vertice<T> origem = obterVerticePorDado(dadoOrigem);
        Vertice<T> destino = obterVerticePorDado(dadoDestino);

        if (destino == null || destino == null) {
            //throw new RuntimeException("Vértices origem ou destino não encontrados!!!");
            return "Vértices origem ou destino não encontrados!";
        }

        List<List<Vertice<T>>> todosCaminhos = new ArrayList<>();
        obterCaminhoRecursivo(origem, destino, new ArrayList<>(), todosCaminhos);

        if (todosCaminhos.isEmpty()) {
            //throw new RuntimeException("O caminho entre " + dadoOrigem.toString() + " e "+dadoDestino.toString()+" não foi encontrado.");
            return "O caminho entre " + dadoOrigem.toString() + " e "+dadoDestino.toString()+" não foi encontrado.";
        }

        StringBuilder sb = new StringBuilder();
        for (List<Vertice<T>> caminho : todosCaminhos) {
            sb.append("Inicio -> ");
            for (Vertice<T> vertice : caminho) {
                sb.append(vertice.getDado().toString()).append(" -> ");
            }
            sb.append("Fim");
            sb.append("\n");
        }

        return sb.toString();
    }

    private void obterCaminhoRecursivo(Vertice<T> verticeAtual, Vertice<T> destino, List<Vertice<T>> caminhoAtual, List<List<Vertice<T>>> todosCaminhos) {
        caminhoAtual.add(verticeAtual);

        verticeAtual.setVisitado(true);

        if (verticeAtual.equals(destino)) {
            todosCaminhos.add(new ArrayList<>(caminhoAtual));
        } else {
            for (Aresta<T> aresta : arestas) {
                if (aresta.getOrigem().equals(verticeAtual) && !aresta.getDestino().getVisitado()) {
                    obterCaminhoRecursivo(aresta.getDestino(), destino, caminhoAtual, todosCaminhos);
                }else if(aresta.getDestino().equals(verticeAtual) && !aresta.getOrigem().getVisitado()){
                    obterCaminhoRecursivo(aresta.getOrigem(), destino, caminhoAtual, todosCaminhos);
                }
            }
        }

        caminhoAtual.remove(verticeAtual);
        verticeAtual.setVisitado(false);
    }

    private Vertice<T> obterVerticePorDado(T dado) {
        Vertice<T> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }

        return vertice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices:");

        for (Vertice vertice : this.vertices) {
            sb.append(" ");
            sb.append(vertice.getDado().toString());
        }

        sb.append("\n\n");

        for (Aresta aresta : this.arestas) {
            sb.append(aresta.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}