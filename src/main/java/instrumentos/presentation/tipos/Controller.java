package instrumentos.presentation.tipos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import instrumentos.Application;
import instrumentos.logic.Service;
import instrumentos.logic.TipoInstrumento;

import java.io.FileOutputStream;
import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init();
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        try {
            model.setList(Service.instance().search(model.getFilter()));
        } catch (Exception e) {
        }
        model.commit();
    }

    public void search(TipoInstrumento filter) throws  Exception{
        model.setFilter(filter);
        List<TipoInstrumento> rows = Service.instance().search(model.getFilter());
        model.setList(rows);
        model.setMode(Application.MODE_CREATE);
        model.commit();
    }

    public void save(TipoInstrumento e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new TipoInstrumento());
        search(model.getFilter());
    }


    public void edit(int row){
        TipoInstrumento e = model.getList().get(row);
        try {
            model.setCurrent(Service.instance().read(e));
            model.setMode(Application.MODE_EDIT);
            model.commit();
        } catch (Exception ex) {}
    }


    public void clear() {
        model.setCurrent(new TipoInstrumento());
        model.setMode(Application.MODE_CREATE);
        model.commit();
    }

    public void del(int row) throws Exception{

        TipoInstrumento e = model.getList().get(row);
        // Realiza la eliminación en el servicio (void)
            Service.instance().delete(e);

            // Verifica si el elemento se ha eliminado correctamente en el modelo local
            if (model.getList().remove(e)) {
                // Actualiza la vista con la lista modificada
                int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.UNIDAD};
                view.getList().setModel(new TableModel(cols, model.getList()));
            } else {
                throw new Exception("Error al eliminar el elemento...");
            }


    }


    public void generatePdfReport() throws Exception{
        Document document = new Document();

        try {
            List<TipoInstrumento> list = Service.instance().search(new TipoInstrumento());
            PdfWriter.getInstance(document, new FileOutputStream("reporteTipos.pdf"));
            document.open();

            // Título del reporte
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Tipos de Instrumentos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Image img = Image.getInstance("Instrumentos/src/main/resources/instrumentos/presentation/instrumentos.presentation.icons/LogoUNA.svg.png"); // Reemplaza con la ruta de tu imagen
            img.setAlignment(Element.ALIGN_CENTER);
            img.scaleToFit(300, 200); // Ajusta el tamaño de la imagen según tus necesidades
            document.add(img);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Tabla de contenido
            PdfPTable table = new PdfPTable(3); // 3 columnas
            table.setWidthPercentage(100);

            // Encabezados de la tabla
            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell cell = new PdfPCell(new Phrase("Código", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Nombre", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Unidad", tableHeaderFont));
            table.addCell(cell);

            // Datos de la tabla
            Font tableDataFont = FontFactory.getFont(FontFactory.HELVETICA);
            for (TipoInstrumento tipo : list) {
                table.addCell(new Phrase(tipo.getCodigo(), tableDataFont));
                table.addCell(new Phrase(tipo.getNombre(), tableDataFont));
                table.addCell(new Phrase(tipo.getUnidad(), tableDataFont));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Creadores: Anner Andrés Angulo Gutiérrez y Marcos Emilio Vásquez Díaz", tableDataFont));

            document.close();
        } catch (Exception e) {
            throw new Exception("Error al generar el reporte");
        }
    }


}