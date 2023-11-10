package instrumentos.presentation.Mensajes;

import instrumentos.Application;
import instrumentos.logic.Instrumento;
import instrumentos.logic.Message;
import instrumentos.logic.TipoInstrumento;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Message> list = new ArrayList<Message>();

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(0);
    }

    public void addMessage(Message message){
        list.add(0, message);
    }

    public List<Message> getList() {
        return list;
    }

    public void clear() {
        list = new ArrayList<Message>();
    }
}



