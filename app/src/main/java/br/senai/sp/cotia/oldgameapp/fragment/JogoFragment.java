package br.senai.sp.cotia.oldgameapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senai.sp.cotia.oldgameapp.R;
import br.senai.sp.cotia.oldgameapp.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {

    // váriavel para acessar os elementos da View
    private FragmentJogoBinding biding;

    // vetor de botoes para referenciar os botoes
    private Button[] botoes;

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

        // retorna a view root do binding
        return biding.getRoot();
    }

    // listener para os botoes
    private View.OnClickListener listenerBotoes = btPress -> {
        // obtém o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        // extrai a posição através do nome do botão
        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        // extrai linha e coluna da string posicao
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        Log.w("BOTAO", linha+"");
        Log.w("BOTAO", coluna+"");
    };
}