package instrumentos.presentation.Mensajes;

import instrumentos.logic.ITarget;
import instrumentos.logic.Service;
import instrumentos.logic.Message;
import instrumentos.presentation.Mensajes.Model;
import instrumentos.Application;

import instrumentos.presentation.Mensajes.View;

public class Controller implements ITarget {

    Model model;
    View view;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
        Service.instanceListener().addTarget(this);
        Service.instanceListener().start();
    }

    public void limpiar() {
        model.clear();
        model.commit();
    }

    public void stop() {Service.instanceListener().stop();}

    @Override
    public void deliver(Message message) {
        model.addMessage(message);
        model.commit();
    }
}