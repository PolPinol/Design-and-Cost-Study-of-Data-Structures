package Model.Tree;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Classe que implementa els arbres binaris i les seves funcions necessàries
 */
public class Arbre {
    public static final int DRETA = 0;
    public static final int ESQUERRA = 1;
    public static final int RR = 0;
    public static final int LL = 0;
    public static final int LR = 1;
    public static final int RL = 1;

    private Node arrel;
    private ArrayList<String> arbreEnText;

    public Arbre(Node node) {
        this.arrel = node;
        this.arbreEnText = new ArrayList<>();
    }

    /**
     * Procediment que crida el balanceig per a eliminar un node
     * @param node: Node a eliminar
     */
    public void balanceigEliminant(Node node) {
        balanceigEliminantsRecursiu(node);
    }

    /**
     * Procediment que elimina el node i fa el balanceig
     * @param node: Node a eliminar
     */
    private void balanceigEliminantsRecursiu(Node node) {
        if (node.getPare() != null) {
            node.actualitzaBalanceigPare(Node.ELIMINANT);
            if (node.getPare().getFactorBalanceig() == 0) {
                pujarPareRecursiu(node.getPare(), Node.ELIMINANT);
            }
        }
    }

    /**
     * Procediment que fa la crida del balanceig recursiu
     * @param node: Node a balancejar
     * @param tipus: Tipus de balanceig (eliminar o inserir)
     */
    public void actualitzaBalanceig(Node node, int tipus) {
        pujarPareRecursiu(node, tipus);
    }

    /**
     * Procediment que puja el pare de forma recursiva
     * @param node: Node a pujar
     * @param tipus: inserció o eliminació
     */
    private void pujarPareRecursiu(Node node, int tipus) {
        if (node.getPare() != null) {
            node.actualitzaBalanceigPare(tipus);
            if (optimitzaArbre(node)) {
                if (node.getFactorBalanceig() == 1 || node.getFactorBalanceig() == -1) {
                    pujarPareRecursiu(node.getPare(), tipus);
                }
            } else {
                if (tipus == Node.INSERINT) {
                    if (node.getPare().getFactorBalanceig() != 0) {
                        pujarPareRecursiu(node.getPare(), tipus);
                    }
                } else {
                    if (node.getPare().getFactorBalanceig() == 0) {
                        pujarPareRecursiu(node.getPare(), tipus);
                    }
                }
            }
        }
    }

    /**
     * Optimitza el Arbre i fa les rotacions que calguin
     * @param node: Node a rotar
     * @return boolean: Indica si s'ha realitzat de forma correcta
     */
    private boolean optimitzaArbre(Node node) {
        int valorBalanceigNode = node.getFactorBalanceig();

        if (valorBalanceigNode == 2 && node.getPare() == null) {
            return optimitzaArbre(node.getFillDreta());
        } else if (valorBalanceigNode == -2 && node.getPare() == null) {
            return optimitzaArbre(node.getFillEsquerra());
        } else if (node.getPare() == null) {
            return true;
        }

        int valorBalanceigPare = node.getPare().getFactorBalanceig();

        if (valorBalanceigPare == 2 && valorBalanceigNode == 0) {
            return optimitzaArbre(node.getPare().getFillDreta());
        } else if (valorBalanceigPare == -2 && valorBalanceigNode == 0) {
            return optimitzaArbre(node.getPare().getFillEsquerra());
        } else if (valorBalanceigPare == 2 && valorBalanceigNode == 1) {
            rotacioRR(node, RR);
            return true;
        } else if (valorBalanceigPare == 2 && valorBalanceigNode == -1) {
            rotacioRL(node);
            return true;
        } else if (valorBalanceigPare == -2 && valorBalanceigNode == 1) {
            rotacioLR(node);
            return true;
        } else if (valorBalanceigPare == -2 && valorBalanceigNode == -1) {
            rotacioLL(node, LL);
            return true;
        }
        return false;
    }

    /**
     * Procediment que elimina un node
     * @param nomNodeEliminar: nom del node a eliminar
     */
    public void eliminarNode(String nomNodeEliminar) {
        Node node = bucarNomNivells(nomNodeEliminar);

        if (node != null) {
            eliminarNode(node);
        }
    }

