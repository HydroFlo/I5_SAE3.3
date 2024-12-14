package fr.univlille.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPokemon {

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        // Initialisation d'un objet Pokémon avec des valeurs spécifiques
        pokemon = new Pokemon(90, 45.0, 100, 85, 1250000, 5120, 80, 105, 95.0);
        pokemon.name = "Pikachu";
        pokemon.type1 = TypePoke.ELECTRIC;
        pokemon.type2 = TypePoke.UNKNOWN;
        pokemon.isLegendary = IsLegendary.FALSE;
    }

    @Test
    void testGetCaracteristiqueByColumnName() {
        // Vérifie que chaque nom de colonne renvoie la bonne caractéristique
        assertEquals(105, pokemon.getCaracterisque("atk"), 0.0001);
        assertEquals(5120, pokemon.getCaracterisque("baseEggStep"), 0.0001);
        assertEquals(45.0, pokemon.getCaracterisque("captureRate"), 0.0001);
        assertEquals(85, pokemon.getCaracterisque("def"), 0.0001);
        assertEquals(1250000, pokemon.getCaracterisque("experienceGrowth"), 0.0001);
        assertEquals(80, pokemon.getCaracterisque("hp"), 0.0001);
        assertEquals(100, pokemon.getCaracterisque("spAtk"), 0.0001);
        assertEquals(90, pokemon.getCaracterisque("spDef"), 0.0001);
        assertEquals(95.0, pokemon.getCaracterisque("speed"), 0.0001);
    }

    @Test
    void testGetType() {
        // Vérifie que le type principal est correctement renvoyé
        assertEquals(TypePoke.ELECTRIC, pokemon.getType1());
    }

    @Test
    void testIsUnknown() {
        // Vérifie que `isUnknown` retourne la bonne valeur
        assertFalse(pokemon.isUnknown());

        // Modifie le type pour UNKNOWN et revérifie
        pokemon.type1 = TypePoke.UNKNOWN;
        assertTrue(pokemon.isUnknown());
    }

    @Test
    void testSetType() {
        // Modifie le type principal et vérifie qu'il est correctement défini
        pokemon.setType("FIRE");
        assertEquals(TypePoke.FIRE, pokemon.getType1());
    }

    @Test
    void testGetValuesNames() {
        // Vérifie que la méthode retourne les noms des caractéristiques
        assertTrue(Pokemon.getValuesNames().contains("Attack"));
        assertTrue(Pokemon.getValuesNames().contains("Base Egg Step"));
        assertTrue(Pokemon.getValuesNames().contains("Speed"));
    }
}
