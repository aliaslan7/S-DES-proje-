import argparse

from s_des import KabaKuvvet_Saldirisi, S_DES_Motoru


def _arg_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="S-DES şifreleme/deşifreleme ve brute-force aracı")
    alt = parser.add_subparsers(dest="komut", required=True)

    sifrele = alt.add_parser("sifrele", help="8-bit açık metni 10-bit anahtar ile şifrele")
    sifrele.add_argument("--anahtar", required=True, help="10-bit anahtar")
    sifrele.add_argument("--metin", required=True, help="8-bit açık metin")
    sifrele.add_argument("--izleme", action="store_true", help="IP/fK/SW adımlarını yazdır")

    desifrele = alt.add_parser("desifrele", help="8-bit şifreli metni 10-bit anahtar ile deşifrele")
    desifrele.add_argument("--anahtar", required=True, help="10-bit anahtar")
    desifrele.add_argument("--metin", required=True, help="8-bit şifreli metin")
    desifrele.add_argument("--izleme", action="store_true", help="IP/fK/SW adımlarını yazdır")

    brute = alt.add_parser("brute-force", help="1024 anahtarı tarayarak olası anahtarları bul")
    brute.add_argument("--acik", required=True, help="8-bit açık metin")
    brute.add_argument("--sifreli", required=True, help="8-bit şifreli metin")

    return parser


def main() -> None:
    args = _arg_parser().parse_args()
    if args.komut == "sifrele":
        print(S_DES_Motoru(args.anahtar).sifrele(args.metin, izleme=args.izleme))
        return
    if args.komut == "desifrele":
        print(S_DES_Motoru(args.anahtar).desifrele(args.metin, izleme=args.izleme))
        return

    sonuc = KabaKuvvet_Saldirisi.anahtar_bul(args.acik, args.sifreli)
    anahtarlar = ", ".join(sonuc["anahtarlar"]) if sonuc["anahtarlar"] else "bulunamadı"
    print(f"Aday anahtar(lar): {anahtarlar}")
    print(f"Süre (ms): {sonuc['sure_ms']}")


if __name__ == "__main__":
    main()
