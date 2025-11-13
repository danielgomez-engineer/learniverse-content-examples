package Ejercicios_programación.Dificultad.Faciles;

public class FizzBuzz {

    public static void main(String[] args) {
        // Paso 1: Inicializar un bucle for para iterar desde 1 hasta 100
        for(int i = 1; i <= 100; i ++) {

            // Paso 2: Verificar si el número es divisible por 3 Y por 5 (múltiplo de ambos)
            if(i % 3 == 0 && i % 5 == 0){
                System.out.println("fizzbuzz");
            }
            // Paso 3: Si no, verificar si es divisible solo por 3
            else if (i % 3 == 0) {
                System.out.println("fizz");
            }
            // Paso 4: Si no, verificar si es divisible solo por 5
            else if (i % 5 == 0){
                System.out.println("buzz");
            }
            // Paso 5: Si no cumple ninguna condición, imprimir el número
            else {
                System.out.println(i);
            }

        }
    }
}
