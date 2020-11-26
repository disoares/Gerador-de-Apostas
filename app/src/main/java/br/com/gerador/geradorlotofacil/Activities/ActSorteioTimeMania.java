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

public class ActSorteioTimeMania extends AppCompatActivity {

    // Constantes
    private final String LOTERIA = "timemania";
    private final String URL = Utils.getUrl(LOTERIA);

    /* Toolbar */
    private Toolbar toolbarPrincipal;

    /* TextView */
    private TextView textCabecalho;
    private TextView textNumero1,textNumero2, textNumero3, textNumero4, textNumero5, textNumero6, textNumero7, text_time_coracao;
    private TextView txtNumeroSorteado1, txtNumeroSorteado2, txtNumeroSorteado3, txtNumeroSorteado4, txtNumeroSorteado5, txtNumeroSorteado6,
            txtNumeroSorteado7, txtNumeroSorteado8, txtNumeroSorteado9, txtNumeroSorteado10, text_time_coracao_sorteado;

    /* Button */
    private Button buttonGerarAposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sorteio_time_mania);

        /* Toolbar */
        toolbarPrincipal = findViewById(R.id.toolbarPrincipal);
        toolbarPrincipal.setTitle(getResources().getString(R.string.menu_timemania));
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
        text_time_coracao = findViewById(R.id.text_time_coracao);
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
        text_time_coracao_sorteado = findViewById(R.id.text_time_coracao_sorteado);

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
            int[] nSorteados = new int[10];
            Random aleatorio = new Random();
            int numero;
            ArrayList<String> numeros = numeros();

            // while para excluir os números
            while (numeros.size() != 10) {
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
            text_time_coracao_sorteado.setText(sorteiaTime().toUpperCase());
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
    }

    /**
     * Método responsável por gravar o cache para consultas offline
     * @param - número do concurso
     * @param - data do concurso
     * @param dezenas - Array com as dezenas separadas por ";"
     */
    public void gravaCache(String concurso, String data, String dezenas[], String time){
        SharedPreferences prefs = getSharedPreferences("dezenas_timemania", 0);
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
        editor.putString("time", time);
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

    /**
     * Método responsável por sortear o time
     * @return Retorna o time sorteado
     */
    public String sorteiaTime(){

        // Declara o random
        Random aleatorio = new Random();

        // Coloca os times no array
        String times[] = {getResources().getString(R.string.time_abc_rn), getResources().getString(R.string.time_america_mg),
                getResources().getString(R.string.time_america_rj), getResources().getString(R.string.time_america_rn),
                getResources().getString(R.string.time_americano_rj), getResources().getString(R.string.time_atletico_go),
                getResources().getString(R.string.time_atletico_mg), getResources().getString(R.string.time_atletico_pr),
                getResources().getString(R.string.time_avai_sc), getResources().getString(R.string.time_bahia_ba),
                getResources().getString(R.string.time_bangu_rj), getResources().getString(R.string.time_barueri_sp),
                getResources().getString(R.string.time_botafogo_pb), getResources().getString(R.string.time_botafogo_rj),
                getResources().getString(R.string.time_bragantino_rj), getResources().getString(R.string.time_brasiliense_df),
                getResources().getString(R.string.time_ceara_ce), getResources().getString(R.string.time_corinthians_sp),
                getResources().getString(R.string.time_coritiba_pr), getResources().getString(R.string.time_crb_al),
                getResources().getString(R.string.time_criciuma_sc), getResources().getString(R.string.time_cruzeiro_mg),
                getResources().getString(R.string.time_csa_al), getResources().getString(R.string.time_desportiva_es),
                getResources().getString(R.string.time_figueirense_sc), getResources().getString(R.string.time_flamengo_rj),
                getResources().getString(R.string.time_fluminense_rj), getResources().getString(R.string.time_fortaleza_ce),
                getResources().getString(R.string.time_gama_df), getResources().getString(R.string.time_goias_go),
                getResources().getString(R.string.time_gremio_rs), getResources().getString(R.string.time_guarani_sp),
                getResources().getString(R.string.time_inter_limeira_sp), getResources().getString(R.string.time_internacional_rs),
                getResources().getString(R.string.time_ipatinga_mg), getResources().getString(R.string.time_ituano_sp),
                getResources().getString(R.string.time_ji_parana_ro), getResources().getString(R.string.time_joinville_sc),
                getResources().getString(R.string.time_juventude_rs), getResources().getString(R.string.time_juventus_sp),
                getResources().getString(R.string.time_londrina_pr), getResources().getString(R.string.time_marilia_sp),
                getResources().getString(R.string.time_mixto_mt), getResources().getString(R.string.time_moto_clube_ma),
                getResources().getString(R.string.time_nacional_am), getResources().getString(R.string.time_nautico_pe),
                getResources().getString(R.string.time_olaria_rj), getResources().getString(R.string.time_operario_ms),
                getResources().getString(R.string.time_palmas_to), getResources().getString(R.string.time_palmeiras_sp),
                getResources().getString(R.string.time_parana_pr), getResources().getString(R.string.time_paulista_sp),
                getResources().getString(R.string.time_paysandu_pa), getResources().getString(R.string.time_ponte_preta_sp),
                getResources().getString(R.string.time_port_desport_sp), getResources().getString(R.string.time_ramo_pa),
                getResources().getString(R.string.time_rio_branco_ac), getResources().getString(R.string.time_rio_branco_es),
                getResources().getString(R.string.time_river_pi), getResources().getString(R.string.time_roraima_rr),
                getResources().getString(R.string.time_sampaio_correa_ma), getResources().getString(R.string.time_santa_cruz_pe),
                getResources().getString(R.string.time_santo_andre_sp), getResources().getString(R.string.time_santos_sp),
                getResources().getString(R.string.time_sao_caetano_sp), getResources().getString(R.string.time_sao_paulo_sp),
                getResources().getString(R.string.time_sao_raimundo_am), getResources().getString(R.string.time_sergipe_se),
                getResources().getString(R.string.time_sport_pe), getResources().getString(R.string.time_treze_pb),
                getResources().getString(R.string.time_tuna_luso_pa), getResources().getString(R.string.time_uberlandia_mg),
                getResources().getString(R.string.time_uniao_barbarense_sp), getResources().getString(R.string.time_uniao_sao_joao_sp),
                getResources().getString(R.string.time_vasco_rj), getResources().getString(R.string.time_vila_nova_go),
                getResources().getString(R.string.time_vila_nova_mg), getResources().getString(R.string.time_vitoria_ba),
                getResources().getString(R.string.time_xv_piracicaba_sp), getResources().getString(R.string.time_ypiranga_ap)};

        // Retorna o time sorteado
        return times[aleatorio.nextInt(times.length)];

    }

    /***************************************************** REQUISIÇÕES **/

    public class Loterias extends AsyncTask<Void, Void, String> {
        String url;
        ProgressDialog progresso;

        // Construtor
        public Loterias(String url, ActSorteioTimeMania activity) {
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
                SharedPreferences prefs = getSharedPreferences("dezenas_timemania", 0);
                // Gera a string com as dezenas separadas por "|"
                String array = "[" + prefs.getString("00", "") + "," + prefs.getString("01", "") + "," +
                                     prefs.getString("02", "") + "," + prefs.getString("03", "") + "," +
                                     prefs.getString("04", "") + "," + prefs.getString("05", "") + "," +
                                     prefs.getString("06", "") + "]";

                // Chama o método responsável por mostrar o resultado e mostrar o cabeçalho
                preencheResultado(prefs.getString("concurso", ""), prefs.getString("data", ""), array,
                        prefs.getString("time", ""));

                Toast.makeText(ActSorteioTimeMania.this, getResources().getString(R.string.texto_semconexao), Toast.LENGTH_SHORT).show();

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
                String time = dezenasSorteadas.getString("team");

                // Formata a data
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date data2 = sdf.parse(data);
                sdf.applyPattern("dd/MM/yyyy");
                String dataFormatada = sdf.format(data2);

                // Chama o método responsável por preencher os resultados e mostrar o cabeçalho
                preencheResultado(concurso, dataFormatada, dezenas, time);

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
        public void preencheResultado(String concurso, String data, String dezenas, String time){
            SharedPreferences prefs = getSharedPreferences("dezenas_timemania", 0);
            String numeros = dezenas.replace("[", "").replace("]", "").replace(",", ";");
            String array[] = numeros.split(";");

            // Verifica se o concurso buscado é o mesmo do gravado em cache
            if(!concurso.equalsIgnoreCase(prefs.getString("concurso", ""))){
                gravaCache(concurso, data, array, time);
            }

            // Preenche o cabeçalho
            textCabecalho.setText(getResources().getString(R.string.texto_semconexao) + " " + concurso + " (" + data + ")");

            // Preenche os números
            textNumero1.setText(String.format("%02d", Integer.parseInt(array[0])));
            textNumero2.setText(String.format("%02d", Integer.parseInt(array[1])));
            textNumero3.setText(String.format("%02d", Integer.parseInt(array[2])));
            textNumero4.setText(String.format("%02d", Integer.parseInt(array[3])));
            textNumero5.setText(String.format("%02d", Integer.parseInt(array[4])));
            textNumero6.setText(String.format("%02d", Integer.parseInt(array[5])));
            textNumero7.setText(String.format("%02d", Integer.parseInt(array[6])));
            text_time_coracao.setText(time);
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