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

public class ActSorteioLotomania extends AppCompatActivity {

    // Constantes
    private final String LOTERIA = "lotomania";
    private final String URL = Utils.getUrl(LOTERIA);

    /* Toolbar */
    private Toolbar toolbarPrincipal;

    /* TextView */
    private TextView textCabecalho;
    private TextView textNumero1,textNumero2, textNumero3, textNumero4, textNumero5, textNumero6, textNumero7, textNumero8,
            textNumero9, textNumero10, textNumero11, textNumero12, textNumero13, textNumero14, textNumero15, textNumero16, textNumero17,
            textNumero18, textNumero19, textNumero20;
    private TextView txtNumeroSorteado1, txtNumeroSorteado2, txtNumeroSorteado3, txtNumeroSorteado4, txtNumeroSorteado5,
            txtNumeroSorteado6, txtNumeroSorteado7, txtNumeroSorteado8, txtNumeroSorteado9, txtNumeroSorteado10, txtNumeroSorteado11,
            txtNumeroSorteado12, txtNumeroSorteado13, txtNumeroSorteado14, txtNumeroSorteado15, txtNumeroSorteado16, txtNumeroSorteado17,
            txtNumeroSorteado18, txtNumeroSorteado19, txtNumeroSorteado20, txtNumeroSorteado21, txtNumeroSorteado22, txtNumeroSorteado23,
            txtNumeroSorteado24, txtNumeroSorteado25, txtNumeroSorteado26, txtNumeroSorteado27, txtNumeroSorteado28, txtNumeroSorteado29,
            txtNumeroSorteado30, txtNumeroSorteado31, txtNumeroSorteado32, txtNumeroSorteado33, txtNumeroSorteado34, txtNumeroSorteado35,
            txtNumeroSorteado36, txtNumeroSorteado37, txtNumeroSorteado38, txtNumeroSorteado39, txtNumeroSorteado40, txtNumeroSorteado41,
            txtNumeroSorteado42, txtNumeroSorteado43, txtNumeroSorteado44, txtNumeroSorteado45, txtNumeroSorteado46, txtNumeroSorteado47,
            txtNumeroSorteado48, txtNumeroSorteado49, txtNumeroSorteado50;

