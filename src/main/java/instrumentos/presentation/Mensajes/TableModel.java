package instrumentos.presentation.Mensajes;

import instrumentos.logic.Message;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<Message> rows;
    int[] cols;

    public TableModel(int[] cols, List<Message> rows){
        this.cols=cols;
        this.rows=rows;
        initColNames();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        switch (col){
            case 0 : return Icon.class;
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        Message sucursal = rows.get(row);
        switch (col){
            case 0: return this.icon(sucursal);
            default: return "";
        }
    }

    private Icon icon(Message message){
        BufferedImage img = new BufferedImage(150, 30, BufferedImage.TYPE_INT_RGB);
        Color c = null;
        String mark = null;
        switch(message.getTipo()){
            case Message.CREATE:
                c = new Color(77,166,61);
                mark = "+";
                break;
            case Message.UPDATE:
                mark="±";
                c = new Color(242, 171, 78);
                break;
            case Message.DELETE:
                c = new Color(204, 59, 14);
                mark="-";
                break;
        }
        Graphics2D g = img.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, 150, 30);
        g.setFont(new Font("Serif", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("("+mark+" "+ message.getEntidad()+") "+ message.getTexto(), 5, 20);
        return new ImageIcon(img);
    }

    public Message getRowAt(int row) {
        return rows.get(row);
    }

    public static final int Mensaje=0;

    String[] colNames = new String[1];
    private void initColNames(){
        colNames[Mensaje]= "Mensaje";
    }

}
