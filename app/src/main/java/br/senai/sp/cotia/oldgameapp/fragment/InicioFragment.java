package br.senai.sp.cotia.oldgameapp.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.senai.sp.cotia.oldgameapp.R;
import br.senai.sp.cotia.oldgameapp.databinding.FragmentInicioBinding;
import br.senai.sp.cotia.oldgameapp.databinding.FragmentJogoBinding;

public class InicioFragment extends Fragment {
    // variavel para acessar os componentes da view
    private FragmentInicioBinding binding;
    // variavel do press start
    private Button start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);

        binding.start.setOnClickListener(view -> {
            NavHostFragment.findNavController(InicioFragment.this).navigate(R.id.action_inicioFragment_to_jogoFragment);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        // pega a referencia para a activity
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().hide();
    }
}