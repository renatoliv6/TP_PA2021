package jogo.logica.dados.memento;

import java.io.IOException;

public interface IOriginator
{
    public Memento createMemento(boolean replay) throws IOException;
    public void restoreMemento(Memento memento, boolean replay) throws IOException, ClassNotFoundException;
}
