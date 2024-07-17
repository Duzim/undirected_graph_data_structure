package main.models.interfaces;

public interface IGrafo<T> {
    void adicionarVertice(T dado);
    void adicionarVertices(T[] dados);
    void adicionarAresta(Double peso, T dadoInicio, T dadoFim);
    Integer obterQuantidadeLacos();
    Integer obterQuantidadeArestasMultiplas();
    Boolean verificarSeGrafoEhCompleto();
    Integer obterGrauDoVertice(T dado);
    String obterCaminhoEntreVertices(T dadoOrigem, T dadoDestino);
}