package Model.Tree;

/**
 * Classe que implementa els nodes dels arbres binaris
 */
public class Node {
    public static final int DRETA = 0;
    public static final int ESQUERRA = 1;
    public static final int INSERINT = 0;
    public static final int ELIMINANT = 1;

    private String name;
    private long valor;
    private Node pare;
    private Node [] fills;
    private int factorBalanceig;

    public Node (String name, long valor) {
        this.name = name;
        this.valor = valor;
        this.fills = new Node[2];
        this.fills[0] = null;
        this.fills[1] = null;
        this.pare = null;
        this.factorBalanceig = 0;
    }

    /**
     * Actualitza el balanceig del pare quan in fill s'insereix o s'elimina
     * @param tipus: Especifica si s'està inserint i eliminant
     */
    public void actualitzaBalanceigPare(int tipus) {
        if (tipus == INSERINT) {
            if (this.pare.getFillDreta() == this) {
                this.pare.factorBalanceig++;
            }

            if (this.pare.getFillEsquerra() == this) {
                this.pare.factorBalanceig--;
            }
        } else {
            if (this.pare.getFillDreta() == this) {
                this.pare.factorBalanceig--;
            }

            if (this.pare.getFillEsquerra() == this) {
                this.pare.factorBalanceig++;
            }
        }
    }

    /**
     * Mètode per pujar el fill quan s'elimina un node
     * @param nodePujat
     * @param nodeEliminar
     */
    public void pujarFill(Node nodePujat, Node nodeEliminar) {
        if (fills[1] != null) {
            if (fills[1] == nodeEliminar) {
                fills[1] = nodePujat;
            }
        }

        if (fills[0] != null) {
            if (fills[0] == nodeEliminar) {
                fills[0] = nodePujat;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public long getValor() {
        return valor;
    }

    public void setFactorBalanceig(int factorBalanceig) {
        this.factorBalanceig = factorBalanceig;
    }

    public void setFillDreta(Node node) {
        this.fills[1] = node;
        if (node != null) {
            this.fills[1].pare = this;
        }
    }

    public void setFillEsquerra(Node node) {
        this.fills[0] = node;
        if (node != null) {
            this.fills[0].pare = this;
        }
    }

    public Node getFillDreta() {
        return fills[1];
    }

    public Node getFillEsquerra() {
        return fills[0];
    }

    public Node getPare() {
        return pare;
    }

    public int getFactorBalanceig() {
        return factorBalanceig;
    }

    public void removePare() {
        pare = null;
    }
}
