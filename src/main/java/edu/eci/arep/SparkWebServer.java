package edu.eci.arep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static spark.Spark.port;
import static spark.Spark.get;

/**
 * Esta clase implementa un servidor web simple utilizando el framework Spark Java.
 *
 * @author Jefer Alexis Gonzalez Romero
 * @version 1.0 (28/02/2023)
 */
public class SparkWebServer {

    /**
     * Punto de entrada principal de la aplicación. Inicia el servidor web y define las rutas.
     */
    public static void main(String... args) {
        port(getPort());
        get("/calculadora", (req, res) -> webClient());
        get("/computar", (req, res) -> {
            res.type("application/json");
            return compute(req.queryParams("comando"));
        });
    }

    /**
     * Recupera el número de puerto para ejecutar el servidor. Busca la variable de entorno "PORT" y usa 4567 como predeterminado.
     *
     * @return Puerto que se usará para la aplicación.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    /**
     * Procesa la cadena de consulta proporcionada que contiene un comando y parámetros opcionales.
     *
     * @param query La cadena de consulta que contiene el comando y los parámetros.
     * @return El resultado de la operación en formato JSON.
     */
    public static String compute(String query) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String outputLine = "{\"result\":";
        String[] commandAndParams = query.split("\\(");
        String command = commandAndParams[0];
        if (command.equals("pal")) {
            String[] result = palindrome(commandAndParams[1].replace(")", "").split(","));
            outputLine += Arrays.toString(result);
        } else {
            double[] array = doubleArrayParams(commandAndParams[1]);
            if (command.equals("vem")) {
                array = new double[]{vectorMagnitude(array)};
            } else mathMethods(array, command);
            outputLine += Arrays.toString(array);
        }
        return outputLine + "}";
    }

    /**
     * Comprueba si cada cadena en el array proporcionado es un palíndromo.
     *
     * @param array  Un array de cadenas para verificar si son palíndromos.
     * @return Un array de cadenas, donde cada elemento representa si el elemento correspondiente en el array de entrada es un palíndromo.
     */
    public static String[] palindrome(String[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            boolean isPalindrome = true;
            String string = array[i];
            int stringLength = string.length();
            for (int j = 0; j < stringLength / 2; j++) {
                if (string.toLowerCase().charAt(j) != string.toLowerCase().charAt(stringLength - 1 - j)) {
                    isPalindrome = false;
                    break;
                }
            }
            result[i] = String.valueOf(isPalindrome);
        }
        return result;
    }

    /**
     * Calcula la magnitud (longitud) de un vector 2D representado por el array de entrada.
     *
     * @param array Un array de doubles que representa un vector 2D (coordenadas x, y).
     * @return La magnitud del vector.
     */
    public static double vectorMagnitude(double[] array) {
        int index = 0;
        double result = 0;
        for (int i = 0; i < array.length - 1; i += 2) {
            result = Math.sqrt(Math.pow(array[i], 2) + Math.pow(array[i + 1], 2));
            index++;
        }
        return result;
    }

    /**
     * Pasa un string con los parámetros divididos por comas a un arreglo de doubles.
     *
     * @param paramsString String con los parámetros separados por comas.
     * @return Arreglo de doubles con los parámetros a clacular.
     */
    public static double[] doubleArrayParams(String paramsString) {
        String params = paramsString.replace(")", "");
        String[] arrayStrings = params.split(",");
        double[] array = new double[arrayStrings.length];
        for (int i = 0; i < arrayStrings.length; i++) array[i] = Double.parseDouble(arrayStrings[i]);
        return array;
    }

    /**
     * Hace el cálculo con Math de java para cada uno de los parámetros dados.
     *
     * @param array   Arreglo con los parámetros que se quieren calcular
     * @param command Comando con la operación que se quiere hacer.
     * @throws NoSuchMethodException     Thrown when a particular method cannot be found.
     * @throws InvocationTargetException Exception thrown by an invoked method or constructor
     * @throws IllegalAccessException    Application tries to reflectively create an instance
     */
    public static void mathMethods(double[] array, String command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < array.length; i++) {
            Method method = Math.class.getMethod(command, double.class);
            array[i] = (double) method.invoke(null, array[i]);
        }
    }

    public static String webClient() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Calculadora</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body style=\"display:flex\">\n" +
                "       <div>" +
                "        <h1>Calcular Seno</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-sin\">Número (Si es más de uno, toca separarlos por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-sin\" name=\"name\" value=\"-3.67\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('sin')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-sin\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Calcular Coseno</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-cos\">Número (Si es más de uno, toca separarlos por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-cos\" name=\"name\" value=\"-3.67\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('cos')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-cos\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Determinar si es palíndromo</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-pal\">Cadena (Si es más de una, toca separarlas por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-pal\" name=\"name\" value=\"Reconocer\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('pal')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-pal\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Calcular magnitud del vector</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-vem\">Parametros (Deben estar separados por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-vem\" name=\"name\" value=\"3,4\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('vem')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-vem\"></div>\n" +
                "       </div>" +
                "\n" +
                clientJS() +
                "    </body>";
    }

    /**
     * Da el código de java script necesario para el cliente.
     *
     * @return String con el script de javascript.
     */
    public static String clientJS() {
        return "        <script>\n" +
                "            function loadGetMsg(command) {\n" +
                "                let number = document.getElementById(\"params-\"+ command).value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg-\" + command).innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/computar?comando=\" + command + \"(\" + number +\")\");\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n";
    }

}
