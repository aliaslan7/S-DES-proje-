public class Anahtar_Uretimi {
    private String k1;
    private String k2;

    public void anahtarlariOlustur(String anahtar10Bit) {
        // 1. P10 Permütasyonu Uygula [cite: 32]
        String p10 = S_DES_Araclar.permutasyonUygula(anahtar10Bit, S_DES_Tablolar.P10);
        
        // 2. Bloğu ikiye böl (5 bit sol, 5 bit sağ)
        String sol = p10.substring(0, 5);
        String sag = p10.substring(5);

        // 3. LS-1 (1 bit sola kaydır) ve K1 üret [cite: 33, 34]
        sol = S_DES_Araclar.solaKaydir(sol, 1);
        sag = S_DES_Araclar.solaKaydir(sag, 1);
        this.k1 = S_DES_Araclar.permutasyonUygula(sol + sag, S_DES_Tablolar.P8);

        // 4. LS-2 (önceki halinden 2 bit daha kaydır) ve K2 üret [cite: 33, 34]
        sol = S_DES_Araclar.solaKaydir(sol, 2);
        sag = S_DES_Araclar.solaKaydir(sag, 2);
        this.k2 = S_DES_Araclar.permutasyonUygula(sol + sag, S_DES_Tablolar.P8);
    }

    public String getK1() { return k1; }
    public String getK2() { return k2; }
}