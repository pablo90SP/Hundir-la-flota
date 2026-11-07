import java.util.Random;
import java.util.Scanner;

public class HundirLaFlota {

    // Declaramos las variables
    private static final String AGUA = "🌊";
    private static final String BARCO = "🚢";
    private static final String TOCADO = "🧨";
    private static final String FALLADO = "💧";
    private static final String OCULTO = "❓"; // Símbolo para posiciones no atacadas
    private static final int LONGITUD_BARCO = 3; // Longitud de cada barco

    /**
     *Primero creamos las variables para identificar lo que nos encontraremos en los mapas y creamos un mapa para el jugador y otro para el ordenador.
     */

    private static String[][] mapaJugador = new String[5][5];
    private static String[][] mapaComputadora = new String[5][5];
    // Main principal

    public static void main(String[] args) {
        inicializarMapa(mapaJugador);
        inicializarMapa(mapaComputadora);
        Scanner scanner = new Scanner(System.in);

        // Opciones del menu
        while (true) {
            int opcion = mostrarMenu(scanner);

            switch (opcion) {
                case 1 -> jugar(scanner);
                case 2 -> mostrarNormas();
                case 3 -> {
                    System.out.println("Saliendo del juego...");
                    return; // Sale del programa
                }
                default -> System.out.println("Inténtalo de nuevo.");
            }
        }
    }
    /**
     * Creamos primero una variable para que se nos muestre el Menu del juego y luego creamos tres tipos de opciones para que al poner un numero se nos muestre cada una de ellas, si pulsamos el "1" comenzará el juego, si pulsamos el "2" saldrán las normas de este y el "3" que es para abandonar el juego.
     */

