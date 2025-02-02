package no.svelterev.spill;

public class KortVerdi {
    private String kortVerdi;

    public String getKortVerdi() {
        return kortVerdi;
    }

    public void setKortVerdi(String kortVerdi) {
        this.kortVerdi = kortVerdi;
    }

    public static int hentKortVerdi(String kortVerdi) {
        switch (kortVerdi) {
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            default:
                throw new IllegalArgumentException("Ugyldig kortverdi: " + kortVerdi);
        }
    }
}