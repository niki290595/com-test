package com.javarush.test.level07.lesson04.task04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/* Массив из чисел в обратном порядке
1. Создать массив на 10 чисел.
2. Ввести с клавиатуры 10 чисел и записать их в массив.
3. Расположить элементы массива в обратном порядке.
4. Вывести результат на экран, каждое значение выводить с новой строки.
*/

public class Solution
{
    public static void main(String[] args) throws Exception
    {
        //напишите тут ваш код
        int[] ints = new int[10];
        try (Scanner in = new Scanner(System.in)) {
            for (int i = 0; i < ints.length; i++) ints[i] = in.nextInt();
        }

        int index = ints.length;
        for (int i = 0; i < ints.length / 2; i++) {
            int tmp = ints[i];
            ints[i] = ints[--index];
            ints[index] = tmp;
        }

        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }
}
