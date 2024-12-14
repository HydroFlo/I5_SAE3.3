package fr.univlille.algoknn;

import fr.univlille.model.DonneeBrut;

import java.util.Collection;

public class DistanceEuclidienne implements Distance<DonneeBrut>{
    @Override
    public double distance(DonneeBrut data1, DonneeBrut data2, Collection<String> caracteristiques) {
        double res = 0;
        for(String carac : caracteristiques){
            res += Math.pow(data1.getCaracterisque(carac)-data2.getCaracterisque(carac),2);
        }
        return Math.sqrt(res);
    }

}
