package main.models;

public class Vertice<T> {
    private T dado;
    private Boolean isVisitado;

    public Vertice(T dado){
        this.isVisitado = false;
        this.dado = dado;
    }

    public T getDado() {
        return dado;
    }

    public void setDado(T dado) {
        this.dado = dado;
    }

    public Boolean getVisitado() {
        return isVisitado;
    }

    public void setVisitado(Boolean visitado) {
        isVisitado = visitado;
    }

    @Override
    public String toString(){
        return this.dado.toString();
    }
}