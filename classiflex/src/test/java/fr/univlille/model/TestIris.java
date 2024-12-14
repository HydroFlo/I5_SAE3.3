package fr.univlille.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TestIris {
    
    private Iris fleur;


    @BeforeEach
    public void initialisation(){
        fleur=new Iris(0.1, 1.2, 6.3,9.5);
    }


    //**                    Tests toString                        **/

    @Test
    public void test_To_String_Sans_Variété(){
        Assertions.assertEquals(new Iris(4.5, 5.6, 2.3, 8.1).toString(),
                                         "petalLength : 4.5, petalWidth : 5.6, sepalLength : 2.3, sepalWidth : 8.1, variety : UNKNOWN" );

        Assertions.assertEquals(new Iris(4.0, 0.2, 94, 6.8).toString(),
                                         "petalLength : 4.0, petalWidth : 0.2, sepalLength : 94.0, sepalWidth : 6.8, variety : UNKNOWN" );
    }

    @Test
    public void test_To_String_Nombres_Particuliers(){
        Assertions.assertEquals(new Iris(9.11, 5.63, 2, 8.1, TypeIris.SETOSA).toString(),
                                         "petalLength : 9.11, petalWidth : 5.63, sepalLength : 2.0, sepalWidth : 8.1, variety : SETOSA");

        Assertions.assertEquals(new Iris(11.963, 4, 0, 7, TypeIris.VIRGINICA).toString(),
                                         "petalLength : 11.963, petalWidth : 4.0, sepalLength : 0.0, sepalWidth : 7.0, variety : VIRGINICA");

        Assertions.assertEquals(new Iris(0.0000001, 2.013256400120, 1.26500000, 4.235548, TypeIris.VERSICOLOR).toString(), 
                                         "petalLength : 1.0E-7, petalWidth : 2.01325640012, sepalLength : 1.265, sepalWidth : 4.235548, variety : VERSICOLOR");

        Assertions.assertEquals(new Iris(2.032565412365874, 4.255564581872935, 2.003104010310000, 56.233163200000, TypeIris.SETOSA).toString(),
                                         "petalLength : 2.032565412365874, petalWidth : 4.255564581872935, sepalLength : 2.00310401031, sepalWidth : 56.2331632, variety : SETOSA");

        Assertions.assertEquals(new Iris(656513653355489.0, 247365724945.0, 2126423726444.125, 49132361246278.1, TypeIris.VIRGINICA).toString(),
                                         "petalLength : 6.56513653355489E14, petalWidth : 2.47365724945E11, sepalLength : 2.126423726444125E12, sepalWidth : 4.91323612462781E13, variety : VIRGINICA");

        Assertions.assertEquals(new Iris(151331559.1326221, 656451362354.0251000000, 511151332152.1254000, 315133515168.0, TypeIris.VERSICOLOR).toString(),
                                         "petalLength : 1.513315591326221E8, petalWidth : 6.564513623540251E11, sepalLength : 5.111513321521254E11, sepalWidth : 3.15133515168E11, variety : VERSICOLOR");
    }

    //**                    Test GetCorrespondance(String)                     **/

    @Test
    public void test_Get_Correspondance_String_Cas0(){
        Assertions.assertEquals(fleur.getCaracterisque("sepalLength"), 6.3 );
    }

    @Test
    public void test_Get_Correspondance_String_Cas1(){
        Assertions.assertEquals(fleur.getCaracterisque( "sepalWidth"), 9.5 );
    }

    @Test
    public void test_Get_Correspondance_String_Cas2(){
        Assertions.assertEquals(fleur.getCaracterisque("petalLength"), 0.1 );
    }

    @Test
    public void test_Get_Correspondance_String_Cas3(){
        Assertions.assertEquals(fleur.getCaracterisque("petalWidth"), 1.2 );
    }

    @Test
    public void test_Get_Correspondance_String_Cas4(){
        Assertions.assertEquals(fleur.getCaracterisque("Bike power"), -1.0 );
        Assertions.assertEquals(fleur.getCaracterisque("Est-ce que tout les tests sont lus?"), -1.0 );
        Assertions.assertEquals(fleur.getCaracterisque("N'hésitez pas à me mettre des points bonus ;-)"), -1.0 );
        Assertions.assertEquals(fleur.getCaracterisque("Mathieu Poumaere"), -1.0 );
        Assertions.assertEquals(fleur.getCaracterisque(""), -1.0 );
    }
}