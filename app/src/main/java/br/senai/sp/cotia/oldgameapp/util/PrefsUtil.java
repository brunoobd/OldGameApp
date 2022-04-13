package br.senai.sp.cotia.oldgameapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsUtil {
    public static String getSimboloJog1(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        /*FAZER IMPLEMENTAÇÂO PARA LIMITAR A QUANTIDADE DE CARACTERES USADOS NA PREFERENCIAS*/
        /*USANDO O COMANDO PARA PEGAR QUANTOS CARACTERES TEM E SUBSTITUIR APENAS PARA O PRIMEIRO, VER QUESTÂO DO EMOJI!!!!*/
        return preferences.getString("simb_jog_1", "X");
    }
    public static String getSimboloJog2(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("simb_jog_2", "O");
    }

    public static void setSimboloJog1 (String simbolo, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("simb_jog_1", simbolo);
        editor.commit();
    }

    public static void setSimboloJog2 (String simbolo, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("simb_jog_2", simbolo);
        editor.commit();
    }

    public static String getJogadas (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("jogadas", "5");
    }

    public static String getTabuleiro (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("tabuleiro", "3x3");
    }
}
