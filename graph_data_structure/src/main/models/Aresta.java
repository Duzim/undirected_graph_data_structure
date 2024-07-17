package main.models;

import java.util.Objects;

public class Aresta<T> {
    private Double peso;
    private Vertice<T> origem;
    private Vertice<T> destino;

    public Aresta(Double peso, Vertice<T> origem, Vertice<T> destino) {
        this.peso = peso;
        this.origem = origem;
        this.destino = destino;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Vertice<T> getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice<T> origem) {
        this.origem = origem;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aresta<?> aresta = (Aresta<?>) o;
        return (Objects.equals(origem, aresta.origem) && Objects.equals(destino, aresta.destino))
                || (Objects.equals(destino, aresta.origem) && Objects.equals(origem, aresta.destino))
                || (Objects.equals(origem, aresta.destino) && Objects.equals(destino, aresta.origem));
    }

    @Override
    public int hashCode() {
        return Objects.hash(origem, destino) + Objects.hash(destino, origem);
    }

    @Override
    public String toString(){
        return "Aresta: " + this.origem.getDado().toString() +  " " + this.destino.getDado().toString();
    }
}