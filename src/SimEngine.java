public class SimEngine {
    private double masa;
    private double wsp_spr;
    private double wsp_tlum;
    private double dlug_swob;
    private Vector2D pol_masy;
    private Vector2D pred_masy;
    private Vector2D pkt_zaw;
    private double grawitacja;
    public SimEngine(double mm, double kk, double cc, double LL0, double gg,Vector2D yy, Vector2D vv, Vector2D yy00) {
        if( (mm<0) || (kk<0) || (cc<0) || (LL0<0) || (gg<0) )
            System.out.println("Złe dane wejściowe!");
        else masa = mm; wsp_spr = kk; wsp_tlum = cc; dlug_swob =
                LL0; grawitacja = gg; pol_masy = yy; pred_masy = vv;
        pkt_zaw = yy00; }
    public double dajMase() {return masa;}
    public double dajWsp_spr() {return wsp_spr;}
    public double dajWsp_tlum() {return wsp_tlum;}
    public double dajDlug_swob() {return dlug_swob;}
    public Vector2D dajPol_masy() {return pol_masy;}
    public Vector2D dajPred_masy() {return pred_masy;}
    public Vector2D dajPkt_zaw() {return pkt_zaw;}
    public double dajGrawitacje() {return grawitacja;}
    void ustawPol_masy(Vector2D pMasy) {pol_masy=pMasy;}
    void ustawPkt_zaw(Vector2D pktZaw) {pkt_zaw = pktZaw;}
    void ustawPred_masy(Vector2D predMasy) {pred_masy=predMasy;}
    void ustawWsp_tlum(double wspTlum) {wsp_tlum = wspTlum;}
    void ustawMase(double m) {masa = m;}
    void ustawWsp_spr(double wspSpr) { if (wspSpr < 0) wsp_spr = 0; else
        wsp_spr = wspSpr; }
    void ustawDlug_swob(double dlugSwob) { if (dlugSwob < 0) dlug_swob = 0;
    else dlug_swob = dlugSwob; }
    void ustawGrawitacje(double graw){ grawitacja=graw; }
    public void liczenieWektorow(double timestep) {
        Vector2D silaGrawitacji = new Vector2D(0, masa*grawitacja);
        Vector2D silaSprezystosci = new Vector2D();
        Vector2D silaTlumienia = new Vector2D();
        silaSprezystosci = pkt_zaw.subVectors(dajPol_masy()).normalVector().mulVector

                                (wsp_spr*(pol_masy.subVectors(dajPkt_zaw()).length()-dajDlug_swob()));
        silaTlumienia = pred_masy.mulVector(-wsp_tlum);
        Vector2D silaWypadkowa = new Vector2D();
        silaWypadkowa =
                silaSprezystosci.addVectors(silaTlumienia).addVectors(silaGrawitacji);
        pred_masy = pred_masy.addVectors(silaWypadkowa.mulVector(timestep/masa));
        pol_masy = pol_masy.addVectors(pred_masy.mulVector(timestep));
    }
}
