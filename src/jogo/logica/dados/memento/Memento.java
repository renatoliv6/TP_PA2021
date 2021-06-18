package jogo.logica.dados.memento;

import java.io.*;

public class Memento implements Serializable
{
    public static final long serialVersionUID =  1L;

    private byte[] snapshot;

    public Memento(Object obj) throws IOException
    {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        }finally {
            if(oos!=null) oos.close();
        }

    }

    public Object getSnapshot() throws IOException, ClassNotFoundException
    {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(snapshot));
            return ois.readObject();
        }finally {
            if(ois != null) ois.close();
        }
    }
}
