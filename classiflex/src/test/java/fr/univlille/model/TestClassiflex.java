package fr.univlille.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestClassiflex {

    static String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"data"+System.getProperty("file.separator")+"iris.csv";
    
    
    @Test
    public void test_Charger () throws IOException {
        System.out.println(path);
        Classiflex cf = new Classiflex();
        cf.setJeuDeDonnee(path, Iris.class);
        System.out.println(cf.jeuDeDonnee);
    }



    //**                 Tests Normaliser_0_1                    **/

    @Test
    public void test_Revoie_D_Une_Erreur(){
        Assertions.assertEquals(-1.0, Classiflex.normaliser_0_1(13.0, 15.0, 14.0));
        Assertions.assertEquals(-1.0, Classiflex.normaliser_0_1(15.152423, 15.152423, 15.152423));
        Assertions.assertEquals(-1.0, Classiflex.normaliser_0_1(216.3, 15.6, 19.7));
        Assertions.assertEquals(-1.0, Classiflex.normaliser_0_1(14.3, 15.6, 17.0));
        Assertions.assertEquals(-1.0, Classiflex.normaliser_0_1(15.61364499999, 15.613645, 17.0));
    }

    @Test
    public void test_Valeur_Egale_Au_Minimum(){
        Assertions.assertEquals(0.0, Classiflex.normaliser_0_1(5.0, 5.0, 10.0),0.000001);
        Assertions.assertEquals(0.0, Classiflex.normaliser_0_1(4.15, 4.15, 17.4),0.000001);
        Assertions.assertEquals(0.0, Classiflex.normaliser_0_1(8.27, 8.27, 16.371),0.000001);
    }

    @Test
    public void test_Valeur_Egale_Au_Max(){
        Assertions.assertEquals(1.0, Classiflex.normaliser_0_1(10.0, 5.0, 10.0),0.000001);
        Assertions.assertEquals(1.0, Classiflex.normaliser_0_1(17.4, 4.1, 17.4),0.000001);
        Assertions.assertEquals(1.0, Classiflex.normaliser_0_1(16.3, 8.2, 16.3),0.000001);
    }

    @Test
    public void test_Valeur_Egale_Moyenne(){
        Assertions.assertEquals(0.5, Classiflex.normaliser_0_1(5.0, 0.0, 10.0),0.000001);
        Assertions.assertEquals(0.5, Classiflex.normaliser_0_1(10.0, 5.0, 15.0),0.000001);
        Assertions.assertEquals(0.5, Classiflex.normaliser_0_1(16.3, 15.6, 17.0),0.000001);
    }

    @Test
    public void test_Valeur_Noramle(){
        Assertions.assertEquals(0.15, Classiflex.normaliser_0_1(15.0, 0.0, 100.0),0.000001);
        Assertions.assertEquals(0.85, Classiflex.normaliser_0_1(85.0, 0.0, 100.0),0.000001);
        Assertions.assertEquals(0.8150022, Classiflex.normaliser_0_1(43.28, 6.23, 51.69),0.000001);
        Assertions.assertEquals(0.2553895, Classiflex.normaliser_0_1(17.84, 6.23, 51.69),0.000001);
    }

    @Test
    public void test_Valeur_Retournee_Infinie(){
        Assertions.assertEquals((1.0/6.0), Classiflex.normaliser_0_1(50.0, 45.0, 75.0),0.000001);
        Assertions.assertEquals((5.0/7.0), Classiflex.normaliser_0_1(60.0, 35.0, 70.0),0.000001);
        Assertions.assertEquals((2.0/9.0), Classiflex.normaliser_0_1(23.33778, 14.32, 54.9),0.000001);
    }
}