    /**
     * Elimina un node
     * @param node: node a eliminar
     */
    public void eliminarNode(Node node) {
        Node pare;

        // Eliminar quan el node no te cap fill
        if (node.getFillDreta() == null && node.getFillEsquerra() == null) {
            actualitzaBalanceig(node, Node.ELIMINANT);
            pare = node.getPare();
            if (node.getPare().getFillDreta() == node) {
                pare.setFillDreta(null);
            } else {
                pare.setFillEsquerra(null);
            }
        // Eliminar quan el node te un fill
        } else if (node.getFillDreta() == null) {
            balanceigEliminant(node);
            pare = node.getPare();
            if (pare != null) {
                pare.pujarFill(node.getFillEsquerra(), node);
                optimitzaArbre(pare);
            } else {
                arrel = node.getFillEsquerra();
                node.getFillEsquerra().removePare();
                if (arrel.getFillDreta() != null) {
                    optimitzaArbre(arrel.getFillDreta());
                }
            }
        // Eliminar quan el node te un fill
        } else if(node.getFillEsquerra() == null) {
            balanceigEliminant(node);
            pare = node.getPare();
            if (pare != null) {
                pare.pujarFill(node.getFillDreta(), node);
                optimitzaArbre(pare);
            } else {
                arrel = node.getFillDreta();
                node.getFillDreta().removePare();
                if (arrel.getFillEsquerra() != null) {
                    optimitzaArbre(arrel.getFillEsquerra());
                }
            }
        // Eliminar quan el node te els dos fills (eliminar i substituir)
        } else {
            Node nodeSuccesor = inordreSenseFillEsquerra(node.getFillDreta());
            String nomNodeSuccessor = nodeSuccesor.getName();
            long valueNodeSuccessor = nodeSuccesor.getValor();

            eliminarNode(nodeSuccesor);

            node.setName(nomNodeSuccessor);
            node.setValor(valueNodeSuccessor);
        }
    }

    /**
     * Realitza una rotació LL
     * @param node: node a rotar
     * @param tipus: tipus (inserció o eliminació)
     */
    private void rotacioLL(Node node, int tipus) {
        Node D;
        Node x;
        Node y;
        Node T1, T2, T3, T4;
        Node pareD;

        // Copies
        D = node.getPare();
        x = node.getFillEsquerra();
        y = node;

        T1 = x.getFillEsquerra();
        T2 = x.getFillDreta();
        T3 = y.getFillDreta();
        T4 = D.getFillDreta();
        pareD = D.getPare();

        // Swap
        y.setFillEsquerra(x);
        y.setFillDreta(D);
        x.setFillEsquerra(T1);
        x.setFillDreta(T2);
        D.setFillEsquerra(T3);
        D.setFillDreta(T4);

        // Assignar nou pare general
        if (pareD == null) {
            arrel = y;
            y.removePare();
        } else if (pareD.getFillEsquerra() == D) {
            pareD.setFillEsquerra(y);
        } else {
            pareD.setFillDreta(y);
        }

        // Actualitzar Factors Balanceig
        D.setFactorBalanceig(0);
        if (tipus == LL) {
            y.setFactorBalanceig(0);
        } else {
            x.setFactorBalanceig(0);
        }
    }

    /**
     * Realitza una rotació RR
     * @param node: node a rotar
     * @param tipus: inserció o eliminació
     */
    private void rotacioRR(Node node, int tipus) {
        Node D;
        Node x;
        Node y;
        Node T1, T2, T3, T4;
        Node pareD;

        // Copies
        D = node.getPare();
        x = node;
        y = node.getFillDreta();
        T1 = D.getFillEsquerra();
        T2 = x.getFillEsquerra();
        T3 = y.getFillEsquerra();
        T4 = y.getFillDreta();
        pareD = D.getPare();

        // Swap
        x.setFillEsquerra(D);
        x.setFillDreta(y);
        D.setFillEsquerra(T1);
        D.setFillDreta(T2);
        y.setFillEsquerra(T3);
        y.setFillDreta(T4);

        // Assignar nou pare general
        if (D == arrel) {
            arrel = x;
            x.removePare();
        } else if (pareD.getFillEsquerra() == D) {
            pareD.setFillEsquerra(x);
        } else {
            pareD.setFillDreta(x);
        }


        // Actualitzar Factors Balanceig
        D.setFactorBalanceig(0);
        if (tipus == RR) {
            x.setFactorBalanceig(0);
        } else {
            y.setFactorBalanceig(0);
        }
    }

