package jogo.logica.dados.MiniJogo;

import jogo.logica.dados.DadosJogo;

import java.io.*;
import java.util.*;

public class Palavras extends MiniJogo
{
    String ficheiro = "palavras.txt";

    HashMap<Integer,String> palavras;
    HashMap<Integer,String> palavrasEnviar;
    HashMap<Integer,String> respostas;

    int nPerguntas;
    int nRespostas;
    int tempo;

    public Palavras(DadosJogo dadosJogo, int nPerguntas)
    {
        super(dadosJogo, false);

        palavras = new HashMap<>();
        palavrasEnviar = new HashMap<>();
        respostas = new HashMap<>();
        this.nPerguntas = nPerguntas;
        this.nRespostas = 0;
        tempo = 0;

        Iniciar();
    }

    private void Iniciar()
    {
        try {
            carregaFicheiro();
        }catch (IOException ex)
        {
            dadosJogo.addMsgLog(" " + ex);
        }

    }

    private void calculaTempo()
    {
        int contador = 0;
        if(palavrasEnviar == null)
            return;

        for(int i=0;i<palavrasEnviar.size();i++)
            contador += palavrasEnviar.get(i).length();

        tempo = contador/2;
    }

    @Override
    public void gerarInputJogo()
    {
        int tamanho = palavras.size();
        int idPalavraEnviar;

        if(tamanho <= 0)
            return;

        for(int i=0; i < 5; i++)
        {
            idPalavraEnviar = (int)((Math.random()* tamanho) + 1);
            palavrasEnviar.put(i, palavras.get(idPalavraEnviar));
        }

        calculaTempo();
    }

    private void carregaFicheiro() throws IOException
    {
        BufferedReader in = null;

        try
        {
            BufferedReader buffRead = new BufferedReader(new FileReader(ficheiro));
            String linha = "";
            int i = 1;

            while (true) {
                linha = buffRead.readLine();
                if (linha != null)
                    palavras.put(i,linha.trim());
                else
                    break;
                i++;
            }
            buffRead.close();

        }catch (IOException ex)
        {
            dadosJogo.addMsgLog("Erro a carregar palavras de ficheiro! " + ex);
        }
        finally{
            if(in != null) in.close();
        }
    }

    @Override
    public void insereResposta(String resultado)
    {
        String[] palavrasSeparadas = resultado.split(" ");

        if(palavrasSeparadas != null)
        {
            for (int i=0;i<palavrasSeparadas.length; i++) {
                respostas.put(nRespostas, palavrasSeparadas[i]);
                nRespostas++;
            }
        }

        setFinalTemporizador();
    }

    @Override
    public int getnRespostas() { return respostas.size();}

    @Override
    public int getnPerguntas() { return nPerguntas;}

    public boolean getGanhou()
    {
        for (int i=0;i<palavrasEnviar.size();i++)
        {
            if(!palavrasEnviar.get(i).equals(respostas.get(i)))
                return false;
        }

        if(tempoPassado <= tempo)
            return resultado = true;

        return resultado = false;
    }

    @Override
    public int getTempo() {return tempo;}

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<palavrasEnviar.size();i++)
        {
            sb.append(palavrasEnviar.get(i) + " ");
        }

        return sb.toString();
    }
}
