import java.util.TimerTask;
public class SimTask extends TimerTask {
    private SimEngine simEngine;
    private SpringApplet springApplet;
    private double czas;
    public SimTask(SimEngine se, SpringApplet sa, double t) {
        simEngine = se;
        springApplet = sa;
        czas = t; }
    public void run() {
        simEngine.liczenieWektorow(czas);
        springApplet.repaint();}
}
