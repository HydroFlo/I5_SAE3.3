package fr.univlille.algoknn;

import fr.univlille.model.Classiflex;
import fr.univlille.model.DonneeBrut;

import java.util.Collection;

public class DistanceManhattanNormalisee implements Distance<DonneeBrut>{
    @Override
    public double distance(DonneeBrut data1, DonneeBrut data2, Collection<String> caracteristiques) {
        double res = 0;
        for(String carac : caracteristiques){
            double min = MethodeKnn.getMin(carac);
            double max = MethodeKnn.getMax(carac);
            res += Math.abs(this.normalise(data1.getCaracterisque(carac), min, max) - this.normalise(data2.getCaracterisque(carac),min,max));
        }
        return res;
    }

    public double normalise(double val, double min, double max){
        return Classiflex.normaliser_0_1(val, min, max);
    }

}
