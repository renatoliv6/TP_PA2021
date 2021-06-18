package jogo.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.JogoCareTakerObservable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static jogo.ui.gui.Constants.*;

public class EscolhaCorPecas extends HBox implements Serializable {
    private final JogoCareTakerObservable jogo;

    private List<EscolhaCorPeca> pecas;

    static int nextId = 1;
    boolean esc_Coluna;

    public EscolhaCorPecas(JogoCareTakerObservable jogo, boolean esc_Coluna) {
        this.jogo = jogo;
        this.esc_Coluna = esc_Coluna;

        pecas = new ArrayList<>();

        if(!esc_Coluna)
        {
            pecas.add(new EscolhaCorPeca(Color.GREEN, false));
            pecas.add(new EscolhaCorPeca(Color.RED,false));
            pecas.add(new EscolhaCorPeca(Color.YELLOW,false));
            pecas.add(new EscolhaCorPeca(Color.BLACK,false));
            pecas.add(new EscolhaCorPeca(Color.GRAY,false));
            setSpacing(ESPACO_ENTRE_ITEMS_ESCOLHA_ID);
        }
        else
        {
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            pecas.add(new EscolhaCorPeca(Color.DARKGRAY, true));
            setSpacing(ESPACO_ENTRE_ITEMS_ESCOLHA_COLUNA);
        }

        getChildren().addAll(pecas);
        setAlignment(Pos.CENTER);

    }

    public List<EscolhaCorPeca> getPecas() {return pecas;}

    public Circle getPecaSelecionada()
    {
        for(EscolhaCorPeca c : pecas)
        {
            if(c.selecionada)
                return c;
        }

        return null;
    }

    public void setCor(Color cor)
    {
        for(EscolhaCorPeca p : pecas)
            p.setFill(cor);
    }
    public void reset(){
        if(!esc_Coluna)
        {
            for (EscolhaCorPeca p: pecas)
            {
                p.setStroke(Color.TRANSPARENT);
                p.setStrokeWidth(0);
                p.setEffect(null);
                p.selecionada = false;
            }
        }
        else
        {
            for (EscolhaCorPeca p: pecas)
            {
                p.setFill(Color.DARKGRAY);
                p.setStroke(Color.TRANSPARENT);
                p.setStrokeWidth(0);
                p.setEffect(null);
                p.selecionada = false;
            }
        }
    }

    class EscolhaCorPeca extends Circle implements Serializable {

        boolean selecionada;
        boolean esc_Coluna;
        private int id;

        public EscolhaCorPeca(Color cor, boolean esc_Coluna) {
            selecionada = false;

            setFill(cor);
            this.setStroke(Color.TRANSPARENT);

            if(!esc_Coluna) {
                setOnMouseClicked(ev -> updateMyBorder());
                setRadius(20);
            }
            else {
                this.esc_Coluna = esc_Coluna;
                this.id = nextId++;
                setRadius(15);

                setOnMouseClicked(ev -> {
                    if(this.getFill() == Color.RED)
                        jogo.usarPecaEspecial(this.id);
                    else
                        jogo.jogar(this.id);

                }

                );
                setOnMouseEntered(ev->{
                    updateMyBorder();
                });
            }
        }

        private void updateMyBorder() {
            if (!selecionada) {
                this.setStroke(Color.GRAY);
                this.setStrokeWidth(2);
                javafx.scene.effect.InnerShadow effect = new javafx.scene.effect.InnerShadow();
                effect.setOffsetX(5);
                effect.setOffsetY(5);
                effect.setRadius(10);
                effect.setColor(Color.rgb(0, 0, 0, 0.6));

                this.setEffect(effect);

                for (EscolhaCorPeca c : pecas) {
                    if (!c.equals(this)) {
                        c.setStroke(Color.TRANSPARENT);
                        c.setStrokeWidth(0);
                        c.selecionada = false;
                        c.setEffect(null);
                    }
                }

                this.selecionada = true;

            } else {
                this.setStroke(Color.TRANSPARENT);
                this.setStrokeWidth(0);
                this.setEffect(null);
                this.selecionada = false;
            }
        }
    }
}