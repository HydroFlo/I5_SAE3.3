package fr.univlille.model;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.algoknn.Distance;
import fr.univlille.algoknn.DistanceEuclidienneNormalisee;
import fr.univlille.algoknn.DistanceManhattanNormalisee;
import fr.univlille.algoknn.MethodeKnn;
import fr.univlille.utils.Observable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Classiflex extends Observable {

    protected Collection<DonneeBrut> jeuDeDonnee = null;
    private final Random random = new Random();

    public Classiflex() {}

    /**
     *
     * @param k nombre de voisin à regarder
     * @param distance la distance à utiliser
     * @param caracteristiques liste des caractérisque du la donnée a regardé
     * classifie les donnée grâce à l'algorithme K-NN
     */
    public void classifier(int k,Distance<DonneeBrut> distance, Collection<String> caracteristiques){
        for(DonneeBrut data : this.jeuDeDonnee){
            if(data.isUnknown()){
                String type = MethodeKnn.whatType(data, k, distance, caracteristiques);
                data.setType(type);
            }
        }
        this.notifyObservers();

    }

    public Collection<DonneeBrut> getJeuDeDonnee() {
        return jeuDeDonnee;
    }

    /**
     * Ajoute une donnée au jeu de donnée
     * @param data donnée à ajouter
     */
    public void addData(DonneeBrut data){
        this.jeuDeDonnee.add(data);
        this.notifyObservers();
    }

    /**
     * Ajoute un iris dont on connait les données, mais pas le type
     * @param petalLength taille du pétal de l'iris
     * @param petalWidth largeur du pétal de l'iris
     * @param sepalLength taille du sépal de l'iris
     * @param sepalWidth largeur du sépal de l'iris
     */
    public void addIris(double petalLength, double petalWidth, double sepalLength, double sepalWidth){
        this.addData(new Iris(petalLength, petalWidth, sepalLength, sepalWidth));
        this.notifyObservers();
    }

    /**
     * Ajoute un pokemon dont on connait toutes les valeurs numeriques
     * @param spDef
     * @param captureRate
     * @param spAtk
     * @param def
     * @param experienceGrowth
     * @param baseEggStep
     * @param hp
     * @param atk
     * @param speed
     */
    public void addPokemon(int spDef, double captureRate, int spAtk, int def, int experienceGrowth, int baseEggStep, int hp, int atk, double speed){
        this.addData(new Pokemon(spDef,captureRate,spAtk,def,experienceGrowth,baseEggStep,hp,atk,speed));
        this.notifyObservers();
    }

    /**
     * Normalise la 'valeur' en fonction des 'min' et 'max' indiqué
     * @param valeur valeur à normaliser
     * @param min valeur minimum disponible
     * @param max valeur maximal disponible
     * @return la valeur normalisée
     */
    public static double normaliser_0_1 (double valeur, double min, double max){
        if(max<=min || valeur<min || valeur>max) return -1;
        if(valeur == min) return 0;
        if(valeur == max) return 1;
        return (valeur-min)/(max-min);
    }

    //implementation pour passer la compilation : à changer en appelant classifier

    /**
     *
     * @param k nombre de k a vérifier
     * @param caract liste des caractéristique de la donnée pour la vérif
     * @return
     */
    public double[] robustesse(int k, Collection<String> caract ) {
        int vEucli=0;
        int vManhat=0;
        int tot=0;
        List<DonneeBrut> sortableDatas = new ArrayList<>(jeuDeDonnee);
        System.out.println(sortableDatas);

        /*
        *  Nous obtenons l'erreur 'Exception in thread "JavaFX Application Thread" java.lang.IllegalArgumentException: Comparison method violates its general contract!'
        *  Que nous ne comprenons pas étant donné que le Comparator n'a pas d'erreur à priori (même chat GPT le dit)
        *  Du coup en l'état c'est une génération random
        */
        /*for(DonneeBrut data : sortableDatas){
            String typeEuc = MethodeKnn.whatType(data, k,new DistanceEuclidienneNormalisee(), caract);
            System.out.println(typeEuc+" ---Euc--- "+data.getType().toString());
            if(data.getType().toString().equals(typeEuc)){
                vEucli=vEucli+1;
            }

            String typeMan = MethodeKnn.whatType(data, k,new DistanceManhattanNormalisee(), caract);
            System.out.println(typeMan+" ---Man--- "+data.getType().toString());
            if(data.getType().toString().equals(typeMan)){
                vManhat=vManhat+1;
            }
            tot=tot+1;
        }*/
        return new double[]{(double) Math.round(random.nextDouble(0, 100) * 100) /100, (double) Math.round(random.nextDouble(0, 100) * 100) /100}; // Simule des valeurs aléatoires
    }

    /**
     * Lis un fichier CSV dont le nom est passé en paramètre et renvoie une liste d'DonneeBrut correspondant
     * @param fileName nom du fichier
     * @return liste d'DonneeBrut correspondant au fichier CSV lu
     * @throws IOException renvoie une exception en cas d'erreur de lecture
     */
    public static List<DonneeBrut> readCSV(String fileName, Class<? extends DonneeBrut> classe) throws IOException {
        return new CsvToBeanBuilder<DonneeBrut>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(classe)
                .build().parse();
    }

    /**
     *
     * @param path chemin indiquant la position du csv à lire
     * @param classe la classe correspondant au jeu de donnée à lire
     * @throws IOException en cas d'erreur de lecture
     * lis le csv en paramètre et génère le jeu de donnée avant de l'affecter à l'attribut jeu de donnée
     */
    public void setJeuDeDonnee(String path, Class<? extends DonneeBrut> classe) throws IOException{
        this.jeuDeDonnee = readCSV(path, classe);
    }

    /**
     *
     * @param className nom de la classe dont on veut les attribut
     * @return une collection de String contenant les attribut visible de la classe en paramètre
     * @throws ClassNotFoundException si le nom de classe est incorrecte
     */
    public static Collection<String> getValuesNames(String className) throws ClassNotFoundException {
        Class classe = Class.forName(className);

        Field[] tab = classe.getFields();

        Collection<String> values = new HashSet<>();
        for(int i = 0; i < tab.length; i ++){
            values.add(classe.getFields()[i].getName());
        }
        return values;
    }

    /**
     * Retourn la valeur minimale d'une caractéristique passé en paramètre
     * @param nomCol
     * @return
     */
    public double getMin(String nomCol){
        double min = Double.MAX_VALUE;
        for(DonneeBrut data : jeuDeDonnee){
            if(data.getCaracterisque(nomCol) < min) min = data.getCaracterisque(nomCol);
        }
        return min;
    }


    /**
     * Retourn la valeur maximale d'une caractéristique passé en paramètre
     * @param nomCol
     * @return
     */
    public double getMax(String nomCol){
        double max = Double.MIN_VALUE;
        for(DonneeBrut data : jeuDeDonnee){
            if(data.getCaracterisque(nomCol) > max) max = data.getCaracterisque(nomCol);
        }
        return max;
    }

    /**
     * Appel de la methode setCurrentEnum dans Classiflex pour pouvoir notifier les observateurs à l'appel de la fonction
     * @param currentEnum Nom d'enum a mettre en current enum
     */
    public void setCurrentEnum(String currentEnum) {
        DonneeBrut.setCurrentEnum(currentEnum);
        this.notifyObservers();
    }

    public DonneeBrut getDonee(){
        ArrayList<DonneeBrut> li=new ArrayList<DonneeBrut>(jeuDeDonnee);
        return li.get(1);
    }

    /**
     *
     * @param data donnée à normaliser
     * @param nomCol nom de la collonne du csv à utiliser pour la normalisation
     * @return la valeur normalisée de la donnée data dans la collone nomCol du csv
     */
    public double getNormaliseCaracteristique(DonneeBrut data, String nomCol){
        return Classiflex.normaliser_0_1(data.getCaracterisque(nomCol), this.getMin(nomCol), getMax(nomCol));
    }

}