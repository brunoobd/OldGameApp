package br.senai.sp.cotia.oldgameapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.oldgameapp.R;
import br.senai.sp.cotia.oldgameapp.databinding.FragmentJogoBinding;
import br.senai.sp.cotia.oldgameapp.util.PrefsUtil;

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

    // variaveis para o placar
    private int placarJog1 = 0, placarJog2 = 0, placarVelha = 0;

    // Alert Dialog
    private AlertDialog alert;

    private String jogadas;
    private int jogadasInt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // habilitar o menu
        setHasOptionsMenu(true);

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
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        // atualiza o número de jogadas
        jogadas = PrefsUtil.getJogadas(getContext());
        jogadasInt = Integer.parseInt(jogadas);

        Log.w("EMOJI", Character.isLetter(simbJog1.charAt(0))+"");
        Log.w("EMOJI", Character.isDigit(simbJog1.charAt(1))+"");


        // pega o simbolo do jogador1 e jogador2 e extrai apenas o primeiro caracter

        // se for nao for vazio ele pega o caracter, caso contrario ele nao faz nada
        if (!simbJog1.isEmpty()) {
            // ve se é maior que 1 caracter
            if (simbJog1.length() > 1) {
                // ve se não é emoji
                if (Character.isLetter(simbJog1.charAt(0)) || Character.isDigit(simbJog1.charAt(0))) {
                    simbJog1 = simbJog1.substring(0, 1);
                }
            }
        } else {
            simbJog1 = "X";
        }

        if (!simbJog2.isEmpty()) {
            if (simbJog2.length() > 1) {
                if (Character.isLetter(simbJog2.charAt(0)) || Character.isDigit(simbJog2.charAt(0))) {
                    simbJog2 = simbJog2.substring(0, 1);
                }
            }
        } else {
            simbJog2 = "O";
        }

        // atualiza o placar com os simbolos
        // biding.tvJog1.setText(getResources().getString(R.string.jog1, simbJog1));
        // biding.tvJog2.setText(getResources().getString(R.string.jog2, simbJog2));

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

    // método que atualizar o placar dos jogadores...
    private void atualizaPlacar() {
        biding.tvPlacar1.setText(placarJog1+"");
        biding.tvPlacar2.setText(placarJog2+"");
        biding.tvPlacarVelha.setText(placarVelha+"");
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // ALERTA
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Resetar");
        builder.setMessage("Deseja mesmo resetar o jogo?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                placarJog1 = 0;
                placarJog2 = 0;
                placarVelha = 0;
                atualizaPlacar();
                resetaJogo();
                Toast.makeText(getContext(), "O jogo foi resetado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "O jogo não foi resetado", Toast.LENGTH_SHORT).show();
            }
        });
        // verifica qual item do menu foi selecionado
        switch (item.getItemId()) {
            // caso seja a opção de resetar
            case R.id.menu_resetar:
                alert = builder.create();
                alert.show();
                break;
            case R.id.menu_prefs:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
            case R.id.menu_inicio:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_inicioFragment);
                break;
        }
        return true;
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
            // verifica quem venceu
            if(simbolo.equals(simbJog1)) {
                placarJog1++;
                // informa que o jogador 1 venceu
                Toast.makeText(getContext(), R.string.hp1, Toast.LENGTH_LONG).show();
            } else {
                placarJog2++;
                // informa que o jogador 2 venceu
                Toast.makeText(getContext(), R.string.hp2, Toast.LENGTH_LONG).show();
            }
            // atualiza o placar
            atualizaPlacar();
            // resta a partida
            resetaJogo();
        } else  if (numJogadas == 9) {
            placarVelha++;
            atualizaPlacar();
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
        int totalJogadas = (jogadasInt / 2) + 1;
        if (totalJogadas == placarJog1){
            Toast.makeText(getContext(), "Parabens! voce ganhou a partida jogador 1.", Toast.LENGTH_LONG).show();
            placarJog1 = 0;
            placarJog2 = 0;
            placarVelha = 0;
            atualizaPlacar();
            resetaJogo();
        }else if (totalJogadas == placarJog2){
            Toast.makeText(getContext(), "Parabens! voce ganhou a partida jogador 2.", Toast.LENGTH_LONG).show();
            placarJog1 = 0;
            placarJog2 = 0;
            placarVelha = 0;
            atualizaPlacar();
            resetaJogo();
        }
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

        atualizaVez();
    }

    @Override
    public void onStart() {
        super.onStart();
        // pega a referencia para a activity
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().show();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


}