package gameUtil;

import java.util.ArrayList;

public class CardsCollection {
    public ArrayList<Building> cannons = new ArrayList<>();
    public ArrayList<Building> infernoTowers = new ArrayList<>();
    public ArrayList<Troop> barbarians = new ArrayList<>();
    public ArrayList<Troop> archers = new ArrayList<>();
    public ArrayList<Troop> babyDragons = new ArrayList<>();
    public ArrayList<Troop> wizards = new ArrayList<>();
    public ArrayList<Troop> pekkas = new ArrayList<>();
    public ArrayList<Troop> giants = new ArrayList<>();
    public ArrayList<Troop> valkyries = new ArrayList<>();
    public ArrayList<Spell> rages = new ArrayList<>();
    public ArrayList<Spell> fireballs = new ArrayList<>();
    public ArrayList<Spell> arrows = new ArrayList<>();

    {
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

        barbarians.add(new Troop(
                5,
                Target.GROUND,
                "gameUtil/images/Cards/BarbariansCard.png",
                "",
                null,
                false,
                "gameUtil/images/Troops/barbarian.png",
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                1,
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
                true,
                "gameUtil/images/Troops/pekka.png",
                1,
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
                true,
                "gameUtil/images/Troops/pekka.png",
                1,
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
                true,
                "gameUtil/images/Troops/pekka.png",
                1,
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
                true,
                "gameUtil/images/Troops/pekka.png",
                1,
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
                true,
                "gameUtil/images/Troops/pekka.png",
                1,
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
    }
}