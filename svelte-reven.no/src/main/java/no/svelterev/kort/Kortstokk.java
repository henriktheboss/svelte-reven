package no.svelterev.kort;

import java.util.List;

public class Kortstokk {

    private List<Kort> kortstokk;

    public Kortstokk(List<Kort> kortstokk) {
        this.kortstokk = kortstokk;
    }

    public Kort trekkKort() {
        if (kortstokk.isEmpty())
            throw new RuntimeException("Kortstokken er tom.");

        return kortstokk.removeFirst();
    }

    public List<Kort> getKortstokk() {
        return kortstokk;
    }

    public void setKortstokk(List<Kort> kortstokk) {
        this.kortstokk = kortstokk;
    }
}
