package com.javarush.test.level03.lesson04.task03;

/* StarCraft
Создать 10 зергов, 5 протосов и 12 терран.
Дать им всем уникальные имена.
*/

public class Solution
{
    public static void main(String[] args)
    {
        Zerg zerg1 = new Zerg();
        zerg1.name = "1";
        Zerg zerg2 = new Zerg();
        zerg2.name = "10";
        Zerg zerg3 = new Zerg();
        zerg3.name = "11";
        Zerg zerg4 = new Zerg();
        zerg4.name = "12";
        Zerg zerg5 = new Zerg();
        zerg5.name = "13";
        Zerg zerg6 = new Zerg();
        zerg6.name = "14";
        Zerg zerg7 = new Zerg();
        zerg7.name = "15";
        Zerg zerg8 = new Zerg();
        zerg8.name = "16";
        Zerg zerg9 = new Zerg();
        zerg9.name = "17";
        Zerg zerg10 = new Zerg();
        zerg10.name = "18";
        Protos protos1 = new Protos();
        protos1.name ="b1";
        Protos protos2 = new Protos();
        protos2.name ="b12";
        Protos protos3 = new Protos();
        protos3.name ="b13";
        Protos protos4 = new Protos();
        protos4.name ="b14";
        Protos protos5 = new Protos();
        protos5.name ="b15";
        Terran terran1 = new Terran();
        terran1.name = "n1";
        Terran terran2 = new Terran();
        terran2.name = "n12";
        Terran terran3 = new Terran();
        terran3.name = "n13";
        Terran terran4 = new Terran();
        terran4.name = "n14";
        Terran terran5 = new Terran();
        terran5.name = "n15";
        Terran terran6 = new Terran();
        terran6.name = "n16";
        Terran terran7 = new Terran();
        terran7.name = "n17";
        Terran terran8 = new Terran();
        terran8.name = "n18";
        Terran terran9 = new Terran();
        terran9.name = "n19";
        Terran terran10 = new Terran();
        terran10.name = "n122";
        Terran terran11 = new Terran();
        terran11.name = "n123";
        Terran terran12 = new Terran();
        terran12.name = "n124";
    }

    public static class Zerg
    {
        public String name;
    }

    public static class Protos
    {
        public String name;
    }

    public static class Terran
    {
        public String name;
    }
}