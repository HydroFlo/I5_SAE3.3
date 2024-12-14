package fr.univlille.algoknn;


import fr.univlille.model.Iris;
import fr.univlille.model.TypeIris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TestDistanceManhattan {

    private DistanceManhattan distanceManhattan;
    private Iris iris1;
    private Iris iris2;
    private Collection<String> caracs;

    @BeforeEach
    void setUp() {
        distanceManhattan = new DistanceManhattan();

        // Initialisation des objets Iris avec des valeurs spécifiques
        iris1 = new Iris(1.4, 0.2, 5.1, 3.5, TypeIris.SETOSA);
        iris2 = new Iris(4.7, 1.4, 6.3, 3.3, TypeIris.VERSICOLOR);
        caracs = new ArrayList<>(Arrays.asList("sepalLength","sepalWidth"));
    }

    @Test
    void test_Distance_with_Different_Values() {
        // Calcul attendu basé sur la différence des caractéristiques entre iris1 et iris2
        double expectedDistance = Math.abs(iris1.getCaracterisque("sepalLength") - iris2.getCaracterisque("sepalLength"))
                + Math.abs(iris1.getCaracterisque("sepalWidth") - iris2.getCaracterisque("sepalWidth"));

        double result = distanceManhattan.distance(iris1, iris2, caracs);
        assertEquals(expectedDistance, result, 0.0001);
    }

    @Test
    void test_Distance_with_Same_Values() {
        // Test pour vérifier que la distance est zéro pour des caractéristiques identiques
        iris1 = new Iris(5.0, 1.5, 4.5, 2.5);
        iris2 = new Iris(5.0, 1.5, 4.5, 2.5);

        double result = distanceManhattan.distance(iris1, iris2, caracs);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void test_Distance_with_Boundary_Values() {
        // Test avec des valeurs limites pour voir si la méthode gère correctement les cas extrêmes
        iris1 = new Iris(0.0, 0.0, 0.0, 0.0);
        iris2 = new Iris(10.0, 10.0, 10.0, 10.0);

        double expectedDistance = Math.abs(iris1.getCaracterisque("sepalLength") - iris2.getCaracterisque("sepalLength"))
                + Math.abs(iris1.getCaracterisque("sepalWidth") - iris2.getCaracterisque("sepalWidth"));

        double result = distanceManhattan.distance(iris1, iris2, caracs);
        assertEquals(expectedDistance, result, 0.0001);
    }
}
