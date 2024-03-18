package Grafo ;

import java.util.ArrayList;


public class Vertice {
    
    public int num, grau ;
    public String nome ;
    public ArrayList<Vertice> listaAdj ;
    public boolean visitado ;
    public boolean isFilme ;
    int distancia;
    
    public Vertice(int num)
    {
        this.num = num ;
        this.grau = 0 ;
        this.listaAdj = new ArrayList<>() ;
        this.visitado = false ;
        this.nome = "" ;
        this.isFilme = false ;
    }
    
    public void adicionaNome(String nome) 
    {
        this.nome = nome;
    }

    public void adicionaAresta(Vertice v) 
    {
        listaAdj.add(v) ; // Adiciona o filme
        grau++ ;
    }

    public void visita() 
    {
        visitado = true ;
    }

    public boolean foiVisitado() 
    {
        return visitado ;
    }

    public String toString() 
    {
        return Integer.toString(num) ;
    }
}
