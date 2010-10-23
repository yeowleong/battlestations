/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Feb 20, 2008
 * Time: 6:53:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Level {
    private String requiredLevel;
    private String upgradeLevel;

    public Level(String upgradeLevel, String requiredLevel) {
        this.requiredLevel = requiredLevel;
        this.upgradeLevel = upgradeLevel;
    }

    public Level() {
    }

    public String getRequiredLevel() {
        return requiredLevel;
    }

    public String getUpgradeLevel() {
        return upgradeLevel;
    }

    public boolean equals(Object another) {
        if (another instanceof Level) {
            Level l = (Level) another;
            return l.requiredLevel.equals(requiredLevel)
                    && l.upgradeLevel.equals(upgradeLevel);
        }
        return false;
    }

    public String toString() {
        return "L" + requiredLevel + " - " + upgradeLevel;
    }
}
