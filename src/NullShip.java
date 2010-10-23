public final class NullShip extends Ship {

    public NullShip() {

    }

    public void setLevel(Level level) {
    }

    public Level getLevel() {
        return NULL_LEVEL; //new NullLevel();
    }

    public String getName() {
        return "";
    }

    public int getSpeed() {
        return 0;
    }

    public int getCapacity() {
        return 0;
    }

    public int getHP() {
        return 0;
    }

    public String toString() {
        return "";
    }

    public int getNumWeaponSlots() {
        return 0;
    }

    public String getRequiredLevel() {
        return "";
    }

    public Object clone() {
//        Ship s = new Ship();
        return NULL_SHIP;
    }

    private static final NullShip NULL_SHIP = new NullShip();
    private static final NullLevel NULL_LEVEL = new NullLevel();

}