public class Sifreleme_Motoru {

    // Karmaşık f_k fonksiyonu (Round Function) [cite: 38]
    public static String fK_Fonksiyonu(String veri, String anahtar) {
        String sol = veri.substring(0, 4);
        String sag = veri.substring(4);

        // 1. Expansion Permutation (EP) uygula [cite: 38]
        String ep = S_DES_Araclar.permutasyonUygula(sag, S_DES_Tablolar.EP);
        
        // 2. Anahtar ile XOR'la [cite: 38]
        String xorSonuc = S_DES_Araclar.xorUygula(ep, anahtar);
        
        // 3. S-Box (S0 ve S1) dönüşümleri [cite: 38]
        String s0Giris = xorSonuc.substring(0, 4);
        String s1Giris = xorSonuc.substring(4);
        
        String s0Cikis = sBoxHesapla(s0Giris, S_DES_Tablolar.S0);
        String s1Cikis = sBoxHesapla(s1Giris, S_DES_Tablolar.S1);
        
        // 4. P4 Permütasyonu ve Sol blok ile XOR [cite: 38]
        String p4 = S_DES_Araclar.permutasyonUygula(s0Cikis + s1Cikis, S_DES_Tablolar.P4);
        String yeniSol = S_DES_Araclar.xorUygula(p4, sol);
        
        return yeniSol + sag;
    }

    private static String sBoxHesapla(String bitler, int[][] sBox) {
        // Satır: 1. ve 4. bit, Sütun: 2. ve 3. bit [cite: 22, 28]
        int satir = Integer.parseInt("" + bitler.charAt(0) + bitler.charAt(3), 2);
        int sutun = Integer.parseInt("" + bitler.charAt(1) + bitler.charAt(2), 2);
        
        int deger = sBox[satir][sutun];
        
        // Değeri her zaman 2 bit olacak şekilde döndür (0 -> 00, 1 -> 01) [cite: 13, 22]
        switch(deger) {
            case 0: return "00";
            case 1: return "01";
            case 2: return "10";
            case 3: return "11";
            default: return "00";
        }
    }
    // Şifreleme Ana Metodu [cite: 36, 37, 38, 39, 40, 41]
    public String sifrele(String plaintext, String k1, String k2) {
        // 1. Initial Permutation (IP) [cite: 37]
        String ip = S_DES_Araclar.permutasyonUygula(plaintext, S_DES_Tablolar.IP);
        System.out.println("IP Sonucu: " + ip);

        // 2. fK1 (Round 1) - K1 ile 
        String fk1 = fK_Fonksiyonu(ip, k1);
        System.out.println("fK1 Sonucu: " + fk1);

        // 3. SW (Switch) - Sol ve sağ blok yer değiştirir [cite: 39]
        // Sol 4 bit ile sağ 4 bitin yerini kesin olarak değiştiriyoruz.
        String sw = fk1.substring(4) + fk1.substring(0, 4);
        System.out.println("SW Sonucu: " + sw);

        // 4. fK2 (Round 2) - K2 ile [cite: 40]
        String fk2 = fK_Fonksiyonu(sw, k2);
        System.out.println("fK2 Sonucu: " + fk2);

        // 5. IP-1 (Inverse Initial Permutation) 
        String sonuc = S_DES_Araclar.permutasyonUygula(fk2, S_DES_Tablolar.IP_INV);
        return sonuc;
    }
 // Deşifreleme Metodu [cite: 45, 46]
    public String desifrele(String sifreliMetin, String k1, String k2) {
        // S-DES kuralı: Deşifreleme için şifreleme algoritması kullanılır,
        // ancak anahtarlar ters sırada (önce K2, sonra K1) verilir.
        return sifrele(sifreliMetin, k2, k1); 
    }
}