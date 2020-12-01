package br.com.gerador.geradorlotofacil.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import br.com.gerador.geradorlotofacil.Model.NetworkUtils;
import br.com.gerador.geradorlotofacil.Model.Utils;
import br.com.gerador.geradorlotofacil.R;

public class ActSorteio extends AppCompatActivity {

    /* Constantes --*/
    private final String LOTERIA = "lotofacil";
    private String url;

    /* Toolbar */
    private Toolbar toolbarPrincipal;

    /* TextView */
    private TextView textCabecalho;
    private TextView textNumero1,textNumero2, textNumero3, textNumero4, textNumero5, textNumero6, textNumero7, textNumero8,
            textNumero9, textNumero10, textNumero11, textNumero12, textNumero13, textNumero14, textNumero15;
    private TextView txtNumeroSorteado1, txtNumeroSorteado2, txtNumeroSorteado3, txtNumeroSorteado4, txtNumeroSorteado5,
            txtNumeroSorteado6, txtNumeroSorteado7, txtNumeroSorteado8, txtNumeroSorteado9, txtNumeroSorteado10, txtNumeroSorteado11,
            txtNumeroSorteado12, txtNumeroSorteado13, txtNumeroSorteado14, txtNumeroSorteado15;

    /* Button */
    private Button buttonGerarAposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sorteio);

        /* Toolbar */
        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbarPrincipal);
        toolbarPrincipal.setTitle(getResources().getString(R.string.menu_lotofacil));
        setSupportActionBar(toolbarPrincipal);

        /* TextView */
        textCabecalho = findViewById(R.id.textCabecalho);
        textNumero1 = findViewById(R.id.textNumero1);
        textNumero2 = findViewById(R.id.textNumero2);
        textNumero3 = findViewById(R.id.textNumero3);
        textNumero4 = findViewById(R.id.textNumero4);
        textNumero5 = findViewById(R.id.textNumero5);
        textNumero6 = findViewById(R.id.textNumero6);
        textNumero7 = findViewById(R.id.textNumero7);
        textNumero8 = findViewById(R.id.textNumero8);
        textNumero9 = findViewById(R.id.textNumero9);
        textNumero10 = findViewById(R.id.textNumero10);
        textNumero11 = findViewById(R.id.textNumero11);
        textNumero12 = findViewById(R.id.textNumero12);
        textNumero13 = findViewById(R.id.textNumero13);
        textNumero14 = findViewById(R.id.textNumero14);
        textNumero15 = findViewById(R.id.textNumero15);
        txtNumeroSorteado1 = findViewById(R.id.txtNumeroSorteado1);
        txtNumeroSorteado2 = findViewById(R.id.txtNumeroSorteado2);
        txtNumeroSorteado3 = findViewById(R.id.txtNumeroSorteado3);
        txtNumeroSorteado4 = findViewById(R.id.txtNumeroSorteado4);
        txtNumeroSorteado5 = findViewById(R.id.txtNumeroSorteado5);
        txtNumeroSorteado6 = findViewById(R.id.txtNumeroSorteado6);
        txtNumeroSorteado7 = findViewById(R.id.txtNumeroSorteado7);
        txtNumeroSorteado8 = findViewById(R.id.txtNumeroSorteado8);
        txtNumeroSorteado9 = findViewById(R.id.txtNumeroSorteado9);
        txtNumeroSorteado10 = findViewById(R.id.txtNumeroSorteado10);
        txtNumeroSorteado11 = findViewById(R.id.txtNumeroSorteado11);
        txtNumeroSorteado12 = findViewById(R.id.txtNumeroSorteado12);
        txtNumeroSorteado13 = findViewById(R.id.txtNumeroSorteado13);
        txtNumeroSorteado14 = findViewById(R.id.txtNumeroSorteado14);
        txtNumeroSorteado15 = findViewById(R.id.txtNumeroSorteado15);

        /* Button */
        buttonGerarAposta = findViewById(R.id.buttonGerarAposta);
        buttonGerarAposta.setOnClickListener(gerarApostaListener);

        // Monta a url
        url = Utils.getUrl(LOTERIA);

        /* Chama o serviço das Loterias */
        AsyncTask resultadoLoterias = new Loterias(url, this).execute();

    }

    /**
     * Método ouvinte para gerar a aposta
     */
    View.OnClickListener gerarApostaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] nSorteados = new int[15];
            Random aleatorio = new Random();
            int numero;
            ArrayList<String> numeros = numeros();

            // while para excluir os números
            while (numeros.size() != 15) {
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
        txtNumeroSorteado6.setText(String.format("%02d", numerosSorteados[5]));
        txtNumeroSorteado7.setText(String.format("%02d", numerosSorteados[6]));
        txtNumeroSorteado8.setText(String.format("%02d", numerosSorteados[7]));
        txtNumeroSorteado9.setText(String.format("%02d", numerosSorteados[8]));
        txtNumeroSorteado10.setText(String.format("%02d", numerosSorteados[9]));
        txtNumeroSorteado11.setText(String.format("%02d", numerosSorteados[10]));
        txtNumeroSorteado12.setText(String.format("%02d", numerosSorteados[11]));
        txtNumeroSorteado13.setText(String.format("%02d", numerosSorteados[12]));
        txtNumeroSorteado14.setText(String.format("%02d", numerosSorteados[13]));
        txtNumeroSorteado15.setText(String.format("%02d", numerosSorteados[14]));
    }

    /**
     * Método responsável por gravar o cache para consultas offline
     * @param - número do concurso
     * @param - data do concurso
     * @param dezenas - Array com as dezenas separadas por ";"
     */
    public void gravaCache(String concurso, String data, String dezenas[]){
        SharedPreferences prefs = getSharedPreferences("dezenas", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("concurso", concurso);
        editor.putString("data", data);
        editor.putString("00", dezenas[0]);
        editor.putString("01", dezenas[1]);
        editor.putString("02", dezenas[2]);
        editor.putString("03", dezenas[3]);
        editor.putString("04", dezenas[4]);
        editor.putString("05", dezenas[5]);
        editor.putString("06", dezenas[6]);
        editor.putString("07", dezenas[7]);
        editor.putString("08", dezenas[8]);
        editor.putString("09", dezenas[9]);
        editor.putString("10", dezenas[10]);
        editor.putString("11", dezenas[11]);
        editor.putString("12", dezenas[12]);
        editor.putString("13", dezenas[13]);
        editor.putString("14", dezenas[14]);
        editor.commit();
    }

    /**
     * Método responsável por retornar os números de 1 a 25
     *
     * @return - Retorna um ArrayList com os números
     */
    public static ArrayList<String> numeros() {
        ArrayList<String> numeros = new ArrayList<>();

        for(int i = 1; i <= 25; i++){
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
        public Loterias(String url, ActSorteio activity) {
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
                SharedPreferences prefs = getSharedPreferences("dezenas", 0);
                // Gera a string com as dezenas separadas por "|"
                String array = "[" + prefs.getString("00", "") + "," + prefs.getString("01", "") + "," +
                        prefs.getString("02", "") + "," + prefs.getString("03", "") + "," + prefs.getString("04", "") + "," +
                        prefs.getString("05", "") + "," + prefs.getString("06", "") + "," + prefs.getString("07", "") + "," +
                        prefs.getString("08", "") + "," + prefs.getString("09", "") + "," + prefs.getString("10", "") + "," +
                        prefs.getString("11", "") + "," + prefs.getString("12", "") + "," + prefs.getString("13", "") + "," +
                        prefs.getString("14", "") + "]";

                // Chama o método responsável por mostrar o resultado e mostrar o cabeçalho
                preencheResultado(prefs.getString("concurso", ""), prefs.getString("data", ""), array);

                Toast.makeText(ActSorteio.this, getResources().getString(R.string.texto_semconexao), Toast.LENGTH_SHORT).show();

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
            SharedPreferences prefs = getSharedPreferences("dezenas", 0);
            String numeros = dezenas.replace("[", "").replace("]", "").replace(",", ";");
            String array[] = numeros.split(";");

            // Verifica se o concurso buscado é o mesmo do gravado em cache
            if(!concurso.equalsIgnoreCase(prefs.getString("concurso", ""))){
                gravaCache(concurso, data, array);
            }

            // Preenche o cabeçalho
            textCabecalho.setText(getResources().getString(R.string.texto_cabecalho_lotofacil) + " " + concurso + " (" + data + ")");

            // Preenche os números
            textNumero1.setText(String.format("%02d", Integer.parseInt(array[0].replace("\"", ""))));
            textNumero2.setText(String.format("%02d", Integer.parseInt(array[1].replace("\"", ""))));
            textNumero3.setText(String.format("%02d", Integer.parseInt(array[2].replace("\"", ""))));
            textNumero4.setText(String.format("%02d", Integer.parseInt(array[3].replace("\"", ""))));
            textNumero5.setText(String.format("%02d", Integer.parseInt(array[4].replace("\"", ""))));
            textNumero6.setText(String.format("%02d", Integer.parseInt(array[5].replace("\"", ""))));
            textNumero7.setText(String.format("%02d", Integer.parseInt(array[6].replace("\"", ""))));
            textNumero8.setText(String.format("%02d", Integer.parseInt(array[7].replace("\"", ""))));
            textNumero9.setText(String.format("%02d", Integer.parseInt(array[8].replace("\"", ""))));
            textNumero10.setText(String.format("%02d", Integer.parseInt(array[9].replace("\"", ""))));
            textNumero11.setText(String.format("%02d", Integer.parseInt(array[10].replace("\"", ""))));
            textNumero12.setText(String.format("%02d", Integer.parseInt(array[11].replace("\"", ""))));
            textNumero13.setText(String.format("%02d", Integer.parseInt(array[12].replace("\"", ""))));
            textNumero14.setText(String.format("%02d", Integer.parseInt(array[13].replace("\"", ""))));
            textNumero15.setText(String.format("%02d", Integer.parseInt(array[14].replace("\"", ""))));
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