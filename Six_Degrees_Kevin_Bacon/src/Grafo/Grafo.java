package Grafo ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Grafo {
    
    public int numeroVertices ;  
    public int numeroAresta ;
    public Vertice[] vertices ; //CONTEM OS VERTICES
    public Vertice[] aux_vertices ; //CONTEM OS VERTICES
    public Map<String, Integer> numerosKevinBacon ; // Nome do vertice e seu respectivo numero de kevin bacon.
    

    public Grafo(int v)  //v é a quantidade de vértices
    {
        this.numeroVertices = v ;
        this.numeroAresta = 0 ;
        vertices = new Vertice[v] ;
        this.numerosKevinBacon = new HashMap<>() ;
        
        //CRIA OS VERTICES DO GRAFO
        for (int i = 0; i < v; i++)
        {
            vertices[i] = new Vertice(i) ;
        }
    }

    public int getV() // Devolve a quantidade de vértices
    {
        return numeroVertices ;
    }

    public int getA() // Devolve a quantidade de arestas
    {
        return numeroAresta ;
    }
  
    public ArrayList<Integer> adj(int v) 
    {
        int size = vertices[v].listaAdj.size();
        ArrayList<Integer> a_list = new ArrayList<>(size);
    
        if (size == 0) 
        {
            System.out.println("Vértice (" + v + ") não possui nenhum elemento em sua lista de adjacência.");
            return a_list;
        }
    
        for (int i = 0; i < size; i++) 
        {
            Vertice adj = vertices[v].listaAdj.get(i) ;
            a_list.add(adj.num) ;
        }
    
        //System.out.println("Vértice (" + v + ") --> " + a_list) ;
        return a_list ;
    }


    public int grau(int v) // Devolve o grau do vértice v
    {   
        return vertices[v].grau ;
    }
    
    // Vertice -> String
    public String toString() // Devolve uma string em que cada linha representa uma lista de adjacência do grafo
    {
        int i, j ;
     
        String s_saida = new String() ;
        String s_aux = new String() ;
        
        aux_vertices = new Vertice[vertices.length] ;
        
        for (i = 0; i < numeroVertices; i++) // PERCORRE TODOS OS VÉRTICES DO GRAFO 
        {
            s_aux = "\0" ;
            
            for(j = 0 ; j < vertices[i].listaAdj.size(); j++) // PERCORRE TODA A LISTA DE ADJACENCIA DO VÉRTICE i 
            {
                aux_vertices[i] = vertices[i].listaAdj.get(j); 
                s_aux = s_aux.concat(Integer.toString(aux_vertices[i].num)) ; 
                s_aux = s_aux.concat(" ") ;    
            }   
            
            s_saida = s_saida.concat(i + ":" + s_aux + System.lineSeparator()) ;
            
        }       
        return s_saida ;
    }
    
    public void grafoKevinBacon() 
    {
        Queue<Vertice> fila = new LinkedList<>();
        Vertice bacon = null;

        // Encontra o vértice "Kevin Bacon"
        for (Vertice v : vertices) 
        {
            if (v.nome.equals("Bacon, Kevin")) 
            {
                bacon = v;
                break;
            }
        }

        // Caso Kevin Bacon não tenha sido encontrado.
        if (bacon == null) 
        {
            System.out.println("Kevin Bacon não encontrado.");
            return;
        }

        // Atribui o número 0 para Kevin Bacon
        numerosKevinBacon.put(bacon.nome, 0);
        
        for (Vertice adj : bacon.listaAdj) 
        {
            fila.add(adj) ;
        }
         
        int currentBaconNumber = 1 ;
        String aux = "" ;
        boolean contem = false ;
        
        while (!fila.isEmpty()) 
        {
            Vertice v = fila.remove() ;
            
            if (v.isFilme) 
            {
                if(!bacon.listaAdj.contains(v))
                {
                   for(Vertice adj : v.listaAdj)
                   {
                       if(numerosKevinBacon.get(adj.nome) != null)
                       {
                           aux = adj.nome ;
                           contem = true ;
                       }
                   }
                }
                if(contem == true)
                {
                    currentBaconNumber = numerosKevinBacon.get(aux)+1 ;
                    contem = false ;
                }
                
                numerosKevinBacon.put(v.nome, 0) ;
                
                for (Vertice adj : v.listaAdj) 
                {
                    Integer baconNumber = numerosKevinBacon.get(adj.nome) ;
                    
                    
                    if (baconNumber == null) 
                    {
                        numerosKevinBacon.put(adj.nome, currentBaconNumber) ;
                        fila.add(adj) ;
                        
                        if(adj.listaAdj.size() > 1)
                        {
                            for (Vertice a : adj.listaAdj) 
                            {
                                if(a.isFilme && a.num != v.num)
                                {
                                    fila.add(a) ;
                                }
                            }
                        }
                    }
                }  
            }
        }

        // Histograma dos números de Bacon dos atores
        System.out.println("Distribuição dos números de Kevin Bacon: ") ;
        Map<Integer, Integer> histograma = new HashMap<>() ;

        for (Vertice v : vertices) 
        {
            if (numerosKevinBacon.containsKey(v.nome) && v.isFilme == false) 
            {
                int baconNumber = numerosKevinBacon.get(v.nome) ;

                if (!histograma.containsKey(baconNumber)) 
                {
                    histograma.put(baconNumber, 0) ;
                }
                
                histograma.put(baconNumber, histograma.get(baconNumber) + 1) ;
            }
        }

        
        int total = 0 ;
        int countNull = 0 ;
        
        System.out.println("\nN.Bacon    Frequencia");
        System.out.println("----------------------");
        for (int i = 1; i <= 11; i++) 
        {
            int count = histograma.containsKey(i) ? histograma.get(i) : 0 ;
            System.out.printf("%d:            %d\n", i, count) ;
            total = total + count ;
        }
        
        countNull = numeroVertices - total ;
        
        System.out.printf("null:         " + countNull + "\n\n") ; 
    }
  

    public void atorBaconNumber(String nome1, String nome2) 
    {
        // Declaração das variáveis
        Vertice v1 = null, v2 = null;
        Map<String, Integer> numerosKevinBacon = new HashMap<>();
        Map<Vertice, Vertice> caminhoMinimo = new HashMap<>();
        Queue<Vertice> fila = new LinkedList<>();
        List<String> nomes = new ArrayList<>();
        int baconNumber = 0 ;

        // Criação do mapa verticesMap
        Map<String, Vertice> verticesMap = new HashMap<>();
        
        for (Vertice v : vertices) 
        {
            if(!v.isFilme)
            {
                verticesMap.put(v.nome, v) ;
                numerosKevinBacon.put(v.nome, -1) ;
            }
        }
        
        for (Vertice v : vertices) 
        {
            if (v.nome.equals(nome1)) 
            {
                v1 = v ;
            } 
            else if (v.nome.equals(nome2)) 
            {
                v2 = v ;
            }
            if (v1 != null && v2 != null) 
            {
                break ;
            }
        }

        // Verifica se ambos os vértices foram encontrados
        if (v1 == null || v2 == null) 
        {
            System.out.println("Não foi possível encontrar um ou ambos os atores/atrizes na base de dados.") ;
            return ;
        }

        // Cria um caminho mínimo entre v1 e v2, se existir
        v1.distancia = 0 ;
        v1.visita() ;
        fila.add(v1) ;
        
        while (!fila.isEmpty()) 
        {
            Vertice v = fila.remove() ;
             
            for (Vertice adj : v.listaAdj) 
            {
                if (!adj.foiVisitado()) 
                {
                    adj.distancia = v.distancia + 1;
                    adj.visita();
                    fila.add(adj);
                    caminhoMinimo.put(adj, v);
                }
            }
        }

        // Verifica se foi encontrado um caminho mínimo
        if (!caminhoMinimo.containsKey(v2)) 
        {
            System.out.println(nome1 + " e " + nome2 + " não possuem relação.");
            return;
        }

        // Imprime o caminho mínimo
        Vertice v = v2 ;
        nomes.add(v2.nome) ;
        
        while (v != v1) 
        {
            v = caminhoMinimo.get(v);
            nomes.add(v.nome);
            
            if(!v.isFilme)
            {
                baconNumber++ ;
            }
        }
        
        Collections.reverse(nomes) ; // Inverte a Ordem da Lista 'Nomes'.
        
        
        /* 
            Descomente o caminho e o seus print a seguir, para visualizar o caminho completo entre os atores.
        */ 
        //String caminho = String.join(" -> ", nomes);  
        //System.out.println(caminho) ;

        
        // Imprime o número de Bacon e a relação de trabalho
        System.out.println("\n" + nome1 + " tem um número Bacon de " + baconNumber) ;
        for (int i = 0; i < nomes.size() - 1; i++) 
        {
            String nomeA = nomes.get(i);
            String nomeB = nomes.get(i + 1);
            
            if(i%2 == 0)
            {
                System.out.println(nomeA + " fez " + nomeB + " com " + nomes.get(i+2));
            }
        }
    }
}


