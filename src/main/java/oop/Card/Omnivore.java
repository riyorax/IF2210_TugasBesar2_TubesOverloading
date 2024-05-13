package oop.Card;

public class Omnivore extends Creature implements Animal { 

    // pas dibikin weight masi 0
    public Omnivore(String pathToImg, String name, int harvestGoal) {
        super(pathToImg, name, harvestGoal, 0);
    }

    public int getWeight() {
        return this.getHarvestProgress();
    }

    @Override
    void harvest() {
        System.out.println("Panenn");
    }

    public void eat() {
        System.out.println("Makann");
    }
}
