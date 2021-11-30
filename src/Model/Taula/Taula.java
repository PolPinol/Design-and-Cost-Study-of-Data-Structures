package Model.Taula;

import java.util.ArrayList;

/**
 * Classe que implementa les taules i les seves respectives funcions
 */
public class Taula {
    private static final int VALOR_MAX_ASCII = 'z';

    private String[][] hashMap;
    private int R;
    private final int totalPirates;
    private int[] edats;

    public Taula(int maxElements, ArrayList<String> text){
        this.totalPirates = maxElements;
        this.hashMap = new String[VALOR_MAX_ASCII * this.totalPirates][2];
        this.R = VALOR_MAX_ASCII * this.totalPirates;
        createHashMap(text);
        this.edats = returnEdats();
    }

    public int[] getEdats() {
        return edats;
    }

    /**
     * Retorna les edats de tots els pirates
     * @return edats: array amb totes les edats
     */
    public int[] returnEdats(){
        int[] edats = new int[101];

        for (int i = 0; i < R; i++) {
            if(hashMap[i][0] != null) {
                String[] parts = hashMap[i][1].split(",");
                int age = Integer.parseInt(parts[0]);
                edats[age]++;
            }
        }

        return edats;
    }

    public String[] getPirateByPosition(int position){
        return hashMap[position];
    }

    /**
     * Borra un pirata de la taula
     * @param id: identificador del pirata a borrar
     * @return delete: Indica si s'ha eliminat el pirata correctament
     */
    public boolean deletePirate(String id){
        String[][] aux = new String[VALOR_MAX_ASCII * this.totalPirates][2];
        int j = 0;
        boolean delete = false;

        for (int i = 0; i < hashMap.length; i++) {
            if(hashMap[i][0] != null && !hashMap[i][0].equals(id)){
                aux[j] = hashMap[i];
                j++;
            }
            if(hashMap[i][0] != null && hashMap[i][0].equals(id)) {
                delete = true;
            }
        }

        hashMap = aux;

        return delete;
    }

    /**
     * Ajusta el hashmap
     * @param id: identificador a ajustar
     */
    public void ajustarMap(int id){
        String[][] aux = new String[id + 1][2];

        for (int i = 0; i < R; i++) {
            if(hashMap[i][0] != null){
                aux[i] = hashMap[i];
            }
        }
        hashMap = aux;
    }

    public void linearProbing(int i, int id, String name, String data){
        if(hashMap[id][0] == null){
            hashMap[id][0] = name;
            hashMap[id][1] = data;
        } else {
            i++;
            id = (id + (i*i)) % R;

            linearProbing(i, id, name, data);
        }
    }

    /**
     * Afegeix un pirata
     * @param name: nom del pirata a afegir
     * @param data: informació sobre el pirata
     */
    public void addPirate(String name, String data){
        int id = 0;

        for (int i = 0; i < name.length(); i++) {
            if(name.charAt(i) != ' '){
                id = id + name.codePointAt(i);
            }
        }

        int i = 0;

        if(id > R){
            ajustarMap(id);
            hashMap[id][0] = name;
            hashMap[id][1] = data;
            R = id;
        } else {
            linearProbing(i, id, name, data);
        }
    }

    /**
     * Crea un hashmap
     * @param text: informació sobre el hashmap a crear
     */
    public void createHashMap(ArrayList<String> text){
        int numPirates = Integer.parseInt(text.get(0));

        for (int i = 1; i <= numPirates; i++) {
            String[] parts = text.get(i).split(",");
            addPirate(parts[0], parts[1] + "," + parts[2]);
        }
    }
}
