package fr.univlille.model;

import com.opencsv.bean.CsvBindByName;

import java.util.*;

public class Pokemon extends DonneeBrut{
    //defense,experience_growth,hp,sp_attack,sp_defense,type1,type2,speed,is_legendary
    @CsvBindByName(column = "name")
    protected String name;
    @CsvBindByName(column = "attack")
    public int atk;
    @CsvBindByName(column = "base_egg_steps")
    public int baseEggStep;
    @CsvBindByName(column = "capture_rate")
    public double captureRate;
    @CsvBindByName(column = "defense")
    public int def;
    @CsvBindByName(column = "experience_growth")
    public int experienceGrowth;
    @CsvBindByName(column = "hp")
    public int hp;
    @CsvBindByName(column = "sp_attack")
    public int spAtk;
    @CsvBindByName(column = "sp_defense")
    public int spDef;
    @CsvBindByName(column = "type1")
    protected TypePoke type1;
    @CsvBindByName(column = "type2")
    protected TypePoke type2;
    @CsvBindByName(column = "speed")
    public double speed;
    @CsvBindByName(column = "is_legendary")
    protected IsLegendary isLegendary;



    public Pokemon() {}

    public Pokemon(int spDef, double captureRate, int spAtk, int def, int experienceGrowth, int baseEggStep, int hp, int atk, double speed){
        this.spDef = spDef; this.captureRate = captureRate; this.spAtk = spAtk;
        this.def = def; this.experienceGrowth = experienceGrowth; this.baseEggStep = baseEggStep;
        this.hp = hp; this.atk = atk; this.speed = speed;
        this.name = "noName";
        this.type1 = TypePoke.UNKNOWN;
        this.type2 = TypePoke.UNKNOWN;
        this.isLegendary = IsLegendary.UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public int getAtk() {
        return atk;
    }

    public int getBaseEggStep() {
        return baseEggStep;
    }

    public double getCaptureRate() {
        return captureRate;
    }

    public int getDef() {
        return def;
    }

    public int getExperienceGrowth() {
        return experienceGrowth;
    }

    public int getHp() {
        return hp;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public int getSpDef() {
        return spDef;
    }

    public TypePoke getType1() {
        return type1;
    }

    public TypePoke getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public IsLegendary getIsLegendary() {
        return isLegendary;
    }

    @Override
    public double getCaracterisque(String colName) {
        return switch (colName){
            case "atk" -> this.getAtk();
            case "baseEggStep" -> this.getBaseEggStep();
            case "captureRate" -> this.getCaptureRate();
            case "def" -> this.getDef();
            case "experienceGrowth" -> this.getExperienceGrowth();
            case "hp" -> this.getHp();
            case "speed" -> this.getSpeed();
            case "spAtk" -> this.getSpAtk();
            case "spDef" -> this.getSpDef();
            default -> 0;
        };
    }

    public static Collection<String> getValuesNames(){
        String[] val = new String[]{"Attack", "Base Egg Step", "Capture Rate", "Defense", "Experience Growth", "Hp", "Sp Attack", "Sp Defense", "Speed"};
        return new HashSet<String>(Arrays.asList(val));
    };

    public Object getType(){
        if(getCurrentEnum().equals("fr.univlille.model.IsLegendary")){
            return this.getIsLegendary();
        }else{
            return this.getType1();
        }
    }
    public List<String> getSelfEnum(){
        return Arrays.asList(new String[]{"TypePoke","IsLegendary"});
    }

    @Override
    public boolean isUnknown() {
        return this.type1 == TypePoke.UNKNOWN;
    }

    @Override
    public void setType(String type) {
        this.type1 = TypePoke.valueOf(type);
    }
}
