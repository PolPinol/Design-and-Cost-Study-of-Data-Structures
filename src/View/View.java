package View;

import Entities.Point;
import Entities.Rectangle;
import Model.Taula.GraphicsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe on tenim tots els procediments i funcions que mostren quelcom per pantalla
 */
public class View {

    /**
     * Mostra per consola el menú principal i controla la opció escollida per l'usuari
     * @return option: retorna la opció vàlida seleccionada per l'usuari
     */
    public String menuInicial(){
        Scanner opc = new Scanner(System.in);

        System.out.println("\n-= Pirates del Mediterrani=-\n");
        System.out.println("1. Rutes (Grafs)");
        System.out.println("2. Inventari (Arbres binaris)");
        System.out.println("3. Coberta (Arbres R)");
        System.out.println("4. Tripulació (Taules)\n");
        System.out.println("5. Sortir\n");

        System.out.print("Escull una opció: ");
        String option;
        option = opc.nextLine();

        System.out.println(" ");

        while(!option.equals("1") & !option.equals("2") & !option.equals("3") & !option.equals("4") & !option.equals("5")){
            System.out.println("ERROR! Opció incorrecta\n");
            System.out.print("Escull una opció: ");
            option = opc.nextLine();
            System.out.println(" ");
        }

        return option;
    }

    /**
     * Mostra un missatge per despedir-se quan l'usuari surt del programa
     */
    public void missatgeSortida(){
        System.out.println("\nA reveure camarada!");
    }

    /**
     * Mostra el menú de les rutes i gestiona les opcions de l'usuari
     * @return option: retorna la opció vàlida seleccionada de l'usuari
     */
    public String menuDeLesRutes() {
        Scanner opc = new Scanner(System.in);

        System.out.println("\n\tA. Cercar llocs d'interès (DFS)");
        System.out.println("\tB. Cercar llocs perillosos (BFS)");
        System.out.println("\tC. Mostrar la Carta Nàutica Universal (MST)");
        System.out.println("\tD. Trobar la ruta òptima (Dijkstra)\n");
        System.out.println("\tE. Tornar enrere\n");

        System.out.print("Quina funcionalitat vols executar? ");
        String option;
        option = opc.nextLine();

        while(!option.equals("A") & !option.equals("B") & !option.equals("C") & !option.equals("D") & !option.equals("E")){
            System.out.println("\nERROR! Opció incorrecta\n");
            System.out.print("Quina funcionalitat vols executar? ");
            option = opc.nextLine();
        }

        return option;
    }

    /**
     * Mostra el menú dels arbres binaris i gestiona l'opció de l'usuari
     * @return option: retorna la opció vàlida seleccionada per l'usuari
     */
    public String menuArbres(){
        Scanner opc = new Scanner(System.in);
        String option;

        System.out.println("\tA. Afegir tresor\n" +
                "\tB. Eliminar tresor\n" +
                "\tC. Llistar botí\n" +
                "\tD. Cerca per valor (exacte)\n" +
                "\tE. Cerca per valor (rang)\n\n" +
                "\tF. Tornar enrere\n");

        System.out.print("Quina funcionalitat vols executar? ");
        option = opc.nextLine();

        while(!option.equals("A") & !option.equals("B") & !option.equals("C") & !option.equals("D") & !option.equals("E") & !option.equals("F")){
            System.out.println("\nERROR! Opció incorrecta\n");
            System.out.print("Quina funcionalitat vols executar? ");
            option = opc.nextLine();
        }
        return option;
    }

    /**
     * Demana un valor a l'usuari
     * @return value: valor introduït per l'usuari
     */
    public long demanarValor(){
        Scanner opc = new Scanner(System.in);
        long value;

        System.out.print("\nEntra el valor a cercar: ");
        value = opc.nextLong();

        return value;
    }

    /**
     * Demana els valors mínims i màxims a l'usuari
     * @return values[]: els dos valors (mínim i màxim) que escull l'usuari
     */
    public long[] demanarMinMaxValue(){
        Scanner opc = new Scanner(System.in);
        long[] values = new long[2];

        System.out.print("\nEntra el valor mínim a cercar: ");
        values[0] = opc.nextLong();
        System.out.print("\nEntra el valor màxim a cercar: ");
        values[1] = opc.nextLong();

        return values;
    }

