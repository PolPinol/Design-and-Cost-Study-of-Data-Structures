package Model.Graf;

/**
 * Classe que implementa el node usat pels Grafs i les seves respectives funcions i procediments
 */
public class Node {
    public static final int INTEREST = 0;
    public static final int DANGER = 1;

    private int id;
    private String nom;
    private int tipus;
    private transient int idResumit;
    private transient boolean visitat;

    public Node(String id, String nom, String tipus) {
        this.id = Integer.parseInt(id);
        this.nom = nom;
        this.visitat = false;
        if (tipus.equals("INTEREST")) {
            this.tipus = INTEREST;
        } else {
            this.tipus = DANGER;
        }
    }

    public int getId() {
        return id;
    }

    public int getTipus() {
        return tipus;
    }

    public String getNom() {
        return nom;
    }

    public void setIdResumit(int idResumit) {
        this.idResumit = idResumit;
    }

    public int getIdResumit() {
        return idResumit;
    }

    public void marcar() {
        visitat = true;
    }

    public void desmarcar() {
        visitat = false;
    }

    public boolean isVisitat() {
        return visitat;
    }
}