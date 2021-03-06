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

public class ActSorteioDiaDeSorte extends AppCompatActivity {

    // Constantes
    private final String LOTERIA = "dia_de_sorte";
    private final String URL = Utils.getUrl(LOTERIA);

    /* Toolbar */
    private Toolbar toolbarPrincipal;

    /* TextView */
    private TextView textCabecalho;
    private TextView textNumero1,textNumero2, textNumero3, textNumero4, textNumero5, textNumero6, textNumero7, textMes;
    private TextView txtNumeroSorteado1, txtNumeroSorteado2, txtNumeroSorteado3, txtNumeroSorteado4, txtNumeroSorteado5, txtNumeroSorteado6,
            txtNumeroSorteado7, textMesSorteado;

    /* Button */
    private Button buttonGerarAposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sorteio_dia_de_sorte);

        /* Toolbar */
        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        toolbarPrincipal.setTitle(getResources().getString(R.string.menu_diadesorte));
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
        textMes = findViewById(R.id.textMes);
        txtNumeroSorteado1 = findViewById(R.id.txtNumeroSorteado1);
        txtNumeroSorteado2 = findViewById(R.id.txtNumeroSorteado2);
        txtNumeroSorteado3 = findViewById(R.id.txtNumeroSorteado3);
        txtNumeroSorteado4 = findViewById(R.id.txtNumeroSorteado4);
        txtNumeroSorteado5 = findViewById(R.id.txtNumeroSorteado5);
        txtNumeroSorteado6 = findViewById(R.id.txtNumeroSorteado6);
        txtNumeroSorteado7 = findViewById(R.id.txtNumeroSorteado7);
        textMesSorteado = findViewById(R.id.textMesSorteado);

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
            int[] nSorteados = new int[7];
            Random aleatorio = new Random();
            int numero;
            ArrayList<String> numeros = numeros();

            // while para excluir os números
            while (numeros.size() != 7) {
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
            textMesSorteado.setText(sorteiaMes());
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
    }

    /**
     * Método responsável por gravar o cache para consultas offline
     * @param - número do concurso
     * @param - data do concurso
     * @param dezenas - Array com as dezenas separadas por ";"
     */
    public void gravaCache(String concurso, String data, String dezenas[], String mes){
        SharedPreferences prefs = getSharedPreferences("dezenas_diadesorte", 0);
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
        editor.putString("mes", mes);
        editor.commit();
    }

    /**
     * Método responsável por retornar os números de 1 a 25
     *
     * @return - Retorna um ArrayList com os números
     */
    public static ArrayList<String> numeros() {
        ArrayList<String> numeros = new ArrayList<>();

        for(int i = 1; i <= 31; i++){
            if(i < 10){
                numeros.add("0"+i);
            }else{
                numeros.add(""+i);
            }
        }

        return numeros;
    }

    /**
     * Método responsável por sortear o time
     * @return Retorna o time sorteado
     */
    public String sorteiaMes(){

        // Declara o random
        Random aleatorio = new Random();

        // Atribui os meses em um array
        String meses[] = {getResources().getString(R.string.mes_janeiro), getResources().getString(R.string.mes_fevereiro),
                getResources().getString(R.string.mes_marco), getResources().getString(R.string.mes_abril),
                getResources().getString(R.string.mes_maio), getResources().getString(R.string.mes_junho),
                getResources().getString(R.string.mes_julho), getResources().getString(R.string.mes_agosto),
                getResources().getString(R.string.mes_setembro), getResources().getString(R.string.mes_outubro),
                getResources().getString(R.string.mes_novembro), getResources().getString(R.string.mes_dezembro)};

        // Retorna o mês sorteado
        return meses[aleatorio.nextInt(meses.length)];

    }

    /***************************************************** REQUISIÇÕES **/

    public class Loterias extends AsyncTask<Void, Void, String> {
        String url;
        ProgressDialog progresso;

        // Construtor
        public Loterias(String url, ActSorteioDiaDeSorte activity) {
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
                SharedPreferences prefs = getSharedPreferences("dezenas_diadesorte", 0);
                // Gera a string com as dezenas separadas por "|"
                String array = "[" + prefs.getString("00", "") + "," + prefs.getString("01", "") + "," +
                        prefs.getString("02", "") + "," + prefs.getString("03", "") + "," +
                        prefs.getString("04", "") + "," + prefs.getString("05", "") + "," +
                        prefs.getString("06", "") + "]";

                // Chama o método responsável por mostrar o resultado e mostrar o cabeçalho
                preencheResultado(prefs.getString("concurso", ""), prefs.getString("data", ""), array,
                        prefs.getString("mes", ""));

                Toast.makeText(ActSorteioDiaDeSorte.this, getResources().getString(R.string.texto_semconexao), Toast.LENGTH_SHORT).show();

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
                String mes = dezenasSorteadas.getString("month");

                /*JSONObject array = new JSONObject(json);

                String concurso = array.getString("numero");
                String data = array.getString("data");
                String dezenas = array.getString("sorteio");
                String mes = array.getString("mes");*/

                // Formata a data
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date data2 = sdf.parse(data);
                sdf.applyPattern("dd/MM/yyyy");
                String dataFormatada = sdf.format(data2);

                // Chama o método responsável por preencher os resultados e mostrar o cabeçalho
                preencheResultado(concurso, dataFormatada, dezenas, mes);

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
        public void preencheResultado(String concurso, String data, String dezenas, String mes){
            SharedPreferences prefs = getSharedPreferences("dezenas_diadesorte", 0);
            String numeros = dezenas.replace("[", "").replace("]", "").replace(",", ";");
            String array[] = numeros.split(";");

            // Verifica se o concurso buscado é o mesmo do gravado em cache
            if(!concurso.equalsIgnoreCase(prefs.getString("concurso", ""))){
                gravaCache(concurso, data, array, mes);
            }

            // Preenche o cabeçalho
            textCabecalho.setText(getResources().getString(R.string.texto_cabecalho_diadesorte) + " " + concurso + " (" + data + ")");

            // Preenche os números
            textNumero1.setText(String.format("%02d", Integer.parseInt(array[0])));
            textNumero2.setText(String.format("%02d", Integer.parseInt(array[1])));
            textNumero3.setText(String.format("%02d", Integer.parseInt(array[2])));
            textNumero4.setText(String.format("%02d", Integer.parseInt(array[3])));
            textNumero5.setText(String.format("%02d", Integer.parseInt(array[4])));
            textNumero6.setText(String.format("%02d", Integer.parseInt(array[5])));
            textNumero7.setText(String.format("%02d", Integer.parseInt(array[6])));
            textMes.setText(mes);
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