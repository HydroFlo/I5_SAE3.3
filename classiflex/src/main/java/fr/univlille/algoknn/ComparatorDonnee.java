package fr.univlille.algoknn;

import fr.univlille.model.DonneeBrut;

import java.util.Collection;
import java.util.Comparator;

public class ComparatorDonnee implements Comparator<DonneeBrut> {
    DonneeBrut referenceData;
    Distance<DonneeBrut> dist;
    Collection<String> caracteristiques;
    public ComparatorDonnee(DonneeBrut referenceData, Distance<DonneeBrut> dist, Collection<String> caracteristiques) {
        this.referenceData = referenceData;
        this.dist = dist;
        this.caracteristiques = caracteristiques;

    }
    @Override
    public int compare(DonneeBrut data1, DonneeBrut data2) {
        // Gestion des cas où les distances sont nulles
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Null parameter forbidden");
        }

        // Calcul des distances pour les deux données
        double dist1 = dist.distance(data1, referenceData, caracteristiques);
        double dist2 = dist.distance(data2, referenceData, caracteristiques);

        // Comparaison des distances
        return Double.compare(dist1, dist2);
    }
}
