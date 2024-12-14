package fr.univlille.algoknn;


import java.util.*;

import fr.univlille.model.*;

public class MethodeKnn {
    static List<DonneeBrut> datas;

    /**
     *
     * @param nbVoisin nombre de voisin a vérifié
     * @param distance distance a utilisé pour le calcul des voisins
     * @param data la donnée dont on veut les voisin
     * @param caracteristiques Liste des caractéristique pour calcul de distanc
     * @return les kvoisin de item selon la distance, les caractéristique et le nombre de voisin voulu
     */
    private static DonneeBrut[] kVoisins(int nbVoisin, Distance<DonneeBrut> distance,DonneeBrut data, Collection<String> caracteristiques){
        datas.sort(new ComparatorDonnee(data, distance, caracteristiques));
        DonneeBrut[] k = new DonneeBrut[nbVoisin];
        for(int i = 0; i < nbVoisin ; i++){
            k[i] = datas.get(i);
        }
        return k;
    }

    /**
     *
     * @param item item dont on cherche le type
     * @param nbVoisin nombre de voisin a regardé
     * @param distance distance utilisé pour le calcul des voisins
     * @param caracteristiques Liste des caractéristique pour calcul de distance
     * @return le type de l'item en paramètre d'après ses nbVoisin voisin
     */
    public static String whatType(DonneeBrut item, int nbVoisin, Distance<DonneeBrut> distance, Collection<String> caracteristiques){
        DonneeBrut[] kVoisins = kVoisins(nbVoisin, distance, item, caracteristiques);
        //map dont la clé est le String du type d'un voisin et la valeur le nombre d'occurence de celle-ci
        Map<String, Integer> cpt = new HashMap<>();

        //Compte le nombre d'occurence du type des voisin et le stock dans une map
        for(DonneeBrut data1 : kVoisins) {
            //met dans la map la clé 'data' avec comme valeur 0+1 si la clé n'est pas déjà dans la map, valeur+1 si clé dans la map
            cpt.put(data1.getType().toString(), cpt.getOrDefault(data1.getType().toString(), 0) + 1);
        }

        //détermine la val max
        int max = Integer.MIN_VALUE;
        for(Integer integer : cpt.values()){
            if(integer > max) max = integer;
        }

        //recherche quelle clé a comme valeur le max trouvé et la renvoie
        for(String str : cpt.keySet()){
            if(cpt.get(str) == max){
                return str;
            }
        }
        return null;
    }

    /**
     *
     * @param datas liste de DonneeBrut
     * met à jour la liste de DonneBrut en attribut
     */
    public static void setDatas(List<DonneeBrut> datas) {
        MethodeKnn.datas = datas;
    }

    /**
     *
     * @param nomCol nom de la collonne du CSV dont ont veut le min
     * @return la valeur minimal de la colonne en paramètre
     */
    public static double getMin(String nomCol){
        double min = Double.MAX_VALUE;
        for(DonneeBrut data : datas){
            if(data.getCaracterisque(nomCol) < min) min = data.getCaracterisque(nomCol);
        }
        return min;
    }

    /**
     *
     * @param nomCol nom de la collonne du CSV dont ont veut le max
     * @return la valeur maximal de la colonne en paramètre
     */
    public static double getMax(String nomCol){
        double max = Double.MIN_VALUE;
        for(DonneeBrut data : datas){
            if(data.getCaracterisque(nomCol) > max) max = data.getCaracterisque(nomCol);
        }
        return max;
    }
}