    /**
     * Demana el node origen a buscar a l'usuari
     * @return ident: identificador del node a buscar
     */
    public int missatgeBuscaNode(){
        Scanner ident = new Scanner(System.in);

        System.out.print("\nEntra l'identificador del node origen: ");
        return ident.nextInt();
    }

    /**
     * Demana el node destí a l'usuari
     * @return ident: identificador del node destí
     */
    public int missatgeBuscaNodeDesti(){
        Scanner ident = new Scanner(System.in);

        System.out.print("\nEntra l'identificador del node destí: ");
        return ident.nextInt();
    }

    /**
     * Demana totes les dades necessàries del nou node a inserir
     * @return valors[]: informació sobre el node a inserir
     */
    public String[] demanaInsercio(){
        System.out.println(" ");
        Scanner opc = new Scanner(System.in);
        String[] valors = new String[2];

        System.out.print("Entra el nom del tresor a afegir: ");
        valors[0] = opc.nextLine();

        System.out.print("Entra el valor del tresor a afegir: ");
        valors[1] = opc.nextLine();

        return valors;
    }

    /**
     * Demana el nom del tresor que es desitja eliminar
     * @return nom: nom del tresor a eliminar
     */
    public String demanaEliminacio(){
        System.out.println(" ");
        Scanner opc = new Scanner(System.in);
        String nom;

        System.out.print("Entra el nom del tresor a eliminar: ");
        nom = opc.nextLine();

        return nom;
    }

    /**
     * Mostra el menú dels recorreguts i gestiona la opció seleccionada per l'usuari
     * @return opcio: opció vàlida seleccionada per l'usuari
     */
    public int menuRecorreguts(){
        Scanner opcio = new Scanner(System.in);

        System.out.print("\n\tI. Preordre\n\tII. Postordre\n\tIII. Inordre\n\tIV. Per nivells\n\nQuin recorregut vols fer servir? ");
        String opt = opcio.nextLine();
        opt = opt.toLowerCase();
        while(!opt.equals("i") && !opt.equals("ii") && !opt.equals("iii") && !opt.equals("iv")) {
            System.out.println("\nERROR! Opció incorrecta\n");
            System.out.print("Quin recorregut vols fer servir? ");
            opt = opcio.nextLine();
            opt = opt.toLowerCase();
        }

        switch(opt){
            case "i":
                return 1;
            case "ii":
                return 2;
            case "iii":
                return 3;
            case "iv":
                return 4;
        }
        return 0;
    }

    /**
     * Procediment que mostra per consola els llocs d'interès trobats per DFS
     * @param node: Nodes amb els quals buscarem els llocs d'interès
     */
    public void mostraDFS(ArrayList<Model.Graf.Node> node){
        System.out.println("\nAmb DFS s'han trobat els següents llocs d'interès:\n ");

        for (int i = 0; i < node.size(); i++) {
            System.out.print("\t" + node.get(i).getNom() + "\n");
        }

        System.out.println(" ");
    }

    /**
     * Procediment que mostra per consola els llocs perillosos trobats per DFS
     * @param danger: Nodes amb els quals buscarem els llocs perillosos
     */
    public void mostrarBFS(ArrayList<Model.Graf.Node> danger){
        System.out.println("\nAmb BFS s'han trobat els següents llocs perillosos:\n");

        for (int i = 0; i < danger.size(); i++) {
            System.out.print("\t" + danger.get(i).getNom() + "\n");
        }

        System.out.println(" ");
    }

