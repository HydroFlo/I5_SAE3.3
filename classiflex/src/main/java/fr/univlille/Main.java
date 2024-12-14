package fr.univlille;

import fr.univlille.model.Classiflex;
import fr.univlille.view.ScatterView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"data"+System.getProperty("file.separator")+"iris.csv";
        Classiflex model = new Classiflex();
        //model.setJeuDeDonnee(path);
        ScatterView sv = new ScatterView(model);
        //ScatterView sv2 = new ScatterView(model);
        model.attach(sv);
        //model.attach(sv2);
        System.out.println(path);
        //Classiflex cf = new Classiflex(path);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
