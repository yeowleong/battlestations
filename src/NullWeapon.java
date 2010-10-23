/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Jan 4, 2008
 * Time: 10:18:03 PM
 * To change this template use File | Settings | File Templates.
 */
public final class  NullWeapon extends Weapon {

    public NullWeapon() {
    }

    public int getHP() {
        return 0;
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

    public int getWeight() {
        return 0;
    }

    public String getRefineLevel() {
        return "";
    }
    public String toString() {
        return getName();
    }
}