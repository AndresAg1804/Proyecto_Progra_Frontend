package instrumentos.presentation.Mensajes;

import instrumentos.Application;
import instrumentos.presentation.Mensajes.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel1;
    private JTable table;
    private JButton clearMessages;
    private JPanel panel;

    public View(){
        clearMessages.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.limpiar();
                super.mouseClicked(e);
            }
        });
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
            int[] cols = {TableModel.Mensaje};
            table.setModel(new TableModel(cols, model.getList()));
            table.setRowHeight(30);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(200);
            this.panel.revalidate();
    }

    public JPanel getPanel() {
        return panel;
    }
}
