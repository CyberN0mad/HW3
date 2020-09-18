package com.company;


import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int bossHealth = 1500;
    public static int bossDamage = 60;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {350, 360, 370, 400, 420, 440, 450, 600};
    public static int[] heroesDamage = {40, 25, 30, 5, 0, 15, 45, 35};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic",
            "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber = 0;
    public static boolean isSleeping = false;


    public static void main(String[] args) {
        System.out.println("Start");
        printStatistics();
        while (!isgameOver()) {
            roundNumber++;
            System.out.println("----------  Round: " + roundNumber + "  -----------");
            round();
        }
    }

    private static void printStatistics() {
        System.out.println("_________________");
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + "Health " + heroesHealth[i]);
        }
    }

    public static void round() {
        changeBossDefence();
        heroesHit();
        if (bossHealth > 0) {
            bossHits();
        }


        printStatistics();

    }

    public static boolean isgameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;

        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
            if (allHeroesDead) {
                System.out.println("Boss won!!!");
                break;
            }

        }
        return allHeroesDead;
    }

    public static void bossHits() {
        if (!isSleeping) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] < bossDamage || heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }else {
            isSleeping = false;
        }
        Medic();
        Golem();
        Lucky();
        Berserk();
        Thor();
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        for (int i = 0; i < bossDefenceType.length(); i++) {
            if (bossDefenceType == heroesAttackType[i] && heroesHealth[i] == 0) {
                continue;
            } else {
                System.out.println("Boss choose" + bossDefenceType);
                break;
            }
        }

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] <= bossDamage) {
                heroesHealth[i] = 0;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0) {
                if (bossHealth > 0) {
                    if (bossDefenceType == heroesAttackType[i]) {
                        Random r = new Random();
                        int coef = r.nextInt(6) + 2; //2,3,4,5,6,7,8,
                        if (bossHealth - heroesDamage[i] * coef < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i] * coef;
                        }
                        System.out.println("Critical attack: " + (heroesDamage[i]));
                    } else {
                        if (bossHealth - heroesDamage[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i];
                        }

                    }
                } else {
                    break;
                }
            }
        }
    }

    public static void Medic() {
        Random m = new Random();
        int medicRandom = m.nextInt(40) + 1;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 100 && heroesHealth[4] > 0) {
                System.out.println("Medic heals: " + medicRandom);
                heroesHealth[i] += medicRandom;
            }
            return;
        }
    }
    public static void Golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0 && heroesHealth[i] > 0 && !isSleeping) {
                heroesHealth[4] = heroesHealth[4] - (bossDamage / 5);
                heroesHealth[i] = heroesHealth[i] + (bossDamage / 5);
            }

        }

    }
    public static void Lucky() {
        boolean luckyHero = false;
        Random r = new Random();
        Scanner  userSolve = new Scanner(System.in);
        int luckyRandom = r.nextInt(2) + 1;
        if (heroesHealth[5] > 0) {
            if (luckyRandom == 1) {
                luckyHero = true;
                heroesHealth[5] += bossDamage;
                System.out.println("Lucky dodged!!! ");
            }
        }
    }
    public static void Berserk() {
        Random p = new Random();
        int blockRandom = p.nextInt(30) + 1;
        if (!isSleeping) {
            if (heroesHealth[6] > 0) {
                heroesHealth[6] += blockRandom;
                bossHealth -= (heroesDamage[6] + blockRandom);
                System.out.println("Damage blocked by Berserk: " + blockRandom);
            }
        }
    }
    public static void Thor() {
        Random m = new Random();
        int attackThor = m.nextInt(2) + 1;
        System.out.println(attackThor);
        if (attackThor == 1) {
            isSleeping = true;
            System.out.println("Boss bashed");

        } else {
            isSleeping = false;
            System.out.println("Boss not bashed");

        }
    }


}