    /**
     * Procediment amb el qual mostrem per consola el MST dels grafs
     * @param graf: Tots els grafs amb els quals treballarem
     */
    public void mostrarGraf(HashMap<Integer, ArrayList<Integer>> graf){
        ArrayList<Integer> keys = new ArrayList<>(graf.keySet());
        System.out.println("\nObtenint el MST...\n");
        for (int i = 0; i < keys.size(); i++) {
            ArrayList<Integer> nodesAdj = graf.get(keys.get(i));
            System.out.print(keys.get(i) + " ->");
            for (int j = 0; j < nodesAdj.size(); j++) {
                if (j == 0) {
                    System.out.print(" " + nodesAdj.get(j));
                } else {
                    System.out.print(", " + nodesAdj.get(j));
                }
            }
            System.out.println("");
        }
    }

    /**
     * Procediment que obté la ruta òptima usant Dijkstra
     * @param ints: Valors amb els quals mostrem la ruta usada per Dijkstra
     */
    public void mostrarDijkstra(ArrayList<Integer> ints){
        System.out.println("\nObtenint la ruta òptima...\n");
        for (int i = 0; i < ints.size() - 1; i++) {
            System.out.print(ints.get(i) + " -> ");
        }
        System.out.print(ints.get(ints.size() - 1));
        System.out.println(" ");
    }

    /**
     * Menú que mostra les opcions de les taules i gestiona la opció introduïda per l'usuari
     * @return option: opció vàlida escollida per l'usuari
     */
    public String menuDeLesTaules(){
        Scanner opc = new Scanner(System.in);
        String option;

        System.out.println("\n    A. Afegir pirata");
        System.out.println("    B. Eliminar pirata");
        System.out.println("    C. Consultar pirata");
        System.out.println("    D. Histograma per edats\n");
        System.out.println("    E. Tornar enrere\n");

        System.out.print("Quina funcionalitat vols executar? ");
        option = opc.nextLine();

        while(!option.equals("A") & !option.equals("B") & !option.equals("C") & !option.equals("D") & !option.equals("E")){
            System.out.println("\nERROR! Opció incorrecta\n");
            System.out.print("Quina funcionalitat vols executar? ");
            option = opc.nextLine();
        }

        return option;
    }

    /**
     * Funció que demana la informació necessària sobre un pirata i la retorna
     * @return data[]: tota la informació requerida sobre un pirata
     */
    public ArrayList<String> savePirateInfo(){
        Scanner opc = new Scanner(System.in);
        ArrayList<String> data = new ArrayList<>();
        String data1;
        String data2;

        System.out.print("\nEntra el nom del pirata a afegir: ");
        data.add(opc.nextLine());
        System.out.print("Entra l'edat del pirata a afegir: ");
        data1 = opc.nextLine();
        System.out.print("Entra el rol del pirata a afegir: ");
        data2 = opc.nextLine();

        data.add(data1 + "," + data2);

        return data;
    }

    /**
     * Missatge que confirma que un pirata s'ha afegit de forma correcta
     */
    public void messagePirateAdded(){
        System.out.println("\nEl pirata s'ha afegit correctament a la tripulació.");
    }

    /**
     * Funció que demana la informació necessària per a eliminar un pirata
     * @return opc: nom del pirata
     */
    public String saveInfoToDeletePirate(){
        Scanner opc = new Scanner(System.in);

        System.out.print("\nEntra el nom del pirata a eliminar: ");

        return opc.nextLine();
    }

    /**
     * Missatge que confirma que un pirata s'ha eliminat de forma correcta
     */
    public void messagePirataDeleted(){
        System.out.println("\nEl pirata s'ha eliminat correctament de la tripulació. F.");
    }

    /**
     * Funció que demana el nom del pirata a buscar i retorna dit nom
     * @return opc: nom del pirata a buscar
     */
    public String pirateToSearch(){
        Scanner opc = new Scanner(System.in);

        System.out.print("\nEntra el nom del pirata a consultar: ");

        return opc.nextLine();
    }

    /**
     * Procediment que mostra tots els pirates juntament amb la seva informació
     * @param data: Tots els pirates amb la seva informació
     */
    public void showPirate(String[] data) {
        String[] parts = data[1].split(",");

        System.out.println("\nNom: " + data[0]);
        System.out.println("Edat: " + parts[0]);
        System.out.println("Rol: " + parts[1]);
    }

