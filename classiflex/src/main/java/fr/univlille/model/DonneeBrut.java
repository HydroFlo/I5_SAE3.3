package fr.univlille.model;

import java.lang.reflect.Field;
import java.util.*;

public abstract class DonneeBrut{
    private static final Collection<String> enumDeType = new HashSet<>(Arrays.asList(new String[]{"fr.univlille.model.TypeIris", "fr.univlille.model.IsLegendary", "fr.univlille.model.TypePoke"}));
    private static String currentEnum;

    /**
     *
     * @return true if type is Unknown
     */
    public abstract boolean isUnknown();

    /**
     *
     * @param nomEnum nom de l'énum dont on veut les valeur
     * @return La liste des valeur de l'enum en paramètre
     * @throws ClassNotFoundException en cas d'erreur de chargement de l'enum
     */

    public static ArrayList<String> getValuesFromEnum(String nomEnum) throws ClassNotFoundException{
        ArrayList<String> champs=new ArrayList<String>();
        // Charger dynamiquement la classe
        Class enumClass = Class.forName(nomEnum);
        // Vérifier si c'est bien un enum
        if (enumClass.isEnum()) {
            // Récupérer les champs
            Object[] enumConstants = enumClass.getEnumConstants();

            //ajouter les noms des champs pour en retourner une liste
            for (Object constant : enumConstants) {
                champs.add(constant.toString());
            }
        }
        return champs;
    }

    /**
     *
     * @param colName nom de l'attribut dont ont veut la valeur
     * @return la valeur de l'attribut colName
     */
    public abstract double getCaracterisque(String colName);

    /**
     *
     * @return le type de le type réel d'une donnée
     */
    public abstract Object getType();

    /**
     *
     * @return la liste des enum autorisée par l'appli
     */
    public static Collection<String> getEnumDeType() {
        return enumDeType;
    }

    /**
     *
     * @return l'enum en cours d'utilisation
     */
    public static String getCurrentEnum() {
        return currentEnum;
    }

    /**
     *
     * @return les enum qui sont des atributs de la classe réelle.
     */
    public List<String> getEnumClasses() {
        List<String> enumNames = new ArrayList<>();
        Class<?> clazz = this.getClass(); // Obtenir la classe réelle
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Class<?> type = field.getType();
            if (type.isEnum()) {
                enumNames.add(type.getSimpleName());
            }
        }
        return enumNames;
    }
    /**
     *
     * @param currentEnum nom de l'enum a mettre en enum courante
     * met à jour l'enum courante
     */
    public static void setCurrentEnum(String currentEnum) {
        if(enumDeType.contains(currentEnum)) DonneeBrut.currentEnum = currentEnum;
    }

    /**
     *
     * @param type le type a mettre en type de la donnée
     * met à jour le type de la donnée
     */
    public abstract void setType(String type);


}