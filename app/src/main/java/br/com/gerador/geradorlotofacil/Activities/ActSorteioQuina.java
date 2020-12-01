package br.com.gerador.geradorlotofacil.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import br.com.gerador.geradorlotofacil.Model.NetworkUtils;
import br.com.gerador.geradorlotofacil.Model.Utils;
import br.com.gerador.geradorlotofacil.R;

public class ActSorteioQuina extends AppCompatActivity {

    // Constantes
    private final String LOTERIA = "quina";
    private final String URL = Utils.getUrl(LOTERIA);

    /* Toolbar */
    private Toolbar toolbarPrincipal;

    /* TextView */
    private TextView textCabecalho;
    private TextView textNumero1,textNumero2, textNumero3, textNumero4, textNumero5;
    private TextView txtNumeroSorteado1, txtNumeroSorteado2, txtNumeroSorteado3, txtNumeroSorteado4, txtNumeroSorteado5;

    /* Button */
    private Button buttonGerarAposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sorteio_quina);

        /* Toolbar */
        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        toolbarPrincipal.setTitle(getResources().getString(R.string.menu_quina));
        setSupportActionBar(toolbarPrincipal);

        /* TextView */
        textCabecalho = findViewById(R.id.textCabecalho);
        textNumero1 = findViewById(R.id.textNumero1);
        textNumero2 = findViewById(R.id.textNumero2);
        textNumero3 = findViewById(R.id.textNumero3);
        textNumero4 = findViewById(R.id.textNumero4);
        textNumero5 = findViewById(R.id.textNumero5);
        txtNumeroSorteado1 = findViewById(R.id.txtNumeroSorteado1);
        txtNumeroSorteado2 = findViewById(R.id.txtNumeroSorteado2);
        txtNumeroSorteado3 = findViewById(R.id.txtNumeroSorteado3);
        txtNumeroSorteado4 = findViewById(R.id.txtNumeroSorteado4);
        txtNumeroSorteado5 = findViewById(R.id.txtNumeroSorteado5);

        /* Button */
        buttonGerarAposta = findViewById(R.id.buttonGerarAposta);
        buttonGerarAposta.setOnClickListener(gerarApostaListener);

        /* Chama o serviço das Loterias */
        AsyncTask resultadoLoterias = new Loterias(URL, this).execute();
    }

    /**
     * Método ouvinte para gerar a aposta
     */
    View.OnClickListener gerarApostaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] nSorteados = new int[5];
            Random aleatorio = new Random();
            int numero;
            ArrayList<String> numeros = numeros();

            // while para excluir os números
            while (numeros.size() != 5) {
                // Realiza o sorteio inverso
                numero = aleatorio.nextInt(numeros.size());
                numeros.remove(numero);
            }

            // for para criar a string
            for (int i = 0; i < numeros.size(); i++) {
                // Atribui o número sorteado ao vetor
                nSorteados[i] = Integer.parseInt(numeros.get(i));
            }

            // Chama o método responsável por atribuir os números
            atribuirNumeros(nSorteados);
        }
    };

    public void atribuirNumeros(int[] numerosSorteados){
        txtNumeroSorteado1.setText(String.format("%02d", numerosSorteados[0]));
        txtNumeroSorteado2.setText(String.format("%02d", numerosSorteados[1]));
        txtNumeroSorteado3.setText(String.format("%02d", numerosSorteados[2]));
        txtNumeroSorteado4.setText(String.format("%02d", numerosSorteados[3]));
        txtNumeroSorteado5.setText(String.format("%02d", numerosSorteados[4]));
    }

    /**
     * Método responsável por gravar o cache para consultas offline
     * @param - número do concurso
     * @param - data do concurso
     * @param dezenas - Array com as dezenas separadas por ";"
     */
    public void gravaCache(String concurso, String data, String dezenas[]){
        SharedPreferences prefs = getSharedPreferences("dezenas_quina", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("concurso", concurso);
        editor.putString("data", data);
        editor.putString("00", dezenas[0]);
        editor.putString("01", dezenas[1]);
        editor.putString("02", dezenas[2]);
        editor.putString("03", dezenas[3]);
        editor.putString("04", dezenas[4]);
        editor.commit();
    }

    /**
     * Método responsável por retornar os números de 1 a 25
     *
     * @return - Retorna um ArrayList com os números
     */
    public static ArrayList<String> numeros() {
        ArrayList<String> numeros = new ArrayList<>();

        for(int i = 1; i <= 80; i++){
            if(i < 10){
                numeros.add("0"+i);
            }else{
                numeros.add(""+i);
            }
        }

        return numeros;
    }

    /***************************************************** REQUISIÇÕES **/

    public class Loterias extends AsyncTask<Void, Void, String> {
        String url;
        ProgressDialog progresso;

        // Construtor
        public Loterias(String url, ActSorteioQuina activity) {
            this.url = url;
            progresso = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            progresso.setTitle(getResources().getString(R.string.texto_carregamento));
            progresso.setMessage(getResources().getString(R.string.texto_aguarde));
            progresso.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtils.pegaDados(url);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equalsIgnoreCase("") || s.contains("expirado")) {
                SharedPreferences prefs = getSharedPreferences("dezenas_quina", 0);
                // Gera a string com as dezenas separadas por "|"
                String array = "[" + prefs.getString("00", "") + "," + prefs.getString("01", "") + "," +
                        prefs.getString("02", "") + "," + prefs.getString("03", "") + "," + prefs.getString("04", "") + "]";

                // Chama o método responsável por mostrar o resultado e mostrar o cabeçalho
                preencheResultado(prefs.getString("concurso", ""), prefs.getString("data", ""), array);

                Toast.makeText(ActSorteioQuina.this, getResources().getString(R.string.texto_semconexao), Toast.LENGTH_SHORT).show();

            } else {
                // Chama o método responsável por tratar o XML
                parseJson(s);
            }

            progresso.dismiss();
        }

        /**
         * Método responsável por converter o xml
         * @param json - json retornado da webservice
         */
        public void parseJson(String json){
            try {

                // Retira o primeiro e o último caracter do json
                json = json.substring(1, json.length() - 1);
                JSONObject obj = new JSONObject(json);

                String concurso = obj.getString("Concurso");
                concurso = concurso.substring(concurso.length() - 4, concurso.length());
                String data = obj.getString("DataConcurso").replace("(", "").replace(")", "");
                String dezenas = obj.getString("Numeros");
                String valor = obj.getString("ValorAcumulado");

                // Chama o método responsável por preencher os resultados e mostrar o cabeçalho
                preencheResultado(concurso, data, dezenas);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * Método responsável por criar o array e preencher os resultados
         * @param concurso - Número do concurso
         * @param dezenas - Data do concurso
         * @param dezenas - String com as dezenas separas por "|"
         */
        public void preencheResultado(String concurso, String data, String dezenas){
            SharedPreferences prefs = getSharedPreferences("dezenas_quina", 0);
            String numeros = dezenas.replace("[", "").replace("]", "").replace(",", ";");
            String array[] = numeros.split(";");

            // Verifica se o concurso buscado é o mesmo do gravado em cache
            if(!concurso.equalsIgnoreCase(prefs.getString("concurso", ""))){
                gravaCache(concurso, data, array);
            }

            // Preenche o cabeçalho
            textCabecalho.setText(getResources().getString(R.string.texto_cabecalho_quina) + " " + concurso + " (" + data + ")");

            // Preenche os números
            textNumero1.setText(String.format("%02d", Integer.parseInt(array[0].replace("\"", ""))));
            textNumero2.setText(String.format("%02d", Integer.parseInt(array[1].replace("\"", ""))));
            textNumero3.setText(String.format("%02d", Integer.parseInt(array[2].replace("\"", ""))));
            textNumero4.setText(String.format("%02d", Integer.parseInt(array[3].replace("\"", ""))));
            textNumero5.setText(String.format("%02d", Integer.parseInt(array[4].replace("\"", ""))));
        }
    }

    // Método responsável por sobrescrever o crick do botão voltar
    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirma = new AlertDialog.Builder(this);
        confirma.setTitle(getResources().getString(R.string.texto_sair_da_aplicacao));
        confirma.setMessage(getResources().getString(R.string.texto_sair_da_aplicacao_certeza));
        confirma.setNegativeButton(getResources().getString(R.string.texto_nao), null);
        confirma.setPositiveButton(getResources().getString(R.string.texto_sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        confirma.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_lotofacil:
                startActivity(new Intent(this, ActSorteio.class));
                finish();
                break;
            case R.id.menu_megasena:
                startActivity(new Intent(this, ActSorteioMegaSena.class));
                finish();
                break;
            case  R.id.menu_quina:
                startActivity(new Intent(this, ActSorteioQuina.class));
                finish();
                break;
            case  R.id.menu_duplasenha:
                startActivity(new Intent(this, ActSorteioDuplaSena.class));
                finish();
                break;
            case  R.id.menu_lotomania:
                startActivity(new Intent(this, ActSorteioLotomania.class));
                finish();
                break;
            case  R.id.menu_timemania:
                startActivity(new Intent(this, ActSorteioTimeMania.class));
                finish();
                break;
            case  R.id.menu_diadesorte:
                startActivity(new Intent(this, ActSorteioDiaDeSorte.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}