import unittest

from s_des import Anahtar_Uretimi, KabaKuvvet_Saldirisi, S_DES_Motoru


class SDESCoreTests(unittest.TestCase):
    def test_alt_anahtar_uretimi(self):
        k1, k2 = Anahtar_Uretimi("1010000010").alt_anahtarlar()
        self.assertEqual(k1, "10100100")
        self.assertEqual(k2, "01000011")

    def test_sifreleme_desifreleme_tam_dongu(self):
        motor = S_DES_Motoru("1010000010")
        sifreli = motor.sifrele("11010111")
        self.assertEqual(sifreli, "10101000")
        self.assertEqual(motor.desifrele(sifreli), "11010111")

    def test_kaba_kuvvet_anahtar_bulur(self):
        acik = "11010111"
        gercek_anahtar = "1010000010"
        sifreli = S_DES_Motoru(gercek_anahtar).sifrele(acik)
        sonuc = KabaKuvvet_Saldirisi.anahtar_bul(acik, sifreli)
        self.assertIn(gercek_anahtar, sonuc["anahtarlar"])
        self.assertLessEqual(len(sonuc["anahtarlar"]), 1024)
        self.assertGreaterEqual(sonuc["sure_ms"], 0.0)


if __name__ == "__main__":
    unittest.main()