    /**
     * Realitza una rotació LR
     * @param node: node a rotar
     */
    private void rotacioLR(Node node) {
        Node D;
        Node x;
        Node y;
        Node T1, T2, T3, T4;

        // Copies
        D = node.getPare();
        x = node;
        y = node.getFillDreta();
        T1 = x.getFillEsquerra();
        T2 = y.getFillEsquerra();
        T3 = y.getFillDreta();
        T4 = D.getFillDreta();

        // Swap
        D.setFillDreta(T4);
        D.setFillEsquerra(y);
        y.setFillEsquerra(x);
        y.setFillDreta(T3);
        x.setFillEsquerra(T1);
        x.setFillDreta(T2);

        rotacioLL(y, LR);
    }

    /**
     * Realitza una rotació RL
     * @param node: node a rotar
     */
    private void rotacioRL(Node node) {
        Node D;
        Node x;
        Node y;
        Node T1, T2, T3, T4;

        // Copies
        D = node.getPare();
        x = node.getFillEsquerra();
        y = node;
        T1 = D.getFillEsquerra();
        T2 = x.getFillEsquerra();
        T3 = x.getFillDreta();
        T4 = y.getFillDreta();

        // Swap
        D.setFillEsquerra(T1);
        D.setFillDreta(x);
        x.setFillEsquerra(T2);
        x.setFillDreta(y);
        y.setFillEsquerra(T3);
        y.setFillDreta(T4);

        rotacioRR(x, RL);
    }

    /**
     * Funcio que retorna el node mes proxim per inordre que no te fill esquerra
     * @param node: node a buscar
     * @return node: node especificat
     */
    private Node inordreSenseFillEsquerra(Node node) {
        if (node.getFillEsquerra() == null) {
            return node;
        } else {
            return inordreSenseFillEsquerra(node.getFillEsquerra());
        }
    }

    /**
     * Funcio que busca el Node amb rang de valors per nivells en l'arbre
     * @param valueMinim: valor mínim a buscar
     * @param valueMaxim: valor màxim a buscar
     * @return llista: llista dels nodes obtinguts
     */
    public ArrayList<String> bucarValorsNivells(long valueMinim, long valueMaxim) {
        ArrayList<Node> cua = new ArrayList<>();
        ArrayList<String> llista = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            if (valueMinim <= nou.getValor() && nou.getValor() <= valueMaxim) {
                llista.add(escriureNodeEnLinia(nou));
            }

            if (nou.getFillEsquerra() != null) {
                if (!(nou.getValor() <= valueMinim)) {
                    cua.add(nou.getFillEsquerra());
                }
            }
            if (nou.getFillDreta() != null) {
                if (!(valueMaxim <= nou.getValor())) {
                    cua.add(nou.getFillDreta());
                }
            }
        }

