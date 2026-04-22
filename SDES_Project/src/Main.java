import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Anahtar_Uretimi au = new Anahtar_Uretimi();
        Sifreleme_Motoru sm = new Sifreleme_Motoru();

        while (true) {
            System.out.println("\n========================================");
            System.out.println("   S-DES ARAYÜZ    ");
            System.out.println("========================================");
            System.out.println("1. Metin Şifrele (Encryption)");
            System.out.println("2. Metin Deşifre Et (Decryption)");
            System.out.println("3. Kaba Kuvvet Saldırısı (Brute-Force)");
            System.out.println("4. Çıkış");
            System.out.print("\nSeçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine(); // Boşluk temizle

            if (secim == 4) break;

            switch (secim) {
                case 1:
                    System.out.print("8-bit Düz Metin (Örn: 10101010): ");
                    String pt = scanner.nextLine();
                    System.out.print("10-bit Anahtar (Örn: 0111111101): ");
                    String key = scanner.nextLine();
                    
                    au.anahtarlariOlustur(key);
                    System.out.println("\n--- İşlem Adımları Başlatıldı ---");
                    String ct = sm.sifrele(pt, au.getK1(), au.getK2());
                    System.out.println("\n>>> SONUÇ (Şifreli Metin): " + ct);
                    break;

                case 2:
                    System.out.print("8-bit Şifreli Metin: ");
                    String ctIn = scanner.nextLine();
                    System.out.print("10-bit Anahtar: ");
                    String keyIn = scanner.nextLine();
                    
                    au.anahtarlariOlustur(keyIn);
                    String dt = sm.desifrele(ctIn, au.getK1(), au.getK2());
                    System.out.println("\n>>> SONUÇ (Orijinal Metin): " + dt);
                    break;

                case 3:
                    System.out.print("Bilinen Düz Metin: ");
                    String bpt = scanner.nextLine();
                    System.out.print("Bilinen Şifreli Metin: ");
                    String bct = scanner.nextLine();
                    Guvenlik_Analizi.kabaKuvvetSaldirisi(bpt, bct);
                    break;

                default:
                    System.out.println("Geçersiz seçim!");
            }
            
            System.out.println("\nDevam etmek için ENTER'a basın...");
            scanner.nextLine();
        }
        System.out.println("Program kapatıldı.");
    }
}