Juego de Hundir la flota por consola desarrollado en Java. El jugador se enfrenta a la máquina por turnos hasta que uno de los dos hunde todos los barcos del rival.

Funcionalidades
- Menú principal con las opciones Jugar, Normas y Salir.
- Tablero de 5x5 para el jugador y otro para la máquina.
- Colocación aleatoria de 2 barcos de 3 casillas, en horizontal o en vertical.
- Ataques mediante coordenadas en formato fila columna (valores de 0 a 4).
- Mensajes de Tocado, Fallaste y Destruido según el resultado del ataque.
- Turno de la máquina con ataques aleatorios.
- Opción de abandonar la partida escribiendo abandono.
- Visualización de los mapas finales al terminar la partida.

Tecnologías
- Java 14 o superior
- Scanner para la entrada por consola
- Random para la colocación de barcos y los ataques de la máquina

Ejecución
javac HundirLaFlota.java
java HundirLaFlota

La consola debe admitir caracteres Unicode para que el tablero se muestre correctamente.
