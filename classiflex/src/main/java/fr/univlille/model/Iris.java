package fr.univlille.model;

import com.opencsv.bean.CsvBindByName;


public class Iris extends DonneeBrut {
    @CsvBindByName(column = "petal.length")
    public double petalLength;
    @CsvBindByName(column = "petal.width")
    public double petalWidth;
    @CsvBindByName(column = "sepal.length")
    public double sepalLength;
    @CsvBindByName(column = "sepal.width")
    public double sepalWidth;
    @CsvBindByName(column = "variety")
    protected TypeIris variety;


    /**
     * Création d'un Iris entièrement paramétré
     * @param petalLength
     * @param petalWidth
     * @param sepalLength
     * @param sepalWidth
     * @param type
     */
    public Iris(double petalLength, double petalWidth, double sepalLength, double sepalWidth, TypeIris type) {
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.variety = type;
    }

    /**
     * Création d'un Iris de variété inconnu
     * @param petalLength
     * @param petalWidth
     * @param sepalLength
     * @param sepalWidth
     */
    public Iris(double petalLength, double petalWidth, double sepalLength, double sepalWidth) {
        this(petalLength, petalWidth, sepalLength, sepalWidth, TypeIris.UNKNOWN);
    }

    public Iris(){}

    @Override
    public String toString() {
        return  "petalLength : " + petalLength +
                ", petalWidth : " + petalWidth +
                ", sepalLength : " + sepalLength +
                ", sepalWidth : " + sepalWidth +
                ", variety : " + variety;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getCaracterisque(String colName){
        return switch (colName) {
            case "sepalLength" -> this.getSepalLength();
            case "sepalWidth" -> this.getSepalWidth();
            case "petalLength" -> this.getPetalLength();
            case "petalWidth" -> this.getPetalWidth();
            default -> -1;
        };
    }

    @Override
    public TypeIris getType() {
        return this.variety;
    }

    @Override
    public boolean isUnknown() {
        return this.variety == TypeIris.UNKNOWN;
    }

    @Override
    public void setType(String type) {
        this.variety = TypeIris.valueOf(type);
    }
}