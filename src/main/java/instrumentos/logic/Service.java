package instrumentos.logic;


import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Service implements IService, IListener {  // PROXY
    private static Service theInstance;
    public static IService instance(){
        if (theInstance==null){ 
            theInstance=new Service();
        }
        return theInstance;
    }

    public static IListener instanceListener(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }


    ObjectSocket os = null;
    ObjectSocket as = null;
    ITarget target;

    public Service() {
        try{ this.connect();}
        catch(Exception e){ System.exit(-1);}
    }

    private void connect() throws Exception{
        os = new ObjectSocket(new Socket(Protocol.SERVER,Protocol.PORT));
        os.out.writeInt(Protocol.SYNC);
        os.out.flush();
        os.sid = (String) os.in.readObject();   // stores returned session ID
    }

    private void disconnect() throws Exception{
        os.out.writeInt(Protocol.DISCONNECT);
        os.out.flush();
        os.skt.shutdownOutput();
        os.skt.close();
    }

    @Override
    public void create(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.CREATETI);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Tipo de Instrumento duplicado");
    }

    @Override
    public TipoInstrumento read(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.READTI);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (TipoInstrumento) os.in.readObject();
        }
        else throw new Exception("Tipo de Instrumento no existe");
    }

    @Override
    public void update(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.UPDATETI);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Tipo de Instrumento no existe");
    }

    @Override
    public void delete(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.DELETETI);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Tipo de Instrumento no existe");
    }

    @Override
    public List<TipoInstrumento> search(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.SEARCHTI);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (List<TipoInstrumento>) os.in.readObject();
        }
        else throw new Exception("No existen Tipos de Instrumentos");
    }

    @Override
    public void create(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.CREATEI);
        os.out.writeObject(instrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Instrumento duplicado");
    }

    @Override
    public Instrumento read(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.READI);
        os.out.writeObject(instrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (Instrumento) os.in.readObject();
        }
        else throw new Exception("Instrumento no existe");
    }

    @Override
    public void update(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.UPDATEI);
        os.out.writeObject(instrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Instrumento no existe");
    }

    @Override
    public void delete(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.DELETEI);
        os.out.writeObject(instrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Instrumento no existe");
    }

    @Override
    public List<Instrumento> search(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.SEARCHI);
        os.out.writeObject(instrumento);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (List<Instrumento>) os.in.readObject();
        }
        else throw new Exception("No existen Instrumentos");
    }

    @Override
    public void create(Calibraciones calibraciones) throws Exception {
        os.out.writeInt(Protocol.CREATEC);
        os.out.writeObject(calibraciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Calibracion duplicada");
    }

    @Override
    public Calibraciones read(Calibraciones calibraciones) throws Exception {
        os.out.writeInt(Protocol.READC);
        os.out.writeObject(calibraciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (Calibraciones) os.in.readObject();
        }
        else throw new Exception("Calibracion no existe");
    }

    @Override
    public void update(Calibraciones calibraciones) throws Exception {
        os.out.writeInt(Protocol.UPDATEC);
        os.out.writeObject(calibraciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Calibracion no existe");
    }

    @Override
    public void delete(Calibraciones calibraciones) throws Exception {
        os.out.writeInt(Protocol.DELETEC);
        os.out.writeObject(calibraciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Calibracion no existe");
    }

    @Override
    public List<Calibraciones> search(Calibraciones calibraciones) throws Exception {
        os.out.writeInt(Protocol.SEARCHC);
        os.out.writeObject(calibraciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (List<Calibraciones>) os.in.readObject();
        }
        else throw new Exception("No existen calibraciones");
    }

    @Override
    public void create(Mediciones mediciones) throws Exception {
        os.out.writeInt(Protocol.CREATEM);
        os.out.writeObject(mediciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Medicion duplicada");
    }

    @Override
    public Mediciones read(Mediciones mediciones) throws Exception {
        os.out.writeInt(Protocol.READM);
        os.out.writeObject(mediciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (Mediciones) os.in.readObject();
        }
        else throw new Exception("Medicion no existe");
    }

    @Override
    public void update(Mediciones mediciones) throws Exception {
        os.out.writeInt(Protocol.UPDATEM);
        os.out.writeObject(mediciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Medicion no existe");
    }

    @Override
    public void delete(Mediciones mediciones) throws Exception {
        os.out.writeInt(Protocol.DELETEM);
        os.out.writeObject(mediciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){}
        else throw new Exception("Medicion no existe");
    }

    @Override
    public List<Mediciones> search(Mediciones mediciones) throws Exception {
        os.out.writeInt(Protocol.SEARCHM);
        os.out.writeObject(mediciones);
        os.out.flush();
        if(os.in.readInt() == Protocol.ERROR_NO_ERROR){
            return (List<Mediciones>) os.in.readObject();
        }
        else throw new Exception("No existen mediciones");
    }

    @Override
    public List<Mediciones> searchMedicionesByCalibracion(String s) {
        try {
            os.out.writeInt(Protocol.SEARCHMBC);
            os.out.writeObject(s);
            os.out.flush();
            if (os.in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Mediciones>) os.in.readObject();
            } else throw new Exception("No existen mediciones");
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Calibraciones> searchCalibracionesByInstrumento(String s) {
        try {
            os.out.writeInt(Protocol.SEARCHCBI);
            os.out.writeObject(s);
            os.out.flush();
            if (os.in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Calibraciones>) os.in.readObject();
            } else throw new Exception("No existen calibraciones");
        }catch (Exception e){
            return null;
        }
    }


    // LISTENING FUNCTIONS

    boolean continuar = true;
    public void startListening(){
        try{
            as = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));
            as.sid = os.sid;
            as.out.writeInt(Protocol.ASYNC);
            as.out.writeObject(as.sid);
            as.out.flush();
        } catch(IOException e) {System.out.println("error");}

        Thread t = new Thread(new Runnable(){
            public void run(){
                listen();
            }
        });

        continuar = true;
        t.start();
    }

    public void stopListening(){
        continuar=false;
    }

    public void listen(){
        int method;
        while (continuar) {
            try {
                method = as.in.readInt();
                switch(method){
                    case Protocol.DELIVER:
                        try {
                            Message message=(Message)as.in.readObject();
                            deliver(message);
                        } catch (ClassNotFoundException ex) {}
                        break;
                }
            } catch (IOException  ex) {
                continuar = false;
            }
        }
        // cerrar socket y streams
        try{
            as.skt.shutdownOutput();
            as.skt.close();
        } catch(IOException e) {}
    }

    private void deliver( final Message message ){
        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           target.deliver(message);
                                       }
                                   }
        );
    }

    @Override
    public void addTarget(ITarget target){
        this.target=target;
    }

    @Override
    public void start(){
        this.startListening();
    }

    @Override
    public void stop(){
        this.stopListening();
        try{
            this.disconnect();
        } catch(Exception e){
        }
    }

}
