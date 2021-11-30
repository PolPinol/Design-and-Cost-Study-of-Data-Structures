package Model.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe que gestiona tot el relacionat amb les crides dels arbres binaris
 */
public class Programa {
    private int numNodes;
    private Node [] nodes;
    private Arbre arbre;

    public Programa() {
        ArrayList<String> text = llegirFitxer("Trees/treeXS.paed");

        llegeixNodes(text);
        creaArbre();
    }

    /**
     * Funcio que busca el Node amb el valor en un rang
     */
    public ArrayList<String> cercaRang(long[] values) {
        ArrayList<String> llista;

        long valueMin = values[0];
        long valueMax = values[1];

        llista = arbre.bucarValorsNivells(valueMin, valueMax);

        return llista;
    }

    /**
     * Funcio que busca el Node amb el valor exacte
     * @param value: valor exacte a buscar
     * @return string que mostra si s'ha trobat un tresor amb el nom o no
     */
    public String cercaExacte(long value) {
        if(arbre.bucarValorNivells(value) == null){
            return "\nNo s'ha trobat cap tresor amb aquest valor.";
        }else {
            return "\nS'ha trobat un tresor amb aquest valor: " + arbre.bucarValorNivells(value);
        }
    }

    /**
     * Funcio que elimina un node
     * @param nom: nom del node a eliminar
     */
    public void eliminar(String nom) {
        arbre.eliminarNode(nom);
    }

    /**
     * Funcio amb diferents recorreguts segons la opció escollida
     * @param opcio: opció que especifica quin mètode fem servir per a buscar recorreguts
     * @return text: informació sobre els recorreguts obtinguts
     */
    public ArrayList<String> recorreguts(int opcio) {
        ArrayList<String> text = new ArrayList<>();

        switch(opcio){
            case 1:
                text = arbre.preordre();
                break;
            case 2:
                text = arbre.postordre();
                break;
            case 3:
                text = arbre.inordre();
                break;
            case 4:
                text = arbre.nivells();
                break;
        }
        return text;
    }

    /**
     * Funcio que insereix un node
     * @param nom: Nom del node a inserir
     * @param valor: Valor del node
     */
    public void inserir(String nom, String valor) {
        Node node;

        node = new Node(nom, Long.parseLong(valor));
        arbre.inserir(node);
        arbre.actualitzaBalanceig(node, Node.INSERINT);
    }

    /**
     * Funcio que crea un arbre binari
     */
    private void creaArbre() {
        arbre = new Arbre(nodes[0]);

        for (int i = 1; i < nodes.length; i++) {
            arbre.inserir(nodes[i]);
            arbre.actualitzaBalanceig(nodes[i], Node.INSERINT);
        }
    }

    /**
     * Lectura inicial del nodes del fitxer .paed
     * @param text: Nodes a llegir
     */
    private void llegeixNodes(ArrayList<String> text) {
        numNodes = Integer.parseInt(text.get(0));
        nodes = new Node[numNodes];
        for (int i = 1; i <= numNodes; i++) {
            String[] parts = text.get(i).split(",");
            long valor = Long.parseLong(parts[1]);
            nodes[i - 1] = new Node(parts[0], valor);
        }
    }

    public ArrayList<String> imprimirArbre() {
        return arbre.imprimirArbre();
    }

    /**
     * Lectura inicial del fitxer .paed
     * @param filePath: path del fitxer JSON a llegir
     * @return text: informació obtinguda del fitxer
     */
    private ArrayList<String> llegirFitxer(String filePath) {
        File archivo;
        FileReader fr;
        BufferedReader br;
        ArrayList<String> text = new ArrayList<>();

        try {
            archivo = new File (filePath);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linia;
            while((linia = br.readLine()) != null) {
                text.add(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