    /* Button */
    private Button buttonGerarAposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sorteio_lotomania);

        /* Toolbar */
        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        toolbarPrincipal.setTitle(getResources().getString(R.string.menu_lotomania));
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
        textNumero16 = findViewById(R.id.textNumero16);
        textNumero17 = findViewById(R.id.textNumero17);
        textNumero18 = findViewById(R.id.textNumero18);
        textNumero19 = findViewById(R.id.textNumero19);
        textNumero20 = findViewById(R.id.textNumero20);
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
        txtNumeroSorteado16 = findViewById(R.id.txtNumeroSorteado16);
        txtNumeroSorteado17 = findViewById(R.id.txtNumeroSorteado17);
        txtNumeroSorteado18 = findViewById(R.id.txtNumeroSorteado18);
        txtNumeroSorteado19 = findViewById(R.id.txtNumeroSorteado19);
        txtNumeroSorteado20 = findViewById(R.id.txtNumeroSorteado20);
        txtNumeroSorteado21 = findViewById(R.id.txtNumeroSorteado21);
        txtNumeroSorteado22 = findViewById(R.id.txtNumeroSorteado22);
        txtNumeroSorteado23 = findViewById(R.id.txtNumeroSorteado23);
        txtNumeroSorteado24 = findViewById(R.id.txtNumeroSorteado24);
        txtNumeroSorteado25 = findViewById(R.id.txtNumeroSorteado25);
        txtNumeroSorteado26 = findViewById(R.id.txtNumeroSorteado26);
        txtNumeroSorteado27 = findViewById(R.id.txtNumeroSorteado27);
        txtNumeroSorteado28 = findViewById(R.id.txtNumeroSorteado28);
        txtNumeroSorteado29 = findViewById(R.id.txtNumeroSorteado29);
        txtNumeroSorteado30 = findViewById(R.id.txtNumeroSorteado30);
        txtNumeroSorteado31 = findViewById(R.id.txtNumeroSorteado31);
        txtNumeroSorteado32 = findViewById(R.id.txtNumeroSorteado32);
        txtNumeroSorteado33 = findViewById(R.id.txtNumeroSorteado33);
        txtNumeroSorteado34 = findViewById(R.id.txtNumeroSorteado34);
        txtNumeroSorteado35 = findViewById(R.id.txtNumeroSorteado35);
        txtNumeroSorteado36 = findViewById(R.id.txtNumeroSorteado36);
        txtNumeroSorteado37 = findViewById(R.id.txtNumeroSorteado37);
        txtNumeroSorteado38 = findViewById(R.id.txtNumeroSorteado38);
        txtNumeroSorteado39 = findViewById(R.id.txtNumeroSorteado39);
        txtNumeroSorteado40 = findViewById(R.id.txtNumeroSorteado40);
        txtNumeroSorteado41 = findViewById(R.id.txtNumeroSorteado41);
        txtNumeroSorteado42 = findViewById(R.id.txtNumeroSorteado42);
        txtNumeroSorteado43 = findViewById(R.id.txtNumeroSorteado43);
        txtNumeroSorteado44 = findViewById(R.id.txtNumeroSorteado44);
        txtNumeroSorteado45 = findViewById(R.id.txtNumeroSorteado45);
        txtNumeroSorteado46 = findViewById(R.id.txtNumeroSorteado46);
        txtNumeroSorteado47 = findViewById(R.id.txtNumeroSorteado47);
        txtNumeroSorteado48 = findViewById(R.id.txtNumeroSorteado48);
        txtNumeroSorteado49 = findViewById(R.id.txtNumeroSorteado49);
        txtNumeroSorteado50 = findViewById(R.id.txtNumeroSorteado50);

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
            int[] nSorteados = new int[50];
            Random aleatorio = new Random();
            int numero;
            ArrayList<String> numeros = numeros();

            // while para excluir os números
            while (numeros.size() != 50) {
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

    // Método responsável por atribuir os valores aos textos
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
        txtNumeroSorteado16.setText(String.format("%02d", numerosSorteados[15]));
        txtNumeroSorteado17.setText(String.format("%02d", numerosSorteados[16]));
        txtNumeroSorteado18.setText(String.format("%02d", numerosSorteados[17]));
        txtNumeroSorteado19.setText(String.format("%02d", numerosSorteados[18]));
        txtNumeroSorteado20.setText(String.format("%02d", numerosSorteados[19]));
        txtNumeroSorteado21.setText(String.format("%02d", numerosSorteados[20]));
        txtNumeroSorteado22.setText(String.format("%02d", numerosSorteados[21]));
        txtNumeroSorteado23.setText(String.format("%02d", numerosSorteados[22]));
        txtNumeroSorteado24.setText(String.format("%02d", numerosSorteados[23]));
        txtNumeroSorteado25.setText(String.format("%02d", numerosSorteados[24]));
        txtNumeroSorteado26.setText(String.format("%02d", numerosSorteados[25]));
        txtNumeroSorteado27.setText(String.format("%02d", numerosSorteados[26]));
        txtNumeroSorteado28.setText(String.format("%02d", numerosSorteados[27]));
        txtNumeroSorteado29.setText(String.format("%02d", numerosSorteados[28]));
        txtNumeroSorteado30.setText(String.format("%02d", numerosSorteados[29]));
        txtNumeroSorteado31.setText(String.format("%02d", numerosSorteados[30]));
        txtNumeroSorteado32.setText(String.format("%02d", numerosSorteados[31]));
        txtNumeroSorteado33.setText(String.format("%02d", numerosSorteados[32]));
        txtNumeroSorteado34.setText(String.format("%02d", numerosSorteados[33]));
        txtNumeroSorteado35.setText(String.format("%02d", numerosSorteados[34]));
        txtNumeroSorteado36.setText(String.format("%02d", numerosSorteados[35]));
        txtNumeroSorteado37.setText(String.format("%02d", numerosSorteados[36]));
        txtNumeroSorteado38.setText(String.format("%02d", numerosSorteados[37]));
        txtNumeroSorteado39.setText(String.format("%02d", numerosSorteados[38]));
        txtNumeroSorteado40.setText(String.format("%02d", numerosSorteados[39]));
        txtNumeroSorteado41.setText(String.format("%02d", numerosSorteados[40]));
        txtNumeroSorteado42.setText(String.format("%02d", numerosSorteados[41]));
        txtNumeroSorteado43.setText(String.format("%02d", numerosSorteados[42]));
        txtNumeroSorteado44.setText(String.format("%02d", numerosSorteados[43]));
        txtNumeroSorteado45.setText(String.format("%02d", numerosSorteados[44]));
        txtNumeroSorteado46.setText(String.format("%02d", numerosSorteados[45]));
        txtNumeroSorteado47.setText(String.format("%02d", numerosSorteados[46]));
        txtNumeroSorteado48.setText(String.format("%02d", numerosSorteados[47]));
        txtNumeroSorteado49.setText(String.format("%02d", numerosSorteados[48]));
        txtNumeroSorteado50.setText(String.format("%02d", numerosSorteados[49]));
    }

    /**
     * Método responsável por gravar o cache para consultas offline
     * @param - número do concurso
     * @param - data do concurso
     * @param dezenas - Array com as dezenas separadas por ";"
     */
    public void gravaCache(String concurso, String data, String dezenas[]){
        SharedPreferences prefs = getSharedPreferences("dezenas_lotomania", 0);
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
        editor.putString("15", dezenas[15]);
        editor.putString("16", dezenas[16]);
        editor.putString("17", dezenas[17]);
        editor.putString("18", dezenas[18]);
        editor.putString("19", dezenas[19]);
        editor.commit();
    }

    /**
     * Método responsável por retornar os números de 1 a 25
     *
     * @return - Retorna um ArrayList com os números
     */
    public static ArrayList<String> numeros() {
        ArrayList<String> numeros = new ArrayList<>();

        for(int i = 0; i < 100; i++){
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
        public Loterias(String url, ActSorteioLotomania activity) {
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
                SharedPreferences prefs = getSharedPreferences("dezenas_lotomania", 0);
                // Gera a string com as dezenas separadas por "|"
                String array = "[" + prefs.getString("00", "") + "," + prefs.getString("01", "") + "," +
                        prefs.getString("02", "") + "," + prefs.getString("03", "") + "," + prefs.getString("04", "") + "," +
                        prefs.getString("05", "") + "," + prefs.getString("06", "") + "," + prefs.getString("07", "") + "," +
                        prefs.getString("08", "") + "," + prefs.getString("09", "") + "," + prefs.getString("10", "") + "," +
                        prefs.getString("11", "") + "," + prefs.getString("12", "") + "," + prefs.getString("13", "") + "," +
                        prefs.getString("14", "") + "," + prefs.getString("15", "") + "," + prefs.getString("16", "") + "," +
                        prefs.getString("17", "") + "," + prefs.getString("18", "") + "," + prefs.getString("19", "") + "]";

                // Chama o método responsável por mostrar o resultado e mostrar o cabeçalho
                preencheResultado(prefs.getString("concurso", ""), prefs.getString("data", ""), array);

                Toast.makeText(ActSorteioLotomania.this, getResources().getString(R.string.texto_semconexao), Toast.LENGTH_SHORT).show();

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
                JSONObject array = new JSONObject(json); // pega todo o resultado
                JSONObject infosLoteria = new JSONObject(array.getString("data")); // pega somente os dados do sorteio
                JSONObject dezenasSorteadas = new JSONObject(infosLoteria.getString("drawing"));

                String concurso = infosLoteria.getString("draw_number");
                String data = infosLoteria.getString("draw_date");
                String dezenas = dezenasSorteadas.getString("draw");

                // Formata a data
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date data2 = sdf.parse(data);
                sdf.applyPattern("dd/MM/yyyy");
                String dataFormatada = sdf.format(data2);

                // Chama o método responsável por preencher os resultados e mostrar o cabeçalho
                preencheResultado(concurso, dataFormatada, dezenas);

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (ParseException e){
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
            SharedPreferences prefs = getSharedPreferences("dezenas_lotomania", 0);
            String numeros = dezenas.replace("[", "").replace("]", "").replace(",", ";");
            String array[] = numeros.split(";");

            // Verifica se o concurso buscado é o mesmo do gravado em cache
            if(!concurso.equalsIgnoreCase(prefs.getString("concurso", ""))){
                gravaCache(concurso, data, array);
            }

            // Preenche o cabeçalho
            textCabecalho.setText(getResources().getString(R.string.texto_cabecalho_lotomania) + " " + concurso + " (" + data + ")");

            // Preenche os números
            textNumero1.setText(String.format("%02d", Integer.parseInt(array[0])));
            textNumero2.setText(String.format("%02d", Integer.parseInt(array[1])));
            textNumero3.setText(String.format("%02d", Integer.parseInt(array[2])));
            textNumero4.setText(String.format("%02d", Integer.parseInt(array[3])));
            textNumero5.setText(String.format("%02d", Integer.parseInt(array[4])));
            textNumero6.setText(String.format("%02d", Integer.parseInt(array[5])));
            textNumero7.setText(String.format("%02d", Integer.parseInt(array[6])));
            textNumero8.setText(String.format("%02d", Integer.parseInt(array[7])));
            textNumero9.setText(String.format("%02d", Integer.parseInt(array[8])));
            textNumero10.setText(String.format("%02d", Integer.parseInt(array[9])));
            textNumero11.setText(String.format("%02d", Integer.parseInt(array[10])));
            textNumero12.setText(String.format("%02d", Integer.parseInt(array[11])));
            textNumero13.setText(String.format("%02d", Integer.parseInt(array[12])));
            textNumero14.setText(String.format("%02d", Integer.parseInt(array[13])));
            textNumero15.setText(String.format("%02d", Integer.parseInt(array[14])));
            textNumero16.setText(String.format("%02d", Integer.parseInt(array[15])));
            textNumero17.setText(String.format("%02d", Integer.parseInt(array[16])));
            textNumero18.setText(String.format("%02d", Integer.parseInt(array[17])));
            textNumero19.setText(String.format("%02d", Integer.parseInt(array[18])));
            textNumero20.setText(String.format("%02d", Integer.parseInt(array[19])));
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