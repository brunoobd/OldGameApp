package br.senai.sp.cotia.oldgameapp.fragment;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.oldgameapp.R;
import br.senai.sp.cotia.oldgameapp.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {

    // váriavel para acessar os elementos da View
    private FragmentJogoBinding biding;

    // vetor de botoes para referenciar os botoes
    private Button[] botoes;
    // private ImageButton[] botoes;
    // matriz de string que representa o tabuleiro
    private String[][] tabuleiro;
    // variaveis para os simbolos
    private String simbJog1, simbJog2, simbolo;
    // private int simbJog1, simbJog2, simbolo;
    // variavel random para sortear quem inicia
    Random random;

    private int numJogadas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instanciar o binding
        biding = FragmentJogoBinding.inflate(inflater, container, false);

        //instanciar o vetor dos botoes
        botoes = new Button[9];
        // associar o vetor aos botoes
        botoes[0] = biding.bt00;
        botoes[1] = biding.bt01;
        botoes[2] = biding.bt02;
        botoes[3] = biding.bt10;
        botoes[4] = biding.bt11;
        botoes[5] = biding.bt12;
        botoes[6] = biding.bt20;
        botoes[7] = biding.bt21;
        botoes[8] = biding.bt22;

        // associa o listener aos botoes
        for (Button bt: botoes) {
            bt.setOnClickListener(listenerBotoes);
        }

        // instanciando o tabuleiro
        tabuleiro = new String[3][3];

//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                tabuleiro[i][j] = "";
//            }
//        }

        for (String[] vetor: tabuleiro) {
            Arrays.fill(vetor, "");
        }

        // define os simbolos do jogador1 e do jogador2
        simbJog1 = "X";
        simbJog2 = "O";

        // instancia o random
        random = new Random();

        // sorteia quem iniciara o jogo
        sorteia();

        // método que troca a vez
        atualizaVez();

        // retorna a view root do binding
        return biding.getRoot();
    }
    // metodo que define quem começa jogando
    public void sorteia() {
        // se gerar um valor VERDADEIRO, jogador1 começa,
        // caso contrario jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;
        } else {
            simbolo = simbJog2;
        }
    }

    // metodo que troca a vez
    public void atualizaVez() {
        if (simbolo.equals(simbJog1)) {
            biding.trJg1.setBackgroundColor(getResources().getColor(R.color.secundaria));
            biding.trJg2.setBackgroundColor(getResources().getColor(R.color.principal));
        } else {
            biding.trJg2.setBackgroundColor(getResources().getColor(R.color.secundaria));
            biding.trJg1.setBackgroundColor(getResources().getColor(R.color.principal));
        }
    }


    // listener para os botoes
    private View.OnClickListener listenerBotoes = btPress -> {
        // obtém o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        numJogadas++;
        // extrai a posição através do nome do botão
        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        // extrai linha e coluna da string posicao
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        // preencher a posição da matriz com o simbolo da vez
        tabuleiro[linha][coluna] = simbolo;

        // faz um casting de View para Button
        Button botao = (Button) btPress;
        // "seta" o simbolo no botao pressionado
        botao.setText(simbolo);
        // trocar o background do botao
        // botao.setBackgroundColor(Color.WHITE);

        // desabilitar o botao que foi clicado
        botao.setClickable(false);

        // verifica se venceu
        if (numJogadas >= 5 && venceu()) {
            // informa que houve um vencedor
            Toast.makeText(getContext(), R.string.hp, Toast.LENGTH_LONG).show();
            // resta a partida
            resetaJogo();
        } else  if (numJogadas == 9) {
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            resetaJogo();
        } else {
            // inverte o símbolo

            // com if
            // if (simbolo.equals(simbJog1)) {
            //    simbolo = simbJog2;
            // } else {
            //    simbolo = simbJog1;
            // }

            // com operador ternario
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;

            atualizaVez();
        }

        Log.w("BOTAO", linha+"");
        Log.w("BOTAO", coluna+"");
    };

    private boolean venceu() {
        for (int l = 0; l < 3; l++){
            if (tabuleiro[l][0].equals(simbolo) && tabuleiro[l][1].equals(simbolo) && tabuleiro[l][2].equals(simbolo)) {
                return true;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (tabuleiro[0][c].equals(simbolo) && tabuleiro[1][c].equals(simbolo) && tabuleiro[2][c].equals(simbolo)) {
                return true;
            }
        }

        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }

        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;
    }

    private void resetaJogo() {
        for (String[] vetor: tabuleiro) {
            Arrays.fill(vetor, "");
        }


        // transformando os botoes normais
        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setClickable(true);
            botoes[i].setText("");
        }

        // zerando o numero de jogadas
        numJogadas = 0;

        sorteia();

    }
}