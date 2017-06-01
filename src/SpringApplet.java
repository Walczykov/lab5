import java.applet.*;
import java.awt.*;
import java.util.Timer;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class SpringApplet extends Applet implements MouseListener,
        MouseMotionListener, ActionListener{
    private SimEngine simEngine;
    private SimTask simTask;
    private Timer t;
    boolean mysz;
    double newx, newy;
    TextField masa, sprezystosc, tlumienie, dlugoscSwobodna, grawitacja;
    Button reset;
    Date currentDate = new Date();
    public void init() {
        currentDate.setTime(0);
        mysz = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        Vector2D y0 = new Vector2D(400, 70);
        Vector2D y = new Vector2D(400, 100);
        Vector2D v = new Vector2D(0, 0);
        simEngine = new SimEngine(15, 5, 1, 40, 10, y, v, y0);
        t = new Timer();
        simTask = new SimTask(simEngine, this, 0.05);
        t.scheduleAtFixedRate(simTask, currentDate.getTime(), 5);
        setLayout(null);
        reset = new Button ( "reset " );
        masa = new TextField("10");
        sprezystosc = new TextField("9");
        tlumienie = new TextField("10");
        dlugoscSwobodna = new TextField("40");
        grawitacja = new TextField("9.81");
        reset.setBounds(650, 20, 100, 30);
        dlugoscSwobodna.setBounds(20, 20, 100, 25);
        sprezystosc.setBounds(140, 20, 100, 25);
        tlumienie.setBounds(260, 20, 100, 25);
        masa.setBounds(380, 20, 100, 25);
        grawitacja.setBounds(500, 20, 100, 25);
        add(masa);
        add(sprezystosc);
        add(tlumienie);
        add(dlugoscSwobodna);
        add(grawitacja);
        add(reset);
        reset.addActionListener(this);
    }
    public void paint(Graphics g) {
        g.clearRect(0, 0, 600, 600);
        g.drawLine((int) simEngine.dajPkt_zaw().x, (int)
                        simEngine.dajPkt_zaw().y,
                (int) simEngine.dajPol_masy().x, (int)
                        simEngine.dajPol_masy().y);
        g.drawLine((int) simEngine.dajPkt_zaw().x-20, (int)
                        simEngine.dajPkt_zaw().y,
                (int) simEngine.dajPkt_zaw().x+20, (int)
                        simEngine.dajPkt_zaw().y);
        g.drawOval((int) simEngine.dajPol_masy().x - 10, (int)
                simEngine.dajPol_masy().y, 20, 20);
        g.drawString("długość swobodna", 20, 15);
        g.drawString("sprezystość", 140, 15);
        g.drawString("tłumienie", 260, 15);
        g.drawString("masa", 380, 15);
        g.drawString("grawitacja", 500, 15);
    }
    public void mouseDragged(MouseEvent a) {
        if (mysz == true) {
            simEngine.dajPol_masy().x = a.getX();
            simEngine.dajPol_masy().y = a.getY();
            this.repaint();
            System.out.println(mysz);
            a.consume(); }
    }
    public void mousePressed(MouseEvent a) {
        newx = a.getX();
        newy = a.getY();
        if (simEngine.dajPol_masy().x <= newx && newx <=
                simEngine.dajPol_masy().x+20 && simEngine.dajPol_masy().y<=
                newy
                && simEngine.dajPol_masy().y +20 >=
                newy) {
            simTask.cancel();
            mysz = true;
            System.out.println(mysz);
            a.consume(); }
    }
    public void mouseReleased(MouseEvent a) {
        if (mysz == true) {
            t = new Timer();
            simTask = new SimTask(simEngine, this, 0.05);
            t.scheduleAtFixedRate(simTask, currentDate.getTime(), 5);
            mysz = false;
            a.consume();
            System.out.println(mysz); }
    }
    public void mouseMoved(MouseEvent arg0) { }
    public void mouseClicked(MouseEvent arg0) { }
    public void mouseEntered(MouseEvent arg0) { }
    public void mouseExited(MouseEvent arg0) { }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            t.cancel();
            double a =simEngine.dajPkt_zaw().y +
                    Double.parseDouble(dlugoscSwobodna.getText());
            Vector2D pomocniczy = new Vector2D(simEngine.dajPkt_zaw().x,a);
            simEngine.ustawPol_masy(pomocniczy);
            simEngine.ustawMase(Double.parseDouble(masa.getText()));

            simEngine.ustawGrawitacje(Double.parseDouble(grawitacja.getText()));

            simEngine.ustawWsp_spr(Double.parseDouble(sprezystosc.getText()));

            simEngine.ustawWsp_tlum(Double.parseDouble(tlumienie.getText()));

            simEngine.ustawDlug_swob(Double.parseDouble(dlugoscSwobodna.getText()));
            repaint();
        }
    }
}