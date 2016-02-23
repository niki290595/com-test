package com.javarush.test.level07.lesson04.task05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/* Один большой массив и два маленьких
1. Создать массив на 20 чисел.
2. Ввести в него значения с клавиатуры.
3. Создать два массива на 10 чисел каждый.
4. Скопировать большой массив в два маленьких: половину чисел в первый маленький, вторую половину во второй маленький.
5. Вывести второй маленький массив на экран, каждое значение выводить с новой строки.
*/

public class Solution
{
    public static void main(String[] args) throws Exception
    {
        //напишите тут ваш код
        int[] largeMas = new int[20];
        try (Scanner in = new Scanner(System.in)) {
            for (int i = 0; i < largeMas.length; i++) largeMas[i] = in.nextInt();
        }

        int[] smallMas1 = new int[10];
        int[] smallMas2 = new int[10];
        for (int i = 0; i < smallMas1.length; i++) {
            smallMas1[i] = largeMas[i];
            smallMas2[i] = largeMas[10 + i];
        }

        for (int i = 0; i < smallMas2.length; i++) System.out.println(smallMas2[i]);
    }
}
