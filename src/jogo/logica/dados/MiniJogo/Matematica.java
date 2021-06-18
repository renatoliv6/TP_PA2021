package jogo.logica.dados.MiniJogo;

import jogo.logica.dados.DadosJogo;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Matematica extends MiniJogo
{
    List<Character> operacoes;
    HashMap<Integer,String> calculos;
    HashMap<Integer,Double> resultados;

    HashMap<Integer,Double> respostas;
    int calculoAtual;
    int nPerguntas;
    int nRespostas;
    int tempo;

    public Matematica(DadosJogo dadosJogo, int nPerguntas)
    {
        super(dadosJogo, false);
        calculos = new HashMap<>();
        resultados = new HashMap<>();
        respostas = new HashMap<>();
        this.nPerguntas = nPerguntas;

        operacoes = new ArrayList<>();
        operacoes.add('+');
        operacoes.add('-');
        operacoes.add('*');
        operacoes.add('/');

        calculoAtual = 0;
        tempo = 30;

    }

    @Override
    public void insereResposta(String resultado)
    {
        Double resposta = null;

        try
        {
            resposta = Double.parseDouble(resultado);
        }catch (NumberFormatException n)
        {
            resposta = Double.parseDouble("0");
        }

        respostas.put(nRespostas,resposta);
        nRespostas++;
    }

    @Override
    public void gerarInputJogo()
    {
        StringBuilder s = new StringBuilder();
        Character opAtual;
        int numero1, numero2;
        double resultado = 0;

        numero1 = (int)((Math.random() *99));
        numero2 = (int)((Math.random() *99) + 1);
        Collections.shuffle(operacoes);
        opAtual = operacoes.get(0);

        switch (opAtual)
        {
            case '+':
                resultado = numero1 + numero2;
                break;
            case '-':
                resultado = numero1 - numero2;
                break;
            case '*':
                resultado = numero1 * numero2;
                break;
            case '/':
                resultado = numero1 / numero2;
                break;
        }

        s.append(numero1 + " " + opAtual + " " + numero2 + " = ");
        calculos.put(calculoAtual, s.toString());
        resultados.put(calculoAtual,resultado);

        calculoAtual++;
    }

    @Override
    public int getnRespostas() { return respostas.size();}

    @Override
    public int getnPerguntas() { return nPerguntas;}

    public boolean getGanhou()
    {
        int contador = 0;
        for (int i=0;i<calculos.size();i++)
        {
            if(resultados.get(i).equals(respostas.get(i)))
                contador++;
        }

        if(contador >= 5)
            return resultado = true;

        return resultado = false;
    }

    @Override
    public int getTempo() { return tempo;};

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(calculos.get(calculoAtual-1));
        return sb.toString();
    }

}
