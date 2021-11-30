package Controller;

import Model.Taula.Programa;
import View.View;

import java.util.ArrayList;

/**
 * Classe que gestiona tot el relacionat amb els menús i fa les crides corresponents
 */
public class Controller {
    private static final String GRAFS = "1";
    private static final String ARBRES_BIN = "2";
    private static final String ARBRES_R = "3";
    private static final String TAULES = "4";
    private static final String SORTIR = "5";

    private static final String DFS = "A";
    private static final String BFS = "B";
    private static final String MST = "C";
    private static final String DIJKSTRA = "D";
    private static final String TORNAR_MENU_PRINCIPAL = "E";

    private static final String AFEGIR_TRESOR = "A";
    private static final String ELIMINAR_TRESOR = "B";
    private static final String LLISTAR_BOTI = "C";
    private static final String VALOR_EXACTE = "D";
    private static final String VALOR_RANG = "E";
    private static final String TORNAR_MENU_FROM_TREE = "F";

    private static final String AFEGIR_TRESOR_R = "A";
    private static final String ELIMINAR_TRESOR_R = "B";
    private static final String VISUALITZAR = "C";
    private static final String CERCA_AREA = "D";
    private static final String CERCA_PROX = "E";
    private static final String TORNAR_MENU_FROM_RTREE = "F";

    private static final String ADD_T_PIRATE = "A";
    private static final String DELETE_T_PIRATE = "B";
    private static final String CONSULT_T_PIRATE = "C";
    private static final String HISTOGRAM_T_PIRATE = "D";

    /**
     * Thread que inicia el funcionament constant els diferents menús
     * @throws CloneNotSupportedException
     */
    public void run() throws CloneNotSupportedException {
        View view = new View();
        String menu1 = "";

        while(!menu1.equals(SORTIR)) {
            menu1 = view.menuInicial();

            switch (menu1) {
                case GRAFS:
                    switchMenuRutes(view);
                    break;
                case ARBRES_BIN:
                    switchMenuArbres(view);
                    break;
                case ARBRES_R:
                    switchMenuRTree(view);
                    break;
                case TAULES:
                    switchMenuTaules(view);
                    break;
            }
        }
        view.missatgeSortida();
        System.exit(0);
    }

    /**
     * Gestiona el menú de les rutes
     * @param view: vista a la qual fa les crides pertinents
     */
    private void switchMenuRutes(View view) {
        String menu2 = "";
        Model.Graf.Programa programa = new Model.Graf.Programa();

        while(!menu2.equals(TORNAR_MENU_PRINCIPAL)) {
            menu2 = view.menuDeLesRutes();

            switch (menu2) {
                case DFS:
                    //es demana el node origen a l'usuari per fer el DFS
                    int nodeD = view.missatgeBuscaNode();
                    view.mostraDFS(programa.DFS(nodeD));
                    break;
                case BFS:
                    //es demana el node origen a l'usuari per fer el BFS
                    int nodeB = view.missatgeBuscaNode();
                    view.mostrarBFS(programa.BFS(nodeB));
                    break;
                case MST:
                    programa.creaIdsCorrectes();
                    view.mostrarGraf(programa.MST().mostrarGrafIDs());
                    break;
                case DIJKSTRA:
                    int nodeInici = view.missatgeBuscaNode();
                    int nodeFinal = view.missatgeBuscaNodeDesti();
                    ArrayList<Integer> dijkstraCami = programa.dijkstra(nodeInici, nodeFinal);
                    view.mostrarDijkstra(dijkstraCami);
                    break;
            }
        }
    }

    /**
     * Gestiona el menú dels arbres binaris
     * @param view: vista a la qual fa les crides pertinents
     */
    private void switchMenuArbres(View view) {
        String menuArbres = "";
        Model.Tree.Programa programa = new Model.Tree.Programa();
        view.imprimirArbreBinari(programa.imprimirArbre());

        while(!menuArbres.equals(TORNAR_MENU_FROM_TREE)){
            menuArbres = view.menuArbres();

            switch (menuArbres) {
                case AFEGIR_TRESOR:
                    String[] valors = view.demanaInsercio();
                    programa.inserir(valors[0], valors[1]);
                    view.mostraSuccess(1);
                    break;
                case ELIMINAR_TRESOR:
                    programa.eliminar(view.demanaEliminacio());
                    view.mostraSuccess(2);
                    break;
                case LLISTAR_BOTI:
                    int opcio = view.menuRecorreguts();
                    ArrayList<String> text;
                    text = programa.recorreguts(opcio);
                    view.mostraRecorreguts(text);
                    break;
                case VALOR_EXACTE:
                    long value = view.demanarValor();
                    view.mostraValor(programa.cercaExacte(value));
                    break;
                case VALOR_RANG:
                    view.mostraValorRang(programa.cercaRang(view.demanarMinMaxValue()));
                    break;
            }
        }
    }

    /**
     * Gestiona el menú dels arbres recursius
     * @param view: vista a la qual fa les crides pertinents
     */
    private void switchMenuRTree(View view) {
        String menuRTree = "";
        Model.RTree.Programa programa = new Model.RTree.Programa();

        while(!menuRTree.equals(TORNAR_MENU_FROM_RTREE)){
            menuRTree = view.menuRTree();

            switch (menuRTree){
                case AFEGIR_TRESOR_R:
                    programa.inserir(view.demanaInsercioArbreR());
                    view.insercioCorrecta();
                    break;
                case ELIMINAR_TRESOR_R:
                    boolean correcta = programa.eliminar(view.demanaEliminarArbreR());
                    view.missatgeEliminar(correcta);
                    break;
                case VISUALITZAR:
                    view.representantCobertura();
                    programa.recorreArbre();
                    view.createRTreePanel(programa.getPunts(), programa.getRectangles());
                    break;
                case CERCA_AREA:
                    String punt1 = view.demanaPunt1();
                    String punt2 = view.demanaPunt2();
                    view.mostraLlistaPuntsArea(programa.cercaArea(punt1, punt2));
                    break;
                case CERCA_PROX:
                    String[] text = view.demanaPuntProximitat();
                    view.mostraLlistaPuntsProximitat(programa.cercaProximitat(text));
                    break;
            }
        }
    }

    /**
     * Gestiona el menú de les taules
     * @param view: vista a la qual fa les crides pertinents
     */
    private void switchMenuTaules(View view){
        String menu2 = "";
        Programa programa = new Programa();

        while(!menu2.equals(TORNAR_MENU_PRINCIPAL)) {
            menu2 = view.menuDeLesTaules();

            switch (menu2) {
                case ADD_T_PIRATE:
                    ArrayList<String> newPirate = view.savePirateInfo();
                    programa.addNewPirate(newPirate);

                    String[] data = programa.searchPirate(newPirate.get(0));
                    if(data[0] != null){
                        view.messagePirateAdded();
                    }
                    break;
                case DELETE_T_PIRATE:
                    String pirate = view.saveInfoToDeletePirate();
                    if (programa.deletePirate(pirate)) {
                        view.messagePirataDeleted();
                    } else {
                        view.messagePirataError();
                    }
                    break;
                case CONSULT_T_PIRATE:
                    String search = view.pirateToSearch();
                    String[] data2 = programa.searchPirate(search);
                    if(data2.length > 0){
                        view.showPirate(data2);
                    }
                    break;
                case HISTOGRAM_T_PIRATE:
                    view.showGraphics(programa.getInformationForGraphic());
                    break;
            }
        }
    }
}