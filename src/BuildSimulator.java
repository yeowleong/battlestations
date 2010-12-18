import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: yllee
 * Date: Jul 31, 2008
 * Time: 10:07:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class BuildSimulator {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Battle Stations Build Simulator - V20101218");
        frame.setContentPane(new BuildSimulatorForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
