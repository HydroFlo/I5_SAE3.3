package fr.univlille.view;

import fr.univlille.algoknn.*;
import fr.univlille.utils.Observable;
import fr.univlille.utils.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import fr.univlille.model.*;
import javafx.util.Callback;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScatterView extends Stage implements Observer {
    private Classiflex model;
    ArrayList<XYChart.Series> possibleType;
    MethodeKnn knn;
    ArrayList<String> nomsTypes;
    File selectedFile;
    String path;
    boolean tableau=false;

    ScatterChart<Number, Number> chart;
    String car1 = "";
    String car2 = "";
    Class<? extends DonneeBrut> classe;

    public ScatterView(Classiflex model) {
        this.model=model;

        nomsTypes = new ArrayList<String>();
        possibleType = new ArrayList<XYChart.Series>();

        Label bvn = new Label("Bienvenue sur Classiflex!");
        bvn.setStyle("-fx-font: 24 arial;");
        Label desc = new Label("Commencez par choisir un fichier contenant les données à utiliser!");
        Button fileChooser = new Button("Choisir un fichier");
        fileChooser.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: black;");
        fileChooser.setOnMousePressed(e -> fileChooser.setStyle("-fx-background-color: dodgerblue; -fx-text-fill: white;"));
        fileChooser.setOnMouseReleased(e -> fileChooser.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: black;"));
        fileChooser.setOnMouseEntered(e -> fileChooser.setStyle("-fx-background-color: dodgerblue; -fx-text-fill: black;"));
        fileChooser.setOnMouseExited(e -> fileChooser.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: black;"));
        fileChooser.setOnAction(e -> {
            if (this.formulaireChoixFichier()) {
                this.close();
                try {
                    this.menuPrincipal();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        VBox root = new VBox(bvn,desc,fileChooser);
        root.setSpacing(10);
        root.setPadding(new Insets(5,5,5,5));
        Scene sc = new Scene(root);
        this.setTitle("Classiflex");
        this.setScene(sc);
        this.setResizable(false);
        this.show();
    }

    public void menuPrincipal() throws ClassNotFoundException {
        Stage stage = new Stage();
        Button ajout = new Button("Ajouter un point");
        ajout.setOnAction(e -> {
            try {
                this.formulaireAjout(Classiflex.getValuesNames(classe.getName()));
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        Button classifier=new Button("Classifier");
        classifier.setOnAction(e -> this.formulaireClassifier());
        Button nouvelleVue = new Button("Nouvelle Vue");
        nouvelleVue.setDisable(true); //Rend le boutton de nouvelle vue non cliquable
        nouvelleVue.setOnAction(e -> {
            try {
                this.menuPrincipal();
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        ajout.setMinWidth(130);
        classifier.setMinWidth(80);
        nouvelleVue.setMinWidth(110);

        NumberAxis ordonnee =
                new NumberAxis(" ",0,10,0.25);
        NumberAxis abscisse =
                new NumberAxis(" ",0,10,0.25);
        chart = new ScatterChart<>(abscisse, ordonnee);


        for (int i=0;i<possibleType.size();i++){
            chart.getData().add(possibleType.get(i));
            chart.getData().get(i).setName(nomsTypes.get(i));
        }



        ChoiceBox<String> caracteristique1=new ChoiceBox<String>();
        caracteristique1.getItems().addAll(Classiflex.getValuesNames(classe.getName()));
        caracteristique1.setOnAction(e -> {
            ordonnee.setLabel(caracteristique1.getValue());
            car2 = ordonnee.getLabel();
            this.creerToutPoint(this.model.getJeuDeDonnee());
        });

        ChoiceBox<String> caracteristique2=new ChoiceBox<String>();
        caracteristique2.getItems().addAll(Classiflex.getValuesNames(classe.getName()));
        caracteristique2.setOnAction(e -> {
            abscisse.setLabel(caracteristique2.getValue());
            car1 = abscisse.getLabel();
            this.creerToutPoint(this.model.getJeuDeDonnee());
        });


        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        Region r1 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);
        Region r4 = new Region();
        HBox.setHgrow(r4, Priority.ALWAYS);

        Label tAxes = new Label(" Axes:");
        tAxes.setMinSize(50,25);

        Label tY = new Label(" Y: ");
        tY.setMinSize(30,25);

        Label tX = new Label(" X: ");
        tX.setMinSize(30,25);


        HBox hbox = new HBox(tAxes, r3, tX, caracteristique2, r4, tY, caracteristique1);
        hbox.setAlignment(Pos.CENTER);

        Button robustesse=new Button("Robustesse");

        HBox hboxBottom = new HBox(ajout, classifier, robustesse,r, nouvelleVue);

        ChoiceBox<String> currentEnum=new ChoiceBox<String>();
        currentEnum.getItems().addAll(model.getDonee().getEnumClasses());
        currentEnum.setOnAction(e -> {

//            this.resetChart();

            model.setCurrentEnum(currentEnum.getValue());

        });
        VBox all = new VBox(hbox,new Label("Classification sur"),currentEnum,chart, r1, hboxBottom);
        all.setSpacing(5);
        HBox root=new HBox(all);

        Scene sc = new Scene(root);
        stage.setTitle("Classification de Données Brut");
        stage.setScene(sc);
        robustesse.setOnAction(event -> {
            // Ajouter la table à la HBox lorsqu'on clique sur le bouton
            if(!tableau){
                root.getChildren().add(createTable(model.getJeuDeDonnee().size()));
                stage.setWidth(stage.getWidth()+450);
                tableau=!tableau;
            }else{
                root.getChildren().remove(root.getChildren().size()-1);
                stage.setWidth(stage.getWidth()-450);
                tableau=!tableau;
            }

        });
        stage.setMinWidth(330);
        stage.setMinHeight(350);
        stage.show();
    }

    public void formulaireAjout(Collection<String> valuesName){
        switch (selectedFile.getName()) {
            case "iris.csv" -> {
                ajoutIris();
            }

            case "pokemon_train.csv" -> {
                ajoutPokemon();
            }

            default -> {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur : Ce jeu de données n'est pas pris en charge");
                alert.setContentText("Aucun formulaire d'ajout n'existe pour ce jeu de donnée !");
                alert.show();
            }
        }

    }

    public void ajoutIris(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        HBox h1 = caseFormAjout("Longueur des sépales: ");
        HBox h2 = caseFormAjout("Largeur des sépales: ");
        HBox h3 = caseFormAjout("Longueur des pétales: ");
        HBox h4 = caseFormAjout("Largeur des pétale");

        Button valider = new Button("Creer le point");
        HBox hfinal = new HBox(new Label("  "),valider);
        VBox vbox = new VBox(h1,h2,h3,h4,hfinal);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        Scene scene = new Scene(vbox, 310, 160);
        stage.setScene(scene);
        stage.setTitle("Ajouter un point");
        stage.setResizable(false);
        valider.setOnAction(e -> {
            try {
                this.model.addIris(Double.parseDouble(getTextFromHbox(h3)),
                        Double.parseDouble(getTextFromHbox(h4)),
                        Double.parseDouble(getTextFromHbox(h1)),
                        Double.parseDouble(getTextFromHbox(h2)));
            } catch (NumberFormatException error) {
                /*System.out.println(getTextFromHbox(h3));
                System.out.println(getTextFromHbox(h4));
                System.out.println(getTextFromHbox(h1));
                System.out.println(getTextFromHbox(h2));*/
                System.err.println("Donnée invalide pour ce type");
            }
            stage.close();
        });
        stage.show();
    }

    public void ajoutPokemon(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        HBox h1 = caseFormAjout("Défense spéciale");
        HBox h2 = caseFormAjout("Taux de capture");
        HBox h3 = caseFormAjout("Attaque spéciale");
        HBox h4 = caseFormAjout("Défense");
        HBox h5 = caseFormAjout("experienceGrowth");
        HBox h6 = caseFormAjout("NB pas pour éclosion");
        HBox h7 = caseFormAjout("Points de vie");
        HBox h8 = caseFormAjout("Attaque");
        HBox h9 = caseFormAjout("Vitesse");

        Button valider=new Button("Creer le point");
        HBox hfinal=new HBox(new Label("  "),valider);
        VBox vbox = new VBox(h1,h2,h3,h4,h5,h6,h7,h8,h9,hfinal);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));
        Scene scene = new Scene(vbox, 310, 310);
        stage.setScene(scene);
        stage.setTitle("Ajouter un point");
        stage.setResizable(false);
        valider.setOnAction(e -> {
            try {
                this.model.addPokemon(Integer.parseInt(getTextFromHbox(h1)),
                        Double.parseDouble(getTextFromHbox(h2)),
                        Integer.parseInt(getTextFromHbox(h3)),
                        Integer.parseInt(getTextFromHbox(h4)),
                        Integer.parseInt(getTextFromHbox(h5)),
                        Integer.parseInt(getTextFromHbox(h6)),
                        Integer.parseInt(getTextFromHbox(h7)),
                        Integer.parseInt(getTextFromHbox(h8)),
                        Double.parseDouble(getTextFromHbox(h9)));
            } catch (NumberFormatException error) {
                /*System.out.println(getTextFromHbox(h1));
                System.out.println(getTextFromHbox(h2));
                System.out.println(getTextFromHbox(h3));
                System.out.println(getTextFromHbox(h4));
                System.out.println(getTextFromHbox(h5));
                System.out.println(getTextFromHbox(h6));
                System.out.println(getTextFromHbox(h7));
                System.out.println(getTextFromHbox(h8));
                System.out.println(getTextFromHbox(h9));*/
                System.err.println("Donnée invalide pour ce type");
            }
            stage.close();
        });
        stage.show();
    }

    public String getTextFromHbox(HBox hb){
        String txt = "";
        Node lastChild = hb.getChildren().get(hb.getChildren().size() - 1);
        if(lastChild instanceof TextArea t){
            txt = t.getText();
        }
        return txt;
    }

    public HBox caseFormAjout(String label){
        Label l=new Label(label);
        TextArea t=new TextArea();
        t.setMaxSize(150, 25);
        t.setMinSize(50,25);
        l.setMinSize(150,25);
        l.setMaxSize(150,25);
        return new HBox(new Label("  "),l,t);
    }
  
    public void formulaireClassifier(){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label l1=new Label("Classifier en fonction de combien de voisins?");
        TextArea t1=new TextArea();
        t1.setMaxSize(100, 25);
        t1.setMinSize(50,25);
        l1.setMinSize(350,25);
        l1.setMaxSize(350,25);

        ToggleGroup groupe=new ToggleGroup();
        RadioButton rb1=new RadioButton("Distance Euclidienne");
        rb1.setToggleGroup(groupe);
        RadioButton rb2=new RadioButton("Distance de Manhatan");
        rb2.setToggleGroup(groupe);
        VBox listeChoix=new VBox(rb1,rb2);
        listeChoix.setSpacing(3);

        Button classer=new Button("Classifier");

        Region r1 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);

        VBox vbox=new VBox(l1,t1,new Label("  "), new Label("Quel type de distance ?"),listeChoix,r1, classer);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5,5,5,5));

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Classifier");
        stage.setResizable(false);

        classer.setOnAction(e -> {
            try {
                RadioButton selectedRadioButton = (RadioButton) groupe.getSelectedToggle();
                String dist = "";
                int k = Integer.parseInt(t1.getText());
                if(selectedRadioButton != null) dist = selectedRadioButton.getText();
                if(k < 1) throw new NumberFormatException();
                System.out.println(dist +" k= "+k);
                this.model.classifier(k, getDist(dist), Classiflex.getValuesNames(classe.getName()));
            } catch (NumberFormatException nfe){
                System.err.println("Il faut rentrez un k entier");
            } catch(ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            stage.close();
        });
        stage.show();
    }

    public Distance<DonneeBrut> getDist(String distName){
        if(distName.equals("Distance de Manhatan"))  return new DistanceManhattanNormalisee();
        return new DistanceEuclidienneNormalisee();

    }

    public boolean formulaireChoixFichier(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"data"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fileChooser.showOpenDialog(this);
        if (selectedFile != null && selectedFile.getName().endsWith(".csv")) {
            path = System.getProperty("user.dir")+System.getProperty("file.separator")+"data"+System.getProperty("file.separator")+selectedFile.getName();
            try{
                switch (selectedFile.getName()){
                    case "iris.csv" -> {
                        classe = Iris.class;
                        this.model.setJeuDeDonnee(path, classe);

                        DonneeBrut.setCurrentEnum(TypeIris.class.getName());
                    }
                        
                    case "pokemon_train.csv" -> {
                        classe = Pokemon.class;
                        this.model.setJeuDeDonnee(path, classe);
                        DonneeBrut.setCurrentEnum(TypePoke.class.getName());
                    }

                    default -> {
                        var alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur : Ce jeu de données n'est pas pris en charge");
                        alert.setContentText("Votre fichier n'a pas été chargé !");
                        alert.show();
                        return false;
                    }
                }
                knn=new MethodeKnn();
                MethodeKnn.setDatas((List<DonneeBrut>) this.model.getJeuDeDonnee());
                for(int i = 0; i < DonneeBrut.getValuesFromEnum(DonneeBrut.getCurrentEnum()).size(); i ++){
                        nomsTypes.add(DonneeBrut.getValuesFromEnum(DonneeBrut.getCurrentEnum()).get(i));
                        possibleType.add(new XYChart.Series());
                }

                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fichier chargé");
                alert.setHeaderText("Validation");
                alert.setContentText("Votre fichier a bien été chargé !");
                alert.initOwner(this);
                this.creerToutPoint(this.model.getJeuDeDonnee());
                alert.showAndWait();
                return true;
            }catch(IOException | NullPointerException e){
                System.out.println(e.getMessage());
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur : Fichier Introuvable ou Path invalide");
                alert.setContentText("Votre fichier n'a pas été chargé !");
                alert.show();
                return false;
            } catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur : Type de Fichier invalide");
            alert.setContentText("Le fichier choisi n'est pas au format CSV.");
            alert.show();
            return false;
        }

    }

    public TableView<RowData> createTable(int maxK) {
        TableView<RowData> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Colonne "k"
        TableColumn<RowData, Integer> columnK = new TableColumn<>("k");
        columnK.setCellValueFactory(new PropertyValueFactory<>("k"));
        columnK.setPrefWidth(50);

        // Colonne "Distance Euclidienne"
        TableColumn<RowData, Double> columnEuclidean = new TableColumn<>("Distance Euclidienne");
        columnEuclidean.setCellValueFactory(new PropertyValueFactory<>("euclideanDistance"));
        columnEuclidean.setPrefWidth(200);

        // Colonne "Distance de Manhattan"
        TableColumn<RowData, Double> columnManhattan = new TableColumn<>("Distance de Manhattan");
        columnManhattan.setCellValueFactory(new PropertyValueFactory<>("manhattanDistance"));
        columnManhattan.setPrefWidth(200);

        // Ajouter des données au tableau
        double maxEuclidean = Double.MIN_VALUE;
        int maxEuclideanK = -1;

        double maxManhattan = Double.MIN_VALUE;
        int maxManhattanK = -1;


        for (int k = 1; k <= maxK; k++) {
            double[] percentages = new double[0]; // Appel de la fonction robustesse
            try {
                percentages = model.robustesse(k,Classiflex.getValuesNames(classe.getName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            RowData row = new RowData(k, percentages[0], percentages[1]);
            tableView.getItems().add(row);


        }

        // Appliquer un style aux cellules
        columnEuclidean.setCellFactory(new Callback<>() {
            @Override
            public TableCell<RowData, Double> call(TableColumn<RowData, Double> column) {
                return createStyledCellFactory(maxEuclidean);
            }
        });

        columnManhattan.setCellFactory(new Callback<>() {
            @Override
            public TableCell<RowData, Double> call(TableColumn<RowData, Double> column) {
                return createStyledCellFactory(maxManhattan);
            }
        });

        tableView.getColumns().addAll(columnK, columnEuclidean, columnManhattan);
        return tableView;
    }

    private TableCell<RowData, Double> createStyledCellFactory(double maxPercentage) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("%.2f%%", value));

                    double percentage = value / 100.0;
                    Color color = Color.color(1 - percentage, percentage, 0);

                    setStyle("-fx-background-color: rgb(" +
                            (int) (color.getRed() * 255) + "," +
                            (int) (color.getGreen() * 255) + "," +
                            (int) (color.getBlue() * 255) + "); -fx-text-fill: black;");
                }
            }
        };
    }



    // Classe interne représentant une ligne du tableau
    public static class RowData {
        private final int k;
        private final double euclideanDistance;
        private final double manhattanDistance;

        public RowData(int k, double euclideanDistance, double manhattanDistance) {
            this.k = k;
            this.euclideanDistance = euclideanDistance;
            this.manhattanDistance = manhattanDistance;
        }

        public int getK() {
            return k;
        }

        public double getEuclideanDistance() {
            return euclideanDistance;
        }

        public double getManhattanDistance() {
            return manhattanDistance;
        }
    }



    public void creerPoint(DonneeBrut item){
        double x = model.getNormaliseCaracteristique(item, car1)*10;
        double y = model.getNormaliseCaracteristique(item, car2)*10;
        XYChart.Data<Double,Double> point=new XYChart.Data<Double,Double>(x,y);
        boolean known =false;
        for (int i = 0; i < nomsTypes.size(); i++) {
            if(item.getType().toString().equals(nomsTypes.get(i))){
                possibleType.get(i).getData().add(point);
                known=true;
            }
        }
        if(!known){
            possibleType.get(possibleType.size()-1).getData().add(point);
        }
    }

    public void creerToutPoint(Collection<DonneeBrut> data){
        this.reset();
        for (DonneeBrut i : data) {
            this.creerPoint(i);
        }
    }

    public void reset(){
        for (int i = 0; i < possibleType.size(); i++) {
            possibleType.get(i).getData().clear();
        }
        possibleType.get(possibleType.size()-1).getData().clear();

    }

    public void resetChart() {
        chart.getData().clear();
    }

    public void recreateChart() throws ClassNotFoundException {
        NumberAxis ordonnee =
                new NumberAxis(" ",0,10,0.25);
        NumberAxis abscisse =
                new NumberAxis(" ",0,10,0.25);
        chart = new ScatterChart<>(abscisse, ordonnee);
        for(int i = 0; i < DonneeBrut.getValuesFromEnum(DonneeBrut.getCurrentEnum()).size(); i ++){
            nomsTypes.add(DonneeBrut.getValuesFromEnum(DonneeBrut.getCurrentEnum()).get(i));
            possibleType.add(new XYChart.Series());
        }
        for (int i=0;i<possibleType.size();i++){
            chart.getData().add(possibleType.get(i));
            chart.getData().get(i).setName(nomsTypes.get(i));
        }
    }


    @Override
    public void update(Observable observable) {
//        try {
//            this.recreateChart();
            this.creerToutPoint(this.model.getJeuDeDonnee());
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
