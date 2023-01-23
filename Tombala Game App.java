/*1. Bir tombala torbasında 1'den 99'a kadar numaralanmış (99 dahil) pullar bulunmaktadır. Bu tombala torbasıyla 
aşağıdaki oyunlar oynanmaktadır:
Çekilen bir pul torbaya geri atılmamak üzere
i) Torbadan 3 pul çekiliyor. Çekilen pulların toplamı 150' den küçük ise oyuncu kazanıyor.
ii) Torbadan 3 pul çekiliyor. Çekilen pulların toplamı asal sayı ise oyuncu kazanıyor.
iii) Torbadan 3 pul çekiliyor. En büyük değerli pul ile en küçük değerli pul arasındaki fark ortanca değerli puldan 
büyükse oyuncu kazanıyor.
Oynanacak her bir oyun için oyuncunun kazanma olasılığını en az 30000 oyunu simule ederek hesaplayınız*/


package csd;

class App {
    public static void main(String[] args)
    {
        LottoProbabilitySimulationApp.run();
    }
}

class LottoProbabilitySimulationApp {
    public static void run()
    {
        java.util.Scanner kb = new java.util.Scanner(System.in);

        for (;;) {
            System.out.print("Kaç kez oynatmak istersiniz?");
            int count = Integer.parseInt(kb.nextLine());

            if (count <= 0)
                break;

            LottoProbabilitySimulation simulation = new LottoProbabilitySimulation();

            simulation.run(count);

            System.out.printf("1.oyun için kazanma olasılığı:%f%n", simulation.game1Prob);
            System.out.printf("2.oyun için kazanma olasılığı:%f%n", simulation.game2Prob);
            System.out.printf("3.oyun için kazanma olasılığı:%f%n", simulation.game3Prob);
        }

    }
}

class LottoProbabilitySimulation {
    public double game1Prob;
    public double game2Prob;
    public double game3Prob;

    public void run(int count)
    {
        java.util.Random r = new java.util.Random();

        int winCount1,  winCount2,  winCount3;

        winCount1 = winCount2 = winCount3 = 0;

        for (int i = 0; i < count; ++i) {
            Lotto lotto = new Lotto(r);

            lotto.play();

            if (lotto.winGame1)
                ++winCount1;
            if (lotto.winGame2)
                ++winCount2;
            if (lotto.winGame3)
                ++winCount3;
        }

        game1Prob = (double)winCount1 / count;
        game2Prob = (double)winCount2 / count;
        game3Prob = (double)winCount3 / count;
    }

}

class Lotto {
    public boolean winGame1;
    public boolean winGame2;
    public boolean winGame3;
    public java.util.Random random;

    public int getFirst()
    {
        return random.nextInt(1, 100);
    }

    public int getSecond(int first)
    {
        int second;

        while ((second = random.nextInt(1, 100)) == first)
            ;

        return second;
    }

    public int getThird(int first, int second)
    {
        int third;

        while ((third = random.nextInt(1, 100)) == first || third == second)
            ;

        return third;
    }

    public void playGame1(int first, int second, int third)
    {
        winGame1 = first + second + third < 150;
    }

    public void playGame2(int first, int second, int third)
    {
        winGame2 = NumberUtil.isPrime(first + second + third);
    }

    public void playGame3(int first, int second, int third)
    {
        int min = Math.min(Math.min(first, second), third);
        int max = Math.max(Math.max(first, second), third);
        int mid = first + second + third - min - max;

        winGame3 = max - min > mid;
    }

    public Lotto(java.util.Random r)
    {
        random = r;
    }

    public void play()
    {
        int first = getFirst();
        int second = getSecond(first);
        int third = getThird(first, second);

        playGame1(first, second, third);
        playGame2(first, second, third);
        playGame3(first, second, third);
    }
}

class NumberUtil {
    public static boolean isPrime(long a)
    {
        if (a <= 1)
            return false;

        if (a % 2 == 0)
            return a == 2;

        if (a % 3 == 0)
            return a == 3;

        if (a % 5 == 0)
            return a == 5;

        if (a % 7 == 0)
            return a == 7;

        for (long i = 11; i * i <= a; i += 2)
            if (a % i == 0)
                return false;

        return true;
    }
}