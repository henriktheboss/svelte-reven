package no.svelterev;

import no.svelterev.api.httpapi;
import no.svelterev.kort.Kort;
import no.svelterev.kort.Kortstokk;
import no.svelterev.spiller.Spiller;
import no.svelterev.spill.KortVerdi;

import java.util.ArrayList;
import java.util.List;

public class App {
    void main() {
        List<Kort> kortliste = httpapi.getList(Kort.class, "https://sveltethefox.ekstern.dev.nav.no/shuffle");

        System.out.println(kortliste.size());
        System.out.println(kortliste);

        Kortstokk kortstokk = new Kortstokk(kortliste);

        List<Kort> spiller1Kort = new ArrayList<>();
        List<Kort> spiller2Kort = new ArrayList<>();

        int i = 0;
        for (Kort kort : kortstokk.getKortstokk()) {
            if (i++ % 2 == 0) {
                spiller1Kort.add(kort);
            } else {
                spiller2Kort.add(kort);
            }
        }

        Spiller spiller1 = new Spiller(spiller1Kort);
        Spiller spiller2 = new Spiller(spiller2Kort);

        System.out.println("Spiller 1 hand: " + spiller1.hand());
        System.out.println("Spiller 2 hand: " + spiller2.hand());

        krig(spiller1, spiller2);
    }

    public void krig(Spiller spiller1, Spiller spiller2) {
        while (!spiller1.hand().isEmpty() && !spiller2.hand().isEmpty()) {
            Kort spiller1Kort = spiller1.hand().remove(0);
            Kort spiller2Kort = spiller2.hand().remove(0);

            int comparison = compareCards(spiller1Kort, spiller2Kort);

            if (comparison > 0) {
                System.out.println("Spiller 1 vinner runden! " + spiller1Kort);
                spiller1.hand().add(spiller1Kort);
                spiller1.hand().add(spiller2Kort);
                System.out.println(spiller1.hand().size());
            } else if (comparison < 0) {
                System.out.println("Spiller 2 vinner runden! " + spiller2Kort);
                spiller2.hand().add(spiller1Kort);
                spiller2.hand().add(spiller2Kort);
                System.out.println(spiller2.hand().size());
            } else {
                System.out.println("Krig!");

                if (spiller1.hand().size() < 3 || spiller2.hand().size() < 3) {
                    if (spiller1.hand().size() < 3) {
                        System.out.println("Spiller 1 har ikke nok kort til å fortsette krigen.");
                        System.out.println("Spiller 2 wins!");
                        break;
                    } else {
                        System.out.println("Spiller 2 har ikke nok kort til å fortsette krigen.");
                        System.out.println("Spiller 1 wins!");
                        break;
                    }
                }

                List<Kort> spiller1KrigKort = new ArrayList<>();
                List<Kort> spiller2KrigKort = new ArrayList<>();

                for (int i = 0; i < 3; i++) {
                    spiller1KrigKort.add(spiller1.hand().remove(0));
                    spiller2KrigKort.add(spiller2.hand().remove(0));
                }

                Kort spiller1SisteKort = spiller1KrigKort.get(2);
                Kort spiller2SisteKort = spiller2KrigKort.get(2);

                int krigComparison = compareCards(spiller1SisteKort, spiller2SisteKort);

                if (krigComparison > 0) {
                    System.out.println("Spiller 1 vinner krigen! " + spiller1SisteKort);
                    spiller1.hand().addAll(spiller1KrigKort);
                    spiller1.hand().addAll(spiller2KrigKort);
                    spiller1.hand().add(spiller1Kort);
                    spiller1.hand().add(spiller2Kort);
                } else if (krigComparison < 0) {
                    System.out.println("Spiller 2 vinner krigen! " + spiller2SisteKort);
                    spiller2.hand().addAll(spiller1KrigKort);
                    spiller2.hand().addAll(spiller2KrigKort);
                    spiller2.hand().add(spiller1Kort);
                    spiller2.hand().add(spiller2Kort);
                } else {
                    System.out.println("Krig igjen!");


                }
            }
        }

        if (spiller1.hand().isEmpty()) {
            System.out.println("Spiller 2 wins!");


        } else {
            System.out.println("Spiller 1 wins!");

        }
    }

    private int compareCards(Kort kort1, Kort kort2) {
        int value1 = KortVerdi.hentKortVerdi(kort1.value());
        int value2 = KortVerdi.hentKortVerdi(kort2.value());
        return Integer.compare(value1, value2);
    }
}