        return llista;
    }

    /**
     * Funcio que busca el Node amb el valor per nivells en l'arbre
     * @param value: valor a buscar
     * @return nou: node trobat
     */
    public String bucarValorNivells(long value) {
        ArrayList<Node> cua = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            if (nou.getValor() == value) {
                return nou.getName();
            }

            if (nou.getFillEsquerra() != null) {
                cua.add(nou.getFillEsquerra());
            }
            if (nou.getFillDreta() != null) {
                cua.add(nou.getFillDreta());
            }

        }

        return null;
    }

    /**
     * Funcio que busca el Nodem amb el nom per nivells en l'arbre
     * @param nom: node a buscar
     * @return nou: node esmentat
     */
    private Node bucarNomNivells(String nom) {
        ArrayList<Node> cua = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            if (nou.getName().equals(nom)) {
                return nou;
            }

            if (nou.getFillEsquerra() != null) {
                cua.add(nou.getFillEsquerra());
            }
            if (nou.getFillDreta() != null) {
                cua.add(nou.getFillDreta());
            }

        }

        return null;
    }

    /**
     * Funcio que inicialitza la funcio de preordre recursiva
     * @return nodes: Nodes obtinguts
     */
    public ArrayList<String> preordre() {
        ArrayList<String> nodes = new ArrayList<>();
        preordreRecursiu(arrel, nodes);
        return nodes;
    }

    /**
     * Funcio que retorna l'arbre en preordre
     * @param node: node a buscar
     * @param nodes: nodes amb els que es treballa
     */
    private void preordreRecursiu(Node node, ArrayList<String> nodes) {
        nodes.add(escriureNodeEnLinia(node));
        if (node.getFillEsquerra() != null) {
            preordreRecursiu(node.getFillEsquerra(), nodes);
        }

        if (node.getFillDreta() != null) {
            preordreRecursiu(node.getFillDreta(), nodes);
        }
    }

    /**
     * Funcio que retorna l'arbre en postordre
     * @return: arbre ordenat
     */
    public ArrayList<String> postordre() {
        ArrayList<String> nodes = new ArrayList<>();
        postordreRecursiu(arrel, nodes);
        return nodes;
    }

    /**
     * Funcio que retorna l'arbre en postordre
     * @param node: node a buscar
     * @param nodes: nodes a retornar
     */
    private void postordreRecursiu(Node node, ArrayList<String> nodes) {
        if (node.getFillEsquerra() != null) {
            postordreRecursiu(node.getFillEsquerra(), nodes);
        }

        if (node.getFillDreta() != null) {
            postordreRecursiu(node.getFillDreta(), nodes);
        }

        nodes.add(escriureNodeEnLinia(node));
    }

    /**
     * Funcio que retorna l'arbre en inordre
     * @return nodes: arbre ordenat
     */
    public ArrayList<String> inordre() {
        ArrayList<String> nodes = new ArrayList<>();
        inordreRecursiu(arrel, nodes);
        return nodes;
    }

    /**
     * Funcio que retorna l'arbre en inordre
     * @param node: node a buscar
     * @param nodes: arbre a ordenar
     */
    private void inordreRecursiu(Node node, ArrayList<String> nodes) {
        if (node.getFillEsquerra() != null) {
            inordreRecursiu(node.getFillEsquerra(), nodes);
        }

        nodes.add(escriureNodeEnLinia(node));

        if (node.getFillDreta() != null) {
            inordreRecursiu(node.getFillDreta(), nodes);
        }
    }

    /**
     * Funcio que retorna l'arbre per nivells
     * @return nodes: arbre ordenat
     */
    public ArrayList<String> nivells() {
        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<Node> cua = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            nodes.add(escriureNodeEnLinia(nou));

            if (nou.getFillEsquerra() != null) {
                cua.add(nou.getFillEsquerra());
            }
            if (nou.getFillDreta() != null) {
                cua.add(nou.getFillDreta());
            }

        }

        return nodes;
    }

    /**
     * Escriu un node en línia als demés
     * @param node: Node a escriure
     * @return string: node ja escrit correctament
     */
    private String escriureNodeEnLinia(Node node) {
        return node.getName() + " - " + NumberFormat.getNumberInstance(Locale.GERMAN).format(node.getValor()) + " doblons";
    }

    /**
     * Funcio que inicialitza la funcio recursiva per inserir un node
     * @param nodeInserir: node a inserir de forma recursiva
     */
    public void inserir(Node nodeInserir) {
        inserirRecursiu(arrel, nodeInserir);
    }

    /**
     * Funcio que insereix un node
     * @param node: node pare
     * @param nodeInserir: node a inserir
     */
    private void inserirRecursiu(Node node, Node nodeInserir) {
        long valor = nodeInserir.getValor();

        if (valor < node.getValor()) {
            if (node.getFillEsquerra() == null) {
                node.setFillEsquerra(nodeInserir);
            } else {
                inserirRecursiu(node.getFillEsquerra(), nodeInserir);
            }
        }

        if (valor > node.getValor()) {
            if (node.getFillDreta() == null) {
                node.setFillDreta(nodeInserir);
            } else {
                inserirRecursiu(node.getFillDreta(), nodeInserir);
            }
        }
    }

    /**
     * Imprimeix l'Arbre
     */
    public ArrayList<String> imprimirArbre() {
        arbreEnText = new ArrayList<>();
        recorreArbre(arrel,0, 0);
        ompleLinies();

        return arbreEnText;
    }

    /**
     * Funcio Recursiva per recorre arbre per imprimir
     * @param node: node a recórrer
     * @param fila: fila en la que es troba
     * @param tipus: tipus de node
     */
    public void recorreArbre(Node node, int fila, int tipus) {
        int countNullDreta = 0;
        int countNullEsquerra = 0;
        int countNull = 0;

        imprimirFila(String.valueOf(node.getValor()), fila, tipus);

        if (node.getFillDreta() == null) {
            countNullDreta++;
        }

        if (node.getFillEsquerra() == null) {
            countNullEsquerra++;
        }

        if (countNullDreta == 0) {
            recorreArbre(node.getFillDreta(), fila + 1, DRETA);
        } else {
            imprimirNull("├── X", fila);
        }

        if (countNullEsquerra == 0) {
            recorreArbre(node.getFillEsquerra(), fila + 1, ESQUERRA);
        }

        countNull = countNullDreta + countNullEsquerra;
        if (countNullEsquerra == 1) {
            imprimirNull("└── X", fila);
        } else if (countNull == 2) {
            imprimirNull("├── X", fila);
            imprimirNull("└── X", fila);
        }
    }

    /**
     * Imprimeix fila
     * @param valor: valor a mostrar
     * @param fila: fila on es troba
     * @param tipus: tipus
     */
    public void imprimirFila(String valor, int fila, int tipus) {
        StringBuilder text = new StringBuilder();

        if (fila != 0) {
            text.append("    ".repeat(Math.max(0, (fila - 1))));

            if (tipus == ESQUERRA) {
                text.append("└── ");
            } else if (tipus == DRETA) {
                text.append("├── ");
            } else {
                text.append("    ");
            }
        }

        text.append(valor);
        arbreEnText.add(text.toString());
    }

    /**
     * Imprimeix fila omplint els null amb X
     * @param valor: valor a imprimir
     * @param fila: numero de fila
     */
    public void imprimirNull(String valor, int fila) {
        String text = "    ".repeat(Math.max(0, fila)) + valor;
        arbreEnText.add(text);
    }

    /**
     * Funcio que busca espais buits del text generat per la funcio imprimirArbre per ferho mes visible a la vista el arbre
     */
    public void ompleLinies() {
        for (int i = 0; i < arbreEnText.size(); i++) {
            for (int j = 0; j < (arbreEnText.get(i).length() - 3); j++) {
                String cadena = Character.toString(arbreEnText.get(i).charAt(j)) + Character.toString(arbreEnText.get(i).charAt(j+1)) + Character.toString(arbreEnText.get(i).charAt(j+2));
                if (cadena.equals("├──")) {
                    reemplasaEspaisBuitsPerLinies(j, j+1, j+2, i);
                }
            }
        }
    }

    /**
     * Funcio que busca espais buits i ho substitueix per linies per ferho mes visible per la vista
     * @param posicio1: Primera posició
     * @param posicio2: Segona posició
     * @param posicio3: Tercera posició
     * @param comensaLinia: Començament de la línia
     */
    private void reemplasaEspaisBuitsPerLinies(int posicio1, int posicio2, int posicio3, int comensaLinia) {
        for (int i = comensaLinia+1; i < arbreEnText.size(); i++) {
            if (arbreEnText.get(i).charAt(posicio1) == '└') {
                break;
            }
            String text = arbreEnText.get(i);
            char[] textChars = text.toCharArray();
            textChars[posicio1] = '│';
            textChars[posicio2] = ' ';
            textChars[posicio3] = ' ';

            arbreEnText.set(i, String.valueOf(textChars));
        }
    }

    public Node getArrel() {
        return arrel;
    }

}
