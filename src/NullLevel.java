/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Feb 20, 2008
 * Time: 6:53:04 AM
 * To change this template use File | Settings | File Templates.
 */
public final class NullLevel extends Level {
    private final String requiredLevel = "";
    private final String upgradeLevel = "";

    public NullLevel() {

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
            return l.getRequiredLevel().equals(requiredLevel)
                    && l.getUpgradeLevel().equals(upgradeLevel);
        }
        return false;
    }

    public String toString() {
        return "L" + requiredLevel + " - " + upgradeLevel;
    }
}