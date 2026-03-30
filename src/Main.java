import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Strom strom = new Strom();
        CastMise koren = new CastMise("Záchrana posádky", 0);
        CastMise pripravaRakety = new CastMise("Příprava rakety", 45);
        strom.add(koren, pripravaRakety);
        CastMise kontrolaPaliva = new CastMise("Kontrola paliva", 15);
        strom.add(pripravaRakety, kontrolaPaliva);
        CastMise kontrolaLetovychSystemu = new CastMise("Kontrola letových systémů", 20);
        strom.add(pripravaRakety, kontrolaLetovychSystemu);
        CastMise kalibraceNavigace = new CastMise("Kalibrace navigace", 10);
        strom.add(kontrolaLetovychSystemu, kalibraceNavigace);
        CastMise letKLodi = new CastMise("Let k lodi", 60);
        strom.add(koren, letKLodi);
        CastMise navazaniSpojeni = new CastMise("Navázání spojení", 5);
        strom.add(letKLodi, navazaniSpojeni);
        CastMise priblizeniKLodi = new CastMise("Přiblížení k lodi", 20);
        strom.add(letKLodi, priblizeniKLodi);;
        CastMise otevreniDokovacihoPortu = new CastMise("Otevření dokovacího portu", 8);
        strom.add(priblizeniKLodi, otevreniDokovacihoPortu);
        CastMise vyzvednutiPosadky = new CastMise("Vyzvednutí posádky", 12);
        strom.add(priblizeniKLodi, vyzvednutiPosadky);
        CastMise navratNaZemi = new CastMise("Návrat na Zemi", 90);
        strom.add(koren, navratNaZemi);
        CastMise stabilizaceLetuNaObezneDraze = new CastMise("Stabilizace letu na oběžné dráze", 15);
        strom.add(navratNaZemi, stabilizaceLetuNaObezneDraze);
        CastMise pristani = new CastMise("Přistání", 10);
        strom.add(navratNaZemi, pristani);

        Scanner sc = new Scanner(System.in);

        System.out.println("6. Vypiš celý strom s odsazením (1b)");
        strom.print();
        System.out.println("7. Spočítej počet všech uzlů (1b)");
        System.out.println(strom.countAllNodes());
        System.out.println("8. Spočítej počet listů (1b)");
        System.out.println(strom.countAllLeafs());
        System.out.println("9. Zjisti výšku stromu (1b)");
        System.out.println(strom.calculateDepth());
        System.out.println("10. Spočítej celkový čas všech částí mise (1b)");
        System.out.println(strom.celkovyCas());
        System.out.println("11. Najdi nejdelší část mise (2b)");
        System.out.println(strom.nejdelsiCastMise());
        System.out.println("12. Po zadání názvu části mise zjisti, zda jde o list (2b)");
        System.out.print("Název mise (musí být přesný): ");
        String nazevListu = sc.nextLine();
        System.out.println(strom.jdeOList(nazevListu));
        System.out.println("13. Po zadání názvu části mise vypiš cestu od kořene k této části (2b)");
        System.out.print("Název mise (musí být přesný): ");
        nazevListu = sc.nextLine();
        strom.vypisCestuKeKorenu(nazevListu);
        System.out.println();
        System.out.println("14. Zjisti, na které úrovni je nejvíce uzlů (3b)");
        // Tahle metoda vrátí úroveň uzlu, jež vlastní nejvíce dětí... ciže přičteme 1 pro úroveň s nejvíce uzly
        System.out.println(strom.urovenSNejviceUzly() + 1);
    }
}