    /**
     * Procediment que mostra un valor
     * @param output: valor a mostrar
     */
    public void mostraValor(String output){
        System.out.println(output);
        System.out.println(" ");
    }

    /**
     * Procediment que mostra tots els valors dels tresors que es troben entre dos rangs
     * @param llista: llista de tots els tresors
     */
    public void mostraValorRang(ArrayList<String> llista){
        System.out.println("\nS'han trobat " + llista.size() + " tresors en aquest rang:\n");
        for (int i = 0; i < llista.size(); i++) {
            System.out.println(llista.get(i));
        }
        System.out.println(" ");
    }

    /**
     * Mostra per consola tots els recorreguts
     * @param text: tots els recorreguts a mostrar
     */
    public void mostraRecorreguts(ArrayList<String> text){
        System.out.println(" ");
        for (int i = 0; i < text.size(); i++) {
            System.out.println("\t" + text.get(i));
        }
        System.out.println(" ");
    }

    /**
     * Mostra per pantalla si un tresor s'ha afegit i/o eliminat de forma correcta
     * @param i: determina si estem afegint o eliminant un tresor
     */
    public void mostraSuccess(int i){
        System.out.println(" ");
        if(i == 1){
            System.out.println("El tresor s'ha afegit correctament al botí.");
        }else{
            System.out.println("El tresor s'ha eliminat correctament del botí.");
        }
        System.out.println(" ");
    }

    /**
     * Funció que mostra el menú dels arbres recursius i gestiona la opció escollida per l'usuari
     * @return option: opció vàlida seleccionada per l'usuari
     */
    public String menuRTree(){
        Scanner opc = new Scanner(System.in);
        String option;

        System.out.println("\tA. Afegir tresor\n" +
                "\tB. Eliminar tresor\n" +
                "\tC. Visualitzar\n" +
                "\tD. Cerca per àrea\n" +
                "\tE. Cerca per proximitat\n\n" +
                "\tF. Tornar enrere\n");

        System.out.print("Quina funcionalitat vols executar? ");
        option = opc.nextLine();

        while(!option.equals("A") & !option.equals("B") & !option.equals("C") & !option.equals("D") & !option.equals("E") & !option.equals("F")){
            System.out.println("\nERROR! Opció incorrecta\n");
            System.out.print("Quina funcionalitat vols executar? ");
            option = opc.nextLine();
        }
        return option;
    }

    /**
     * Crida les funcions per a poder mostrar de forma gràfica els arbres recursius
     * @param punts: Punts a mostrar
     * @param rectangles: Rectangles on es troben els punts
     */
    public void createRTreePanel(ArrayList<Point> punts, ArrayList<Rectangle> rectangles){
        RView rView = new RView(punts, rectangles);
        rView.show();
    }

    /**
     * Demana les coordenades del primer punt del rectangle
     * @return option: coordenades seleccionades
     */
    public String demanaPunt1() {
        Scanner opc = new Scanner(System.in);
        String option;
        System.out.print("Entra del primer punt del rectangle (X,Y): ");
        option = opc.nextLine();
        return option;
    }

    /**
     * Demana les coordenades del segon punt del rectangle
     * @return option: coordenades seleccionades
     */
    public String demanaPunt2() {
        Scanner opc = new Scanner(System.in);
        String option;
        System.out.print("Entra del segon punt del rectangle (X,Y): ");
        option = opc.nextLine();
        return option;
    }

    /**
     * Mostra els tresors més propers a un punt
     * @param punts: Punts a mostrar
     */
    public void mostraLlistaPuntsProximitat(Point[] punts) {
        System.out.println("\nEls " + punts.length + " tresors més propers a aquest punt són:");

        System.out.println("");
        for (int i = 0; i < punts.length; i++) {
            String name = punts[i].getName();
            String x = String.valueOf(punts[i].getX());
            String y = String.valueOf(punts[i].getY());

            System.out.println("\t" + name + " (" + x + ", " + y +  ")");
        }
        System.out.println("");
    }

