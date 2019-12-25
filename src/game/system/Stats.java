package game.system;

public class Stats {

    private int health;
    private int energy; //combines drowsiness, hunger, and pain
    private int thirst;
    private int pain;

    private int strengthStat; //melee and forcing things (open door), carry weight
    private int finesseStat; //finesse based actions, dodge chance, stamina
    private int gunStat; //guns and crossbows
    private int archeryStat; //bows
    private int craftingStat; //crafting
    private int lockpickingStat; //lockpicking
    private int readingStat; //reading books

    private Disease.Type disease; //disease

}
