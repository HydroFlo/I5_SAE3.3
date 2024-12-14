package fr.univlille.algoknn;

import fr.univlille.model.Classiflex;
import fr.univlille.model.DonneeBrut;

import java.util.Collection;

public class DistanceEuclidienneNormalisee implements Distance<DonneeBrut>{
    @Override
    public double distance(DonneeBrut data1, DonneeBrut data2, Collection<String> caracteristiques) {
        //initialisation de la distance (par défaut 0)
        double res = 0;
        for(String carac : caracteristiques){
            //On récup min et max pour les calculs normalisé
            double min = MethodeKnn.getMin(carac);
            double max = MethodeKnn.getMax(carac);
            //on ajoute a res la distance entre les 2 points pour la car 'carac'
            res += Math.pow(this.normalise(data1.getCaracterisque(carac),min,max) - this.normalise(data2.getCaracterisque(carac),min, max),2);
        }
        //renvoie la racine carré de 'res' pour avoir la distance euclidienne
        return Math.sqrt(res);
    }

    public double normalise(double val, double min, double max){
        return Classiflex.normaliser_0_1(val, min, max);
    }
}