    /**
     * Mostra els punts continguts dins una àrea seleccionada
     * @param punts: Punts a mostrar
     */
    public void mostraLlistaPuntsArea(ArrayList<Point> punts) {
        if (punts.size() == 0) {
            System.out.println("\nNo s'han trobat tresors en aquesta àrea.\n");
        } else {
            System.out.println("\nS'han trobat " + punts.size() + " tresors en aquesta àrea:");

            System.out.println("");
            for (int i = 0; i < punts.size(); i++) {
                String name = punts.get(i).getName();
                String x = String.valueOf(punts.get(i).getX());
                String y = String.valueOf(punts.get(i).getY());

                System.out.println("\t" + name + " (" + x + ", " + y +  ")");
            }
            System.out.println("");
        }
    }

    /**
     * Demana la informació del tresor a eliminar
     * @return nom: nom del tresor a eliminar
     */
    public String demanaEliminarArbreR() {
        Scanner opc = new Scanner(System.in);
        String nom;

        System.out.print("\nEntra el nom del tresor a eliminar: ");
        nom = opc.nextLine();

        return nom;
    }

    /**
     * Demana la informació necessària per a inserir un nou tresor
     * @return text: tota la informació sobre el tresor
     */
    public String[] demanaInsercioArbreR() {
        Scanner opc = new Scanner(System.in);
        String[] text = new String[3];

        System.out.print("\nEntra el nom del tresor a afegir: ");
        text[0] = opc.nextLine();

        System.out.print("Entra la coordenada X de la posició del tresor a afegir: ");
        text[1] = opc.nextLine();

        System.out.print("Entra la coordenada Y de la posició del tresor a afegir: ");
        text[2] = opc.nextLine();

        return text;
    }

    /**
     * Notifica que el tresor s'ha afegit correctament
     */
    public void insercioCorrecta() {
        System.out.println("\nEl tresor s'ha afegit correctament a la coberta.\n");
    }

    /**
     * Notifica que s'està generant la representació gràfica dels tresor
     */
    public void representantCobertura() {
        System.out.println("\nGenerant la representació de la coberta...\n");
    }

    /**
     * Mostra si un tresor s'ha eliminat correctament o no existeix
     * @param correcta: determina si s'ha pogut eliminar o no existia
     */
    public void missatgeEliminar(boolean correcta) {
        if (correcta) {
            System.out.println("\nEl tresor s'ha eliminat correctament de la coberta.\n");
        } else {
            System.out.println("\nNo existeix el tresor en la coberta.\n");
        }

    }

    /**
     * Demana el número de tresors a trobar i a quines coordenades buscar-los per proximitat
     * @return text: opcions seleccionades per l'usuari
     */
    public String[] demanaPuntProximitat() {
        Scanner opc = new Scanner(System.in);
        String[] text = new String[2];

        System.out.print("\nEntra el nombre de tresors a trobar: ");
        text[0] = opc.nextLine();

        System.out.print("Entra el punt a on cercar (X,Y): ");
        text[1] = opc.nextLine();

        return text;
    }

    /**
     * Mètode que imprimeix per pantalla un arbre binari
     * @param text: text de l'arbre binari
     */
    public void imprimirArbreBinari(ArrayList<String> text) {
        for (int i = 0; i < text.size(); i++) {
            System.out.println(text.get(i));
        }
        System.out.println("");
    }

    /**
     * Mètode que executa un missatge d'error quan no es troba.
     */
    public void messagePirataError() {
        System.out.println("\nNo s'ha trobat això!");
    }

    public void showGraphics(int[] values){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setSize(1500,1000);
        frame.setLocationRelativeTo(null);

        JPanel data = new JPanel();
        data.setLayout(new BorderLayout());
        data.setPreferredSize(new Dimension(1500,30));
        data.setMaximumSize(new Dimension(1500,30));

        JLabel label = new JLabel("Edat");
        data.add(label);

        GraphicsPanel graphs = new GraphicsPanel(values);

        frame.add(data, BorderLayout.NORTH);
        frame.add(graphs, BorderLayout.CENTER);
    }
}