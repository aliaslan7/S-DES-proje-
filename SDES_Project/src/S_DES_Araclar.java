public class S_DES_Araclar {

    /**
     * Genel Permütasyon Metodu
     * Verilen bit dizisini, belirtilen tabloya göre yeniden düzenler. [cite: 28, 32, 34]
     */
    public static String permutasyonUygula(String girdi, int[] tablo) {
        StringBuilder cikti = new StringBuilder();
        for (int pozisyon : tablo) {
            // Tablodaki değerler 1 tabanlı olduğu için (position - 1) kullanıyoruz.
            cikti.append(girdi.charAt(pozisyon - 1));
        }
        return cikti.toString();
    }

    /**
     * Dairesel Sola Kaydırma (Circular Left Shift)
     * Anahtar üretimi sırasında bitleri belirtilen adım kadar sola kaydırır. 
     */
    public static String solaKaydir(String girdi, int adim) {
        // Girdinin başından 'adim' kadar alıp sona ekleyerek dairesel kaydırma yapar.
        return girdi.substring(adim) + girdi.substring(0, adim);
    }

    /**
     * XOR İşlemi
     * İki bit dizisini (String) XOR işlemine tabi tutar. [cite: 38]
     */
    public static String xorUygula(String s1, String s2) {
        StringBuilder sonuc = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            // Char değerlerini sayıya çevirip XOR uyguluyoruz: (0^0=0, 0^1=1, 1^1=0)
            int bit1 = Character.getNumericValue(s1.charAt(i));
            int bit2 = Character.getNumericValue(s2.charAt(i));
            sonuc.append(bit1 ^ bit2);
        }
        return sonuc.toString();
    }
}