    // Inicializa el mapa con AGUA y coloca los barcos aleatoriamente
    private static void inicializarMapa(String[][] mapa) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                mapa[i][j] = AGUA; // Inicializa el mapa con AGUA
            }
        }

        // Colocar barcos aleatorios
        colocarBarcosAleatorios(mapa);
    }

    private static void colocarBarcosAleatorios(String[][] mapa) {
        Random random = new Random();
        int barcosColocados = 0;

        while (barcosColocados < 2) { // Cambia el número según cuántos barcos quieras colocar
            int fila = random.nextInt(5);
            int columna = random.nextInt(5);
            boolean horizontal = random.nextBoolean();

            // Verificar si hay espacio para colocar el barco
            if (puedeColocarBarco(mapa, fila, columna, horizontal)) {
                for (int i = 0; i < LONGITUD_BARCO; i++) {
                    if (horizontal) {
                        mapa[fila][columna + i] = BARCO;
                    } else {
                        mapa[fila + i][columna] = BARCO;
                    }
                }
                barcosColocados++;
            }
        }
    }

    private static boolean puedeColocarBarco(String[][] mapa, int fila, int columna, boolean horizontal) {
        for (int i = 0; i < LONGITUD_BARCO; i++) {
            int nuevaFila = horizontal ? fila : fila + i;
            int nuevaColumna = horizontal ? columna + i : columna;

            // Verificar si está fuera de límites o si ya hay un barco
            if (nuevaFila >= 5 || nuevaColumna >= 5 || BARCO.equals(mapa[nuevaFila][nuevaColumna])) {
                return false;
            }
        }
        return true;
    }

    /**
     *En esta sección se medirá la longitud de los barcos que en este caso son 3 y se pondrán aleatoriamente por toda la zona del mapa.
     */

    //Menu por pantalla

    private static int mostrarMenu(Scanner scanner) {
        System.out.println("#############################");
        System.out.println("#                           #");
        System.out.println("#      [Hundir la flota]    #");
        System.out.println("#       Menú principal      #");
        System.out.println("#                           #");
        System.out.println("#        Jugar (1)          #");
        System.out.println("#        Normas (2)         #");
        System.out.println("#        Salir (3)          #");
        System.out.println("#                           #");
        System.out.println("#############################");
        System.out.print("Selecciona una opción: ");
        return scanner.nextInt();
    }

    // Normas por pantalla

    private static void mostrarNormas() {
        System.out.println("\n===== Normas de Hundir la Flota =====");
        System.out.println("1. El juego consiste en hundir los barcos del oponente.");
        System.out.println("2. Ambos jugadores tienen barcos en un mapa de 5x5.");
        System.out.println("3. Los barcos tienen una longitud de 3 casillas y están posicionados en línea recta.");
        System.out.println("4. Introduce coordenadas para atacar: si aciertas, dirá 'Tocado', si no, 'Fallaste'.");
        System.out.println("5. Si destruyes las 3 partes de un barco, dirá 'Destruido'.");
        System.out.println("6. El juego termina cuando un jugador hunde todos los barcos del oponente o se retira.");
        System.out.println("====================================\n");
    }
    /**
     * En este apartado explicamos un poco como se mostrará el menú en pantalla y cuales serían las normas del juego.
     */

    private static void jugar(Scanner scanner) {
        scanner.nextLine(); // Limpiar buffer
        boolean juegoTerminado = false;

        while (!juegoTerminado) {
            System.out.println("Turno del Jugador");
            imprimirMapa(mapaComputadora);
            System.out.print("Introduce coordenada (fila columna) o 'abandono' para salir: ");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("abandono")) {
                System.out.println("El jugador ha abandonado el juego.");
                break;
            }

            if (realizarAtaque(mapaComputadora, entrada) && juegoTerminado(mapaComputadora)) {
                System.out.println("¡Felicidades! El jugador ha ganado.");
                break;
            }
            /**
             * Aquí como podemos observar, cuando sea el turno del jugador se le pedirá que escriba el número de filas y columnas que desea que se muestre en el juego o sino abandonar la partida.
             */

            // Turno de la computadora
            System.out.println("\nTurno de la Computadora");
            String ataqueComputadora = generarAtaqueComputadora();
            System.out.println("La computadora ataca en: " + ataqueComputadora);
            realizarAtaque(mapaJugador, ataqueComputadora);

            if (juegoTerminado(mapaJugador)) {
                System.out.println("La computadora ha ganado.");
                juegoTerminado = true;
            }
        }

        System.out.println("\nMapa final del Jugador:");
        imprimirMapa(mapaJugador);
        System.out.println("\nMapa final de la Computadora:");
        imprimirMapa(mapaComputadora);
    }
    /**
     *En estos dos apartados se nos indicará de quien es el turno y dirá en que zona hemos atacado de la zona y también que al ganar el jugador o el ordenador nos dirá que ha ganado uno de ellos.
     */


    private static void imprimirMapa(String[][] mapa) {
        for (String[] fila : mapa) {
            for (String c : fila) {
                // Muestra "❓" para las posiciones no atacadas
                if (c.equals(BARCO) || c.equals(AGUA)) {
                    System.out.print(OCULTO + " ");
                } else {
                    System.out.print(c + " ");
                }
            }
            System.out.println();
        }
    }

    //Realiza ataque y muestra por pantalla dependiendo de la entrada

    private static boolean realizarAtaque(String[][] mapa, String coordenadas) {
        try {
            String[] partes = coordenadas.split(" ");
            int fila = Integer.parseInt(partes[0]);
            int columna = Integer.parseInt(partes[1]);

            if (fila < 0 || fila >= mapa.length || columna < 0 || columna >= mapa[0].length) {
                System.out.println("Coordenadas fuera de rango.");
                return false;
            }

            String objetivo = mapa[fila][columna];
            if (objetivo.equals(BARCO)) {
                mapa[fila][columna] = TOCADO;
                System.out.println("¡Tocado!");
                if (esDestruido(mapa, fila, columna)) {
                    System.out.println("¡Destruido!");
                }
            } else if (objetivo.equals(AGUA)) {
                mapa[fila][columna] = FALLADO;
                System.out.println("Fallaste.");
            } else {
                System.out.println("Ya atacaste esta posición.");
            }
            return true;
        } catch (Exception _) {
            System.out.println("Entrada inválida. Intenta de nuevo.");
            return false;
        }
    }
    /**
     * Aquí se nos pedirá que digamos el número de filas y columnas para que ataque en esa zona en específica.
     */



    /**
     *En caso de que pongamos algún número fuera del rango de 0-4 nos indicará que nos hemos salido de la zona de rango. Y por cada acción que hagamos se nos indicará en pantalla si hemos dado al barco o no.
     */

    private static boolean esDestruido(String[][] mapa, int fila, int columna) {
        // Verifica si todas las partes de un barco han sido tocadas
        int partesTocadas = 0;

        for (int i = 0; i < LONGITUD_BARCO; i++) {
            if (columna + i < 5 && mapa[fila][columna + i].equals(TOCADO)) {
                partesTocadas++;
            }
            if (fila + i < 5 && mapa[fila + i][columna].equals(TOCADO)) {
                partesTocadas++;
            }
        }

        return partesTocadas == LONGITUD_BARCO;
    }

    private static boolean juegoTerminado(String[][] mapa) {
        for (String[] fila : mapa) {
            for (String c : fila) {
                if (c.equals(BARCO)) {
                    return false;
                }
            }
        }
        System.out.println("¡Has ganado!");
        return true;
    }

    // Ataque aleatorio de la Compu

    private static String generarAtaqueComputadora() {
        Random random = new Random();
        int fila = random.nextInt(5);
        int columna = random.nextInt(5);
        return fila + " " + columna;
    }
}
/**
 *En esta sección diseñamos los comandos para que se lleve a cabo la destrucción de los barcos enemigos y que al final de la partida se muestre como que hemos ganado.
 */