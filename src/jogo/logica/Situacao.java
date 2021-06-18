package jogo.logica;

import java.io.Serializable;

public enum Situacao implements Serializable {
    AGUARDA_INICIO, AGUARDAR_DEFINIR_MODO_JOGO, AGUARDAR_JOGADA, AGUARDAR_FIM_MINI_JOGO_MAT, AGUARDAR_FIM_MINI_JOGO_PT, AGUARDAR_FIM_REPLAY, AGUARDAR_TERMINAR
}
