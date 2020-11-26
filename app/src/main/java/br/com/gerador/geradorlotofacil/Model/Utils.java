package br.com.gerador.geradorlotofacil.Model;

public class Utils {

    // Constantes
    private static final String TOKEN = "045e0599bc9234e3fde6bab4a32c83aa4482a28fdd3d03667bf0e267be93e5c3";

    // Método responsável por retornar a string
    public static String getUrl(String loteria){
        return "https://www.lotodicas.com.br/api/v2/" + loteria + "/results/last?token=" + TOKEN;
    }

}
