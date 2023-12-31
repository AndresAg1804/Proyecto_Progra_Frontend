package instrumentos.presentation.Calibraciones;

import instrumentos.logic.Calibraciones;
import instrumentos.logic.Mediciones;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelMediciones extends AbstractTableModel implements javax.swing.table.TableModel {
    Calibraciones cali;
    List<Mediciones> rows;
    int[] cols;

    public TableModelMediciones(int[] cols, List<Mediciones> rows){
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
        switch (cols[col]){
            default: return super.getColumnClass(col);
        }
    }


    public int getRowCount() {
        if(rows==null){
            return 0;
        }
        return rows.size();
    }


    public void setValueAt(Object value, int rowIndex, int columIndex){
        String valor = (String) value;
        Mediciones e = rows.get(rowIndex);
        switch (cols[columIndex]){
            case LECTURA: e.setLectura(Integer.parseInt(valor)); break;
        }
    }

    public Object getValueAt(int row, int col) {
        Mediciones sucursal = rows.get(row);
        switch (cols[col]){
            case MEDIDA: return sucursal.getMedida();
            case REFERENCIA: return sucursal.getReferencia();
            case LECTURA: return sucursal.getLectura();
            default: return "";
        }
    }

    public Mediciones getRowAt(int row) {
        return rows.get(row);
    }

    public static final int MEDIDA=0;
    public static final int REFERENCIA=1;
    public static final int LECTURA=2;

    String[] colNames = new String[6];
    private void initColNames(){
        colNames[MEDIDA]= "Medida";
        colNames[REFERENCIA]= "Referencia";
        colNames[LECTURA]= "Lectura";
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        // Habilitar la edición solo para la columna "LECTURA" (índice 2)
        return col == TableModelMediciones.LECTURA;
    }

}