package fr.univlille.algoknn;

import fr.univlille.model.Iris;
import fr.univlille.model.TypeIris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TestDistanceEuclidienne {

    private DistanceEuclidienne distanceEuclidienne;
    
    private Iris setosa;
    private Iris versicolor;
    private Iris virginica;

    private Iris setosaSimple;
    private Iris versicolorSimple;
    private Iris virginicaSimple;

    private Iris fleurSame1;
    private Iris fleurSame2;
    private Iris fleurSame3;
    private Iris fleurSame4;

    private Iris fleurLimite1;
    private Iris fleurLimite2;
    private Iris fleurLimite3;
    private Iris fleurLimite4;
    private Collection<String> caracs;

    @BeforeEach
    void initialisation() {
        distanceEuclidienne = new DistanceEuclidienne();

        //pour les tests avec des données plus faciles
        setosaSimple = new Iris(1, 2, 4, 3, TypeIris.SETOSA);
        versicolorSimple = new Iris(4, 5, 6, 1, TypeIris.VERSICOLOR);
        virginicaSimple = new Iris(2, 1, 2, 7, TypeIris.VIRGINICA);

        //pour les tests plus précis et réalistes
        setosa = new Iris(1.4, 0.2, 5.1, 3.5, TypeIris.SETOSA);
        versicolor = new Iris(4.7, 1.4, 6.3, 3.3, TypeIris.VERSICOLOR);
        virginica = new Iris(3.1, 4.2, 6.7, 0.7, TypeIris.VIRGINICA);

        //pour les tests avec la même fleur
        fleurSame1 = new Iris(5.0, 1.5, 4.5, 2.5);
        fleurSame2 = new Iris(8.0, 4.4, 2.3, 1.8);
        fleurSame3 = new Iris(5.3, 2.8, 5.4, 9.2);
        fleurSame4 = new Iris(7.7, 1.2, 6.2, 0.3);
        
        //pour les tests avec des distances aux limites
        fleurLimite1 = new Iris(10.0, 10.0, 10.0, 10.0);
        fleurLimite2 = new Iris(0.0, 0.0, 0.0, 0.0);
        fleurLimite3 = new Iris(10.0, 0.0, 10.0, 0.0);
        fleurLimite4 = new Iris(0.0, 10.0, 0.0, 10.0);
        caracs = new ArrayList<>(Arrays.asList("petalLength","petalWidth","sepalLength","sepalWidth"));
    }



    //**                 Tests distance                   **/

    @Test
    void test_Distance_with_Different_Simple_Values() {
        assertEquals(5.09901, distanceEuclidienne.distance(setosaSimple, versicolorSimple, caracs), 0.00001);
        assertEquals(8.48528, distanceEuclidienne.distance(virginicaSimple, versicolorSimple, caracs), 0.00001);
        assertEquals(4.69041, distanceEuclidienne.distance(setosaSimple, virginicaSimple, caracs), 0.00001);
    }

    @Test
    void test_Distance_with_Different_Precise_Values() {
        assertEquals(3.71618, distanceEuclidienne.distance(setosa, versicolor, caracs), 0.00001);
        assertEquals(5.41202, distanceEuclidienne.distance(virginica, setosa, caracs), 0.00001);
        assertEquals(4.16173, distanceEuclidienne.distance(versicolor,virginica, caracs), 0.00001);
    }

    @Test
    void test_Distance_with_Same_Values() {
        assertEquals(0.0, distanceEuclidienne.distance(fleurSame1, fleurSame1, caracs), 0.0001);
        assertEquals(0.0, distanceEuclidienne.distance(fleurSame2, fleurSame2, caracs), 0.0001);
        assertEquals(0.0, distanceEuclidienne.distance(fleurSame3, fleurSame3, caracs), 0.0001);
        assertEquals(0.0, distanceEuclidienne.distance(fleurSame4, fleurSame4 , caracs), 0.0001);
    }

    @Test
    void test_Distance_with_Boundary_Values() {
        assertEquals(20, distanceEuclidienne.distance(fleurLimite1, fleurLimite2, caracs), 0.0001);
        assertEquals(20, distanceEuclidienne.distance(fleurLimite3, fleurLimite4, caracs), 0.0001);
        assertEquals(20, distanceEuclidienne.distance(fleurLimite3, fleurLimite4, caracs), 0.0001);
    }
}
