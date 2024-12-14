package fr.univlille.algoknn;

import fr.univlille.model.DonneeBrut;
import fr.univlille.model.Iris;
import fr.univlille.model.TypeIris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TestDistanceEuclidienneNormalisee {

    private DistanceEuclidienneNormalisee distanceEuclidienneNormalisee;
    private Iris iris1;
    private Iris iris2;
    private Collection<String> caracs;

    @BeforeEach
    void setUp() {
        distanceEuclidienneNormalisee = new DistanceEuclidienneNormalisee();

        // Initialisation des objets Iris avec des valeurs spécifiques
        iris1 = new Iris(1.4, 0.2, 5.1, 3.5, TypeIris.SETOSA);
        iris2 = new Iris(4.7, 1.4, 6.3, 3.3, TypeIris.VERSICOLOR);
        MethodeKnn.setDatas(Arrays.asList(new DonneeBrut[]{iris1, iris2}));
        caracs = new ArrayList<>(Arrays.asList("sepalLength","sepalWidth"));
    }

    @Test
    void test_Distance_with_Different_Values() {
        // Calcul attendu basé sur les valeurs de iris1 et iris2 pour certaines caractéristiques.
        // Normalisation et calcul de la distance euclidienne.

        double result = distanceEuclidienneNormalisee.distance(iris1, iris2, caracs);
        // Vérifiez que la distance est positive
        assertTrue(result >= 0);
    }

    @Test
    void test_Distance_with_Same_Values() {
        // Test pour vérifier que la distance est zéro pour des caractéristiques identiques
        iris1 = new Iris(5.0, 1.5, 4.5, 2.5);
        iris2 = new Iris(5.0, 1.5, 4.5, 2.5);
        MethodeKnn.setDatas(Arrays.asList(new DonneeBrut[]{iris1, iris2}));

        double result = distanceEuclidienneNormalisee.distance(iris1, iris2, caracs);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void test_Distance_with_Boundary_Values() {
        // Test avec des valeurs limites pour voir si la méthode gère correctement les cas extrêmes
        iris1 = new Iris(0.0, 0.0, 0.0, 0.0);
        iris2 = new Iris(10.0, 10.0, 10.0, 10.0);
        MethodeKnn.setDatas(Arrays.asList(new DonneeBrut[]{iris1, iris2}));

        double result = distanceEuclidienneNormalisee.distance(iris1, iris2, caracs);
        // Vérifiez que le résultat est supérieur à zéro et conforme
        assertTrue(result >= 0);
    }

    //il n'y a pas de tests pour la fonctions normaliser car elle ne fait qu'un appelle de la fonction normalisé_0_1 
    //cette fonction est dejà testé dans TestClassiflex il n'est donc pas utile de la tester a nouveau.
}
