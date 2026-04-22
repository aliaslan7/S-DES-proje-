
public class Guvenlik_Analizi {

    public static void kabaKuvvetSaldirisi(String bilinenMetin, String bilinenSifreli) {
        System.out.println("\n--- Kaba Kuvvet (Brute-Force) Saldırısı Başlatıldı ---");
        long baslangicZamani = System.currentTimeMillis();
        
        Anahtar_Uretimi au = new Anahtar_Uretimi();
        Sifreleme_Motoru sm = new Sifreleme_Motoru();
        
        boolean anahtarBulundu = false;

        // 1024 olasılığı tara (0'dan 1023'e kadar) 
        for (int i = 0; i < 1024; i++) {
            // Sayıyı 10 bitlik stringe çevir (Örn: 5 -> "0000000101")
            String denemeAnahtari = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
            
            au.anahtarlariOlustur(denemeAnahtari);
            String sonuc = sm.sifrele(bilinenMetin, au.getK1(), au.getK2());
            
            if (sonuc.equals(bilinenSifreli)) {
                long bitisZamani = System.currentTimeMillis();
                System.out.println("ANAHTAR BULUNDU: " + denemeAnahtari);
                System.out.println("Geçen Süre: " + (bitisZamani - baslangicZamani) + " ms");
                anahtarBulundu = true;
                break; 
            }
        }

        if (!anahtarBulundu) {
            System.out.println("Anahtar bulunamadı.");
        }
    }
}