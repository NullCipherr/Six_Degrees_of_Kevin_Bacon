package Grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Main {

public static void main(String[] args) throws IOException {
        
        int countAtores = 0;
        int countFilmes = 0;
        
        // Le o arquivo e conta o número de atores e filmes.
        try (BufferedReader br = new BufferedReader(new FileReader("src/movies.txt"))) 
        {
            String line;
            
            while ((line = br.readLine()) != null) 
            {
                String[] atores = line.split("/") ;
                countAtores += atores.length ;
                countFilmes++ ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Cria o grafo
        Grafo grafo = new Grafo(countAtores) ;
        
        try (BufferedReader br = new BufferedReader(new FileReader("src/movies.txt"))) 
        {
            String line ;
            int atorAtual = 0 ;
            int filmeAtual = 0 ;
            boolean primeiraIteracao ;
            
            for (int i = 0; i < countFilmes; i++) 
            {
                line = br.readLine() ;
                String[] atores = line.split("/") ;
                
                primeiraIteracao = true ; // Primeiro elemento é Filme, logo é a aresta que irá ligar todos os atores
                
                for (String ator : atores) 
                {
                    if (!primeiraIteracao) 
                    {
                        // Verifica se o ator já foi inserido no Grafo.
                        for(int j = 0 ; j < grafo.numeroVertices ; j++)
                        {
                            if(grafo.vertices[j].nome.equals(ator))
                            {
                                grafo.vertices[j].adicionaAresta(grafo.vertices[filmeAtual]) ;
                                grafo.vertices[filmeAtual].adicionaAresta(grafo.vertices[j]) ;
                                break;
                            }
                        }
                        
                        grafo.vertices[atorAtual].adicionaNome(ator) ;
                        grafo.vertices[atorAtual].isFilme = false ;
                        grafo.vertices[filmeAtual].adicionaAresta(grafo.vertices[atorAtual]) ;
                        grafo.vertices[atorAtual].adicionaAresta(grafo.vertices[filmeAtual]) ;
                    } 
                    else 
                    {
                        filmeAtual = atorAtual ;
                        grafo.vertices[filmeAtual].adicionaNome(ator) ;
                        grafo.vertices[filmeAtual].isFilme = true ;
                        primeiraIteracao = false ;
                    }
                    
                    atorAtual++ ;
                }
            }
        } catch (IOException e) {
        }
        
        
        // Demonstração do Projeto 1 e 2
        //System.out.println(grafo.vertices[52].grau) ;
        //System.out.println(grafo.vertices[52].listaAdj) ;
        
        // Demonstração do Projeto 3
        grafo.grafoKevinBacon();
        grafo.atorBaconNumber("Willcox, Tim", "Bacon, Kevin") ; // OBS : Procurar por um ator de cada vez. 

    }
    
}

