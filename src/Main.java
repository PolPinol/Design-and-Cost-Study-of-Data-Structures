import Controller.Controller;

/**
 * Classe principal on inicialitzem el programa
 */
public class Main {

    /**
     * Procediment el qual inicialitza el nostre programa
     * @param args: Possibles arguments per a passar-li
     * @throws CloneNotSupportedException Excepció per a controlar possibles errors
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Controller controller = new Controller();

        controller.run();
    }
}