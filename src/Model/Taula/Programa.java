package Model.Taula;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que gestiona totes les crides a procediments i funcions de les taules
 */
public class Programa {
    private static final String FILE_PATH = "Tables/tablesM.paed";

    private ArrayList<String> noms;
    private int numNodes;
    private Taula taula;

    public Programa(){
        ArrayList<String> text = llegirFitxer(FILE_PATH);
        this.noms = new ArrayList();
        this.numNodes = 0;
        saveNames(text);
        this.taula = new Taula(numNodes, text);
    }

    /**
     * Genera i mostra el gràfic de les taules
     */
    public int[] getInformationForGraphic(){
        return taula.getEdats();
    }

    /**
     * Afegeix un nou pirata
     * @param data: informació del pirata
     */
    public void addNewPirate(ArrayList<String> data){
        taula.addPirate(data.get(0), data.get(1));
    }

    /**
     * Elimina un pirata
     * @param id: identificador del pirata a eliminar
     * @return boolean: indica si s'ha eliminat correctament
     */
    public boolean deletePirate(String id){
        return taula.deletePirate(id);
    }

    /**
     * Busca un pirata i retorna la seva informació
     * @param id: identificador del pirata a buscar
     * @return data: informació sobre el pirata buscat
     */
    public String[] searchPirate(String id){
        String[] data = new String[0];

        for (int i = 0; i < numNodes; i++) {
            String pirate = taula.getPirateByPosition(i)[0];
            if(pirate != null && pirate.equals(id)){
                data = taula.getPirateByPosition(i);
                break;
            }
        }

        return data;
    }

    /**
     * Llegeix el fitxer que conté la informació per a generar les taules
     * @param filePath: path que condueix al fitxer
     * @return text: informació extreta del fitxer
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

    /**
     * Guarda els noms dels pirates
     * @param text: conté tota la informació dels pirates
     */
    private void saveNames(ArrayList<String> text){
        numNodes = Integer.parseInt(text.get(0));

        for (int i = 1; i <= numNodes; i++) {
            String[] parts = text.get(i).split(",");
            noms.add(parts[0]);
        }
    }

    /**
     * Busca els caràcters màxims
     * @return numChars: número de caràcters
     */
    private int searchMaxChars(){
        int numChars = 0;

        for (int i = 0; i < numNodes; i++) {
            int lenght = noms.get(i).length();
            if(numChars < lenght){
                numChars = lenght;
            }
        }

        return numChars;
    }
}
