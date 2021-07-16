package gameUtil;

import java.util.ArrayList;
import java.util.List;

public class CardsCollection {
    private static final ArrayList<Building> cannons = new ArrayList<>();
    private static final ArrayList<Building> infernoTowers = new ArrayList<>();
    private static final ArrayList<Building> kingTowers = new ArrayList<>();
    private static final ArrayList<Building> archerTowers = new ArrayList<>();
    private static final ArrayList<Troop> barbarians = new ArrayList<>();
    private static final ArrayList<Troop> archers = new ArrayList<>();
    private static final ArrayList<Troop> babyDragons = new ArrayList<>();
    private static final ArrayList<Troop> wizards = new ArrayList<>();
    private static final ArrayList<Troop> pekkas = new ArrayList<>();
    private static final ArrayList<Troop> giants = new ArrayList<>();
    private static final ArrayList<Troop> valkyries = new ArrayList<>();
    private static final ArrayList<Spell> rages = new ArrayList<>();
    private static final ArrayList<Spell> fireballs = new ArrayList<>();
    private static final ArrayList<Spell> arrows = new ArrayList<>();
    private static final ArrayList<ArrayList<Card>> collectionOfAllCards = new ArrayList<>();

    static {
        cannons.add(new Building(
                3,
                Target.GROUND,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                380,
                30,
                60,
                0.8,
                5.5,
                BuildingName.CANNON,
                "gameUtil/images/Troops/cannon.png"));

        cannons.add(new Building(
                3,
                Target.GROUND,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                418,
                30,
                66,
                0.8,
                5.5,
                BuildingName.CANNON,
                "gameUtil/images/Troops/cannon.png"));

        cannons.add(new Building(
                3,
                Target.GROUND,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                459,
                30,
                72,
                0.8,
                5.5,
                BuildingName.CANNON,
                "gameUtil/images/Troops/cannon.png"));

        cannons.add(new Building(
                3,
                Target.GROUND,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                505,
                30,
                79,
                0.8,
                5.5,
                BuildingName.CANNON,
                "gameUtil/images/Troops/cannon.png"));

        cannons.add(new Building(
                3,
                Target.GROUND,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                554,
                30,
                87,
                0.8,
                5.5,
                BuildingName.CANNON,
                "gameUtil/images/Troops/cannon.png"));

        infernoTowers.add(new Building(
                5,
                Target.ANY,
                "gameUtil/images/Cards/InfernoTowerCard.png",
                "",
                null,
                800,
                40,
                20,
                0.4,
                6,
                BuildingName.INFERNO_TOWER,
                "gameUtil/images/Troops/infernoTower.png"));

        infernoTowers.add(new Building(
                5,
                Target.ANY,
                "gameUtil/images/Cards/InfernoTowerCard.png",
                "",
                null,
                880,
                40,
                22,
                0.4,
                6,
                BuildingName.INFERNO_TOWER,
                "gameUtil/images/Troops/infernoTower.png"));


        infernoTowers.add(new Building(
                5,
                Target.ANY,
                "gameUtil/images/Cards/InfernoTowerCard.png",
                "",
                null,
                968,
                40,
                24,
                0.4,
                6,
                BuildingName.INFERNO_TOWER,
                "gameUtil/images/Troops/infernoTower.png"));

        infernoTowers.add(new Building(
                5,
                Target.ANY,
                "gameUtil/images/Cards/InfernoTowerCard.png",
                "",
                null,
                1064,
                40,
                26,
                0.4,
                6,
                BuildingName.INFERNO_TOWER,
                "gameUtil/images/Troops/infernoTower.png"));

        infernoTowers.add(new Building(
                5,
                Target.ANY,
                "gameUtil/images/Cards/InfernoTowerCard.png",
                "",
                null,
                1168,
                40,
                29,
                0.4,
                6,
                BuildingName.INFERNO_TOWER,
                "gameUtil/images/Troops/infernoTower.png"));

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                2,
                4,
                300,
                75,
                5,
                1.5));

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                2,
                4,
                330,
                82,
                5,
                1.5));

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                2,
                4,
                363,
                90,
                5,
                1.5));

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                2,
                4,
                438,
                99,
                5,
                1.5));

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                2,
                4,
                480,
                109,
                5,
                1.5));

        archers.add(new Troop(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArchersCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/archers.png",
                5,
                2,
                125,
                33,
                5,
                1.2));

        archers.add(new Troop(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArchersCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/archers.png",
                5,
                2,
                127,
                44,
                5,
                1.2));

        archers.add(new Troop(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArchersCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/archers.png",
                5,
                2,
                151,
                48,
                5,
                1.2));

        archers.add(new Troop(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArchersCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/archers.png",
                5,
                2,
                166,
                53,
                5,
                1.2));

        archers.add(new Troop(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArchersCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/archers.png",
                5,
                2,
                182,
                58,
                5,
                1.2));

        babyDragons.add(new Troop(
                4,
                Target.ANY,
                "gameUtil/images/Cards/BabyDragonCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/babyDragon.png",
                3,
                1,
                800,
                100,
                8,
                1.8));

        babyDragons.add(new Troop(
                4,
                Target.ANY,
                "gameUtil/images/Cards/BabyDragonCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/babyDragon.png",
                3,
                1,
                880,
                110,
                8,
                1.8));

        babyDragons.add(new Troop(
                4,
                Target.ANY,
                "gameUtil/images/Cards/BabyDragonCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/babyDragon.png",
                3,
                1,
                968,
                121,
                8,
                1.8));

        babyDragons.add(new Troop(
                4,
                Target.ANY,
                "gameUtil/images/Cards/BabyDragonCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/babyDragon.png",
                3,
                1,
                1064,
                133,
                8,
                1.8));

        babyDragons.add(new Troop(
                4,
                Target.ANY,
                "gameUtil/images/Cards/BabyDragonCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/babyDragon.png",
                3,
                1,
                1168,
                146,
                8,
                1.8));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                340,
                130,
                5,
                1.8));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                374,
                143,
                5,
                1.7));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                411,
                157,
                5,
                1.7));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                452,
                172,
                5,
                1.7));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                496,
                189,
                5,
                1.7));

        wizards.add(new Troop(
                5,
                Target.ANY,
                "gameUtil/images/Cards/WizardCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/wizard.png",
                5,
                1,
                496,
                189,
                5,
                1.7));

        giants.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/GiantCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/giant.png",
                2,
                1,
                2000,
                126,
                3,
                1.5));

        giants.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/GiantCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/giant.png",
                2,
                2,
                2200,
                138,
                3,
                1.5));

        giants.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/GiantCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/giant.png",
                2,
                1,
                2420,
                152,
                3,
                1.5));

        giants.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/GiantCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/giant.png",
                2,
                1,
                2660,
                167,
                3,
                1.5));

        giants.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/GiantCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/giant.png",
                2,
                1,
                2920,
                183,
                3,
                1.5));

        valkyries.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/ValkyrieCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/valkyrie.png",
                2,
                1,
                880,
                120,
                5,
                1.5));

        valkyries.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/ValkyrieCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/valkyrie.png",
                2,
                1,
                968,
                132,
                5,
                1.5));

        valkyries.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/ValkyrieCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/valkyrie.png",
                2,
                1,
                1064,
                145,
                5,
                1.5));

        valkyries.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/ValkyrieCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/valkyrie.png",
                2,
                1,
                1170,
                159,
                5,
                1.5));

        valkyries.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/ValkyrieCard.png",
                "",
                null,
                true,
                "gameUtil/images/Troops/valkyrie.png",
                2,
                1,
                1284,
                175,
                5,
                1.5));

        pekkas.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/MiniPEKKACard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/pekka.png",
                2,
                1,
                600,
                325,
                8,
                1.8));

        pekkas.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/MiniPEKKACard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/pekka.png",
                2,
                1,
                660,
                357,
                8,
                1.8));

        pekkas.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/MiniPEKKACard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/pekka.png",
                2,
                1,
                726,
                393,
                8,
                1.8));

        pekkas.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/MiniPEKKACard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/pekka.png",
                2,
                1,
                798,
                432,
                8,
                1.8));

        pekkas.add(new Troop(
                4,
                Target.GROUND,
                "gameUtil/images/Cards/MiniPEKKACard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/pekka.png",
                2,
                1,
                876,
                474,
                8,
                1.8));

        arrows.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArrowsCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.ARROW,
                4,
                144));

        arrows.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArrowsCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.ARROW,
                4,
                156));

        arrows.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArrowsCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.ARROW,
                4,
                174));

        arrows.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArrowsCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.ARROW,
                4,
                189));

        arrows.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/ArrowsCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.ARROW,
                4,
                210));

        fireballs.add(new Spell(
                4,
                Target.ANY,
                "gameUtil/images/Cards/FireballCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.FIRE_BALL,
                2.5,
                357));

        fireballs.add(new Spell(
                4,
                Target.ANY,
                "gameUtil/images/Cards/FireballCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.FIRE_BALL,
                2.5,
                357));

        fireballs.add(new Spell(
                4,
                Target.ANY,
                "gameUtil/images/Cards/FireballCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.FIRE_BALL,
                2.5,
                393));

        fireballs.add(new Spell(
                4,
                Target.ANY,
                "gameUtil/images/Cards/FireballCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.FIRE_BALL,
                2.5,
                432));

        fireballs.add(new Spell(
                4,
                Target.ANY,
                "gameUtil/images/Cards/FireballCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.FIRE_BALL,
                2.5,
                474));

        rages.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/RageCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.RAGE,
                5,
                60));

        rages.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/RageCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.RAGE,
                5,
                65));

        rages.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/RageCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.RAGE,
                5,
                70));

        rages.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/RageCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.RAGE,
                5,
                75));

        rages.add(new Spell(
                3,
                Target.ANY,
                "gameUtil/images/Cards/RageCard.png",
                "",
                "gameUtil/images/Troops/pekka.png",
                null,
                SpellName.RAGE,
                5,
                80));

        archerTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                1400,
                Integer.MAX_VALUE,
                50,
                0.8,
                7.5,
                BuildingName.ARCHER_TOWER,
                "gameUtil/images/Troops/archerTower.png"));

        archerTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                1512,
                Integer.MAX_VALUE,
                54,
                0.8,
                7.5,
                BuildingName.ARCHER_TOWER,
                "gameUtil/images/Troops/archerTower.png"));

        archerTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                1624,
                Integer.MAX_VALUE,
                58,
                0.8,
                7.5,
                BuildingName.ARCHER_TOWER,
                "gameUtil/images/Troops/archerTower.png"));

        archerTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                1750,
                Integer.MAX_VALUE,
                62,
                0.8,
                7.5,
                BuildingName.ARCHER_TOWER,
                "gameUtil/images/Troops/archerTower.png"));

        archerTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                1890,
                Integer.MAX_VALUE,
                69,
                0.8,
                7.5,
                BuildingName.ARCHER_TOWER,
                "gameUtil/images/Troops/archerTower.png"));


        kingTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                2400,
                Integer.MAX_VALUE,
                50,
                0.8,
                7,
                BuildingName.KING_TOWER,
                "gameUtil/images/Troops/kingTower.png"));

        kingTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                2568,
                Integer.MAX_VALUE,
                53,
                0.8,
                7,
                BuildingName.KING_TOWER,
                "gameUtil/images/Troops/kingTower.png"));

        kingTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                2736,
                Integer.MAX_VALUE,
                57,
                0.8,
                7,
                BuildingName.KING_TOWER,
                "gameUtil/images/Troops/kingTower.png"));

        kingTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                2904,
                Integer.MAX_VALUE,
                60,
                0.8,
                7,
                BuildingName.KING_TOWER,
                "gameUtil/images/Troops/kingTower.png"));

        kingTowers.add(new Building(
                0,
                Target.ANY,
                "gameUtil/images/Cards/CannonCard.png",
                "",
                null,
                3096,
                Integer.MAX_VALUE,
                64,
                0.8,
                7,
                BuildingName.KING_TOWER,
                "gameUtil/images/Troops/kingTower.png"));

        var lists = List.of(
                cannons,
                infernoTowers,
                barbarians,
                archers,
                babyDragons,
                wizards,
                pekkas,
                giants,
                valkyries,
                rages,
                fireballs,
                arrows,
                archerTowers,
                kingTowers);
                lists.forEach(list -> collectionOfAllCards.add((ArrayList<Card>) list));

        cannons.forEach(troop -> troop.setBuildingName(BuildingName.CANNON));
        archerTowers.forEach(troop -> troop.setBuildingName(BuildingName.ARCHER_TOWER));
        kingTowers.forEach(troop -> troop.setBuildingName(BuildingName.KING_TOWER));
        infernoTowers.forEach(troop -> troop.setBuildingName(BuildingName.INFERNO_TOWER));
        barbarians.forEach(troop -> troop.setTroopName(TroopName.BARBARIANS));
        archers.forEach(troop -> troop.setTroopName(TroopName.ARCHER));
        babyDragons.forEach(troop -> troop.setTroopName(TroopName.BABY_DRAGON));
        wizards.forEach(troop -> troop.setTroopName(TroopName.WIZARD));
        pekkas.forEach(troop -> troop.setTroopName(TroopName.PEKKA));
        giants.forEach(troop -> troop.setTroopName(TroopName.GIANT));
        valkyries.forEach(troop -> troop.setTroopName(TroopName.VALKYRIE));
        rages.forEach(troop -> troop.setSpellName(SpellName.RAGE));
        fireballs.forEach(troop -> troop.setSpellName(SpellName.FIRE_BALL));
        arrows.forEach(troop -> troop.setSpellName(SpellName.ARROW));

        infernoTowers.forEach(Building -> {
           Building.setFunctionality(new Function() {
               @Override
               public void execute(AliveTroop troop) {

               }
           });
        });

        infernoTowers.get(0).setMaxDamage(400);
        infernoTowers.get(1).setMaxDamage(440);
        infernoTowers.get(2).setMaxDamage(484);
        infernoTowers.get(3).setMaxDamage(532);
        infernoTowers.get(4).setMaxDamage(584);
    }

    /**
     * return list of cards with indicated level
     * @param level is the level of the cards
     * @return return list of the cards at indicated level
     */
    public static ArrayList<Card> getCardSet(int level) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            cards.add(collectionOfAllCards.get(i).get(level - 1));
        return cards;
    }

    public static ArrayList<Building> getKingTowers() {
        return kingTowers;
    }

    public static ArrayList<Building> getArcherTowers() {
        return archerTowers;
    }
}



