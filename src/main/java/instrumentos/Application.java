package instrumentos;

import javax.swing.*;

// Anner Andrés Angulo Gutierrez 504530978 y Marcos Emilio Vásquez Díaz 801530366


public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {ex.printStackTrace();}

        instrumentos.presentation.tipos.Model tiposModel= new instrumentos.presentation.tipos.Model();
        instrumentos.presentation.tipos.View tiposView = new instrumentos.presentation.tipos.View();
        instrumentos.presentation.Instrumentos.Model InstrumentosModel = new instrumentos.presentation.Instrumentos.Model();
        instrumentos.presentation.Instrumentos.View InstrumentosView = new instrumentos.presentation.Instrumentos.View();
        instrumentos.presentation.Calibraciones.Model CalibracionesModel = new instrumentos.presentation.Calibraciones.Model();
        instrumentos.presentation.Calibraciones.View CalibracionesView = new instrumentos.presentation.Calibraciones.View();
        instrumentos.presentation.AcercaDe.Model AcercaDeModel = new instrumentos.presentation.AcercaDe.Model();
        instrumentos.presentation.AcercaDe.View AcercaDeView = new instrumentos.presentation.AcercaDe.View();
        instrumentos.presentation.Mensajes.Model MensajesModel = new instrumentos.presentation.Mensajes.Model();
        instrumentos.presentation.Mensajes.View MensajesView = new instrumentos.presentation.Mensajes.View();

        tiposController = new instrumentos.presentation.tipos.Controller(tiposView,tiposModel);
        InstrumentosController = new instrumentos.presentation.Instrumentos.Controller(InstrumentosView,InstrumentosModel);
        CalibracionesController = new instrumentos.presentation.Calibraciones.Controller(CalibracionesView,CalibracionesModel);
        AcercaDeController = new instrumentos.presentation.AcercaDe.Controller(AcercaDeView,AcercaDeModel);
        MensajesController = new instrumentos.presentation.Mensajes.Controller(MensajesView,MensajesModel);

        window = new JFrame();
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(10,10,750,400);
        tabs.setBorder(BorderFactory.createTitledBorder("Mantenimientos"));
        tabs.add("Tipos de Instrumento",tiposView.getPanel());
        tabs.add("Instrumentos",InstrumentosView.getPanel());
        tabs.add("Calibraciones",CalibracionesView.getPanel());
        tabs.add("Acerca de",AcercaDeView.getPanel());

        JPanel mensajes = MensajesView.getPanel();
        mensajes.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        mensajes.setBounds(770,10,200,400);

        window.setLayout(null);
        window.add(tabs);
        window.add(mensajes);

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("SILAB: Sistema de Laboratorio Industrial");
        window.setVisible(true);
    }

    public static instrumentos.presentation.tipos.Controller tiposController;

    public static instrumentos.presentation.Instrumentos.Controller InstrumentosController;

    public static instrumentos.presentation.Calibraciones.Controller CalibracionesController;

    public static instrumentos.presentation.AcercaDe.Controller AcercaDeController;

    public static instrumentos.presentation.Mensajes.Controller MensajesController;

    public static JFrame window;

    public final static int MODE_CREATE =1;
    public final static int MODE_EDIT = 2;
}
