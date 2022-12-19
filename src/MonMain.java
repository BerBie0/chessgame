import Model.Model;
import Controller.Controller;
import View.View;

public class MonMain {

    public static void main(String[] args) {
        Model model = new Model();
        Controller c = new Controller(model);
        View view = new View(model, c);
        //https://perso.telecom-paristech.fr/hudry/coursJava/interSwing/boutons5.html
        //TODO controleur modele + vue ?

        model.addObserver(view);
    }
}
