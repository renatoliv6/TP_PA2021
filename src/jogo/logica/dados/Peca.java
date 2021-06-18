package jogo.logica.dados;

import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Peca implements Serializable
{
    public static final long serialVersionID = 1;

    int linha;
    int coluna;
    Identificador identificador;

    public Peca(Peca peca)
    {
        this.linha = peca.linha;
        this.coluna = peca.coluna;
        this.identificador = peca.identificador;
    }

    public Peca(int linha, int coluna, Identificador identificador)
    {
        this.linha = linha;
        this.coluna = coluna;
        this.identificador = identificador;
    }

    public Identificador getIdentificador() { return identificador; }

}
