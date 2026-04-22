from __future__ import annotations

from dataclasses import dataclass
from time import perf_counter


class S_DES_Tablolar:
    P10 = [3, 5, 2, 7, 4, 10, 1, 9, 8, 6]
    P8 = [6, 3, 7, 4, 8, 5, 10, 9]
    IP = [2, 6, 3, 1, 4, 8, 5, 7]
    IP_TERS = [4, 1, 3, 5, 7, 2, 8, 6]
    EP = [4, 1, 2, 3, 2, 3, 4, 1]
    P4 = [2, 4, 3, 1]
    S0 = [
        [1, 0, 3, 2],
        [3, 2, 1, 0],
        [0, 2, 1, 3],
        [3, 1, 3, 2],
    ]
    S1 = [
        [0, 1, 2, 3],
        [2, 0, 1, 3],
        [3, 0, 1, 0],
        [2, 1, 0, 3],
    ]


def _permute(bitler: str, tablo: list[int]) -> str:
    return "".join(bitler[i - 1] for i in tablo)


def _xor(sol: str, sag: str) -> str:
    return "".join("1" if a != b else "0" for a, b in zip(sol, sag))


def _sola_kaydir(bitler: str, miktar: int) -> str:
    return bitler[miktar:] + bitler[:miktar]


def _s_kutusu(girdi: str, kutu: list[list[int]]) -> str:
    satir = int(girdi[0] + girdi[3], 2)
    sutun = int(girdi[1] + girdi[2], 2)
    return f"{kutu[satir][sutun]:02b}"


def _bit_dogrula(bitler: str, beklenen_uzunluk: int, alan_adi: str) -> None:
    if len(bitler) != beklenen_uzunluk or any(ch not in {"0", "1"} for ch in bitler):
        raise ValueError(f"{alan_adi} {beklenen_uzunluk}-bit ikili dize olmalıdır.")


@dataclass(frozen=True)
class Anahtar_Uretimi:
    anahtar_10bit: str

    def alt_anahtarlar(self) -> tuple[str, str]:
        _bit_dogrula(self.anahtar_10bit, 10, "Anahtar")
        p10 = _permute(self.anahtar_10bit, S_DES_Tablolar.P10)
        sol, sag = p10[:5], p10[5:]

        sol1, sag1 = _sola_kaydir(sol, 1), _sola_kaydir(sag, 1)
        k1 = _permute(sol1 + sag1, S_DES_Tablolar.P8)

        sol2, sag2 = _sola_kaydir(sol1, 2), _sola_kaydir(sag1, 2)
        k2 = _permute(sol2 + sag2, S_DES_Tablolar.P8)
        return k1, k2


class S_DES_Motoru:
    def __init__(self, anahtar_10bit: str):
        self.anahtar_10bit = anahtar_10bit
        self.k1, self.k2 = Anahtar_Uretimi(anahtar_10bit).alt_anahtarlar()

    def _fk(self, bitler_8: str, alt_anahtar: str) -> str:
        sol, sag = bitler_8[:4], bitler_8[4:]
        genislet = _permute(sag, S_DES_Tablolar.EP)
        karisim = _xor(genislet, alt_anahtar)
        s0 = _s_kutusu(karisim[:4], S_DES_Tablolar.S0)
        s1 = _s_kutusu(karisim[4:], S_DES_Tablolar.S1)
        p4 = _permute(s0 + s1, S_DES_Tablolar.P4)
        yeni_sol = _xor(sol, p4)
        return yeni_sol + sag

    def sifrele(self, acik_metin_8bit: str, izleme: bool = False) -> str:
        _bit_dogrula(acik_metin_8bit, 8, "Açık metin")
        ip = _permute(acik_metin_8bit, S_DES_Tablolar.IP)
        fk1 = self._fk(ip, self.k1)
        sw = fk1[4:] + fk1[:4]
        fk2 = self._fk(sw, self.k2)
        sifreli = _permute(fk2, S_DES_Tablolar.IP_TERS)

        if izleme:
            print(f"IP   : {ip}")
            print(f"fK1  : {fk1}")
            print(f"SW   : {sw}")
            print(f"fK2  : {fk2}")
            print(f"IP^-1: {sifreli}")
        return sifreli

    def desifrele(self, sifreli_metin_8bit: str, izleme: bool = False) -> str:
        _bit_dogrula(sifreli_metin_8bit, 8, "Şifreli metin")
        ip = _permute(sifreli_metin_8bit, S_DES_Tablolar.IP)
        fk1 = self._fk(ip, self.k2)
        sw = fk1[4:] + fk1[:4]
        fk2 = self._fk(sw, self.k1)
        acik = _permute(fk2, S_DES_Tablolar.IP_TERS)

        if izleme:
            print(f"IP   : {ip}")
            print(f"fK1  : {fk1}")
            print(f"SW   : {sw}")
            print(f"fK2  : {fk2}")
            print(f"IP^-1: {acik}")
        return acik


class KabaKuvvet_Saldirisi:
    @staticmethod
    def anahtar_bul(acik_metin_8bit: str, sifreli_metin_8bit: str) -> dict[str, object]:
        _bit_dogrula(acik_metin_8bit, 8, "Açık metin")
        _bit_dogrula(sifreli_metin_8bit, 8, "Şifreli metin")

        baslangic = perf_counter()
        adaylar: list[str] = []
        for i in range(1024):
            anahtar = f"{i:010b}"
            motor = S_DES_Motoru(anahtar)
            if motor.sifrele(acik_metin_8bit) == sifreli_metin_8bit:
                adaylar.append(anahtar)
        sure_ms = (perf_counter() - baslangic) * 1000
        return {"anahtarlar": adaylar, "sure_ms": round(sure_ms, 3)}
