package instrumentos.presentation.Instrumentos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import instrumentos.Application;
import instrumentos.logic.Instrumento;
import instrumentos.logic.Service;
import instrumentos.logic.TipoInstrumento;

import java.io.FileOutputStream;
import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        try {
            model.init(Service.instance().search(new TipoInstrumento()));
        } catch (Exception e) {
        }
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

    public void search(Instrumento filter) throws  Exception{
        model.setFilter(filter);
        List<Instrumento> rows = Service.instance().search(model.getFilter());
        if (rows.isEmpty()){
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setList(rows);
        model.setMode(Application.MODE_CREATE);
        model.commit();
    }

    public void save(Instrumento e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setFilter(new Instrumento());
        search(model.getFilter());
    }

    public void edit(int row){
        Instrumento e = model.getList().get(row);
        try {
            Application.CalibracionesController.setInstrumento(e);
            model.setCurrent(Service.instance().read(e));
            model.setMode(Application.MODE_EDIT);
            model.commit();
        } catch (Exception ex) {}
    }

    public void clear() {
        model.setCurrent(new Instrumento());
        model.setMode(Application.MODE_CREATE);
        model.commit();
    }

    public void shown() {
        try {
            model.setListTypes(Service.instance().search(new TipoInstrumento()));
        } catch (Exception e) {
        }
        model.commit();
    }

    public void del(int row) throws Exception{

        Instrumento e = model.getList().get(row);
        // Realiza la eliminación en el servicio (void)
        if(e.getCalibraciones().isEmpty()) {
            Service.instance().delete(e);

            // Verifica si el elemento se ha eliminado correctamente en el modelo local
            if (model.getList().remove(e)) {
                // Actualiza la vista con la lista modificada
                int[] cols = {TableModel.SERIE, TableModel.DESCRIPCION, TableModel.MINIMO, TableModel.MAXIMO, TableModel.TOLERANCIA};
                view.getList().setModel(new TableModel(cols, model.getList()));
            } else {
                throw new Exception("Error al eliminar el elemento...");
            }
        }else {
            throw new Exception("No se puede eliminar el instrumento porque tiene calibraciones asociadas");
        }

    }

    public void generatePdfReport() throws Exception {
        Document document = new Document();

        try {
            List<Instrumento> list = Service.instance().search(new Instrumento());
            PdfWriter.getInstance(document, new FileOutputStream("reporteInstrumentos.pdf"));
            document.open();

            // Título del reporte
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Instrumentos", titleFont);
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
            PdfPTable table = new PdfPTable(5); // 3 columnas
            table.setWidthPercentage(100);

            // Encabezados de la tabla
            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell cell = new PdfPCell(new Phrase("No.Serie", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Descripcion", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Minimo", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Maximo", tableHeaderFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Tolerancia", tableHeaderFont));
            table.addCell(cell);

            // Datos de la tabla
            Font tableDataFont = FontFactory.getFont(FontFactory.HELVETICA);
            for (Instrumento instrumento : list) {
                table.addCell(new Phrase(instrumento.getSerie(), tableDataFont));
                table.addCell(new Phrase(instrumento.getDescripcion(), tableDataFont));
                table.addCell(new Phrase(String.valueOf(instrumento.getMinimo()), tableDataFont));
                table.addCell(new Phrase(String.valueOf(instrumento.getMaximo()), tableDataFont));
                table.addCell(new Phrase(String.valueOf(instrumento.getTolerancia()), tableDataFont));
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