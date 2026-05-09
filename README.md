# S-DES-proje-

Bu proje, **Simplified Data Encryption Standard (S-DES)** algoritmasının uçtan uca tasarımını ve güvenlik analizini içerir.

## Özellikler

- **Tam Gerçekleme:** 10-bit anahtar ve 8-bit blok yapısı ile şifreleme/deşifreleme.
- **Adım Adım İzleme:** Konsoldan IP, fK ve SW ara adımlarını izleme.
- **Brute-Force Analizi:** 1024 olası anahtarı tarayarak aday anahtarları bulma.
- **Modüler OOP:** `S_DES_Motoru`, `Anahtar_Uretimi`, `S_DES_Tablolar` sınıfları.

## Kullanım

```bash
python main.py sifrele --anahtar 1010000010 --metin 11010111 --izleme
python main.py desifrele --anahtar 1010000010 --metin 10101000 --izleme
python main.py brute-force --acik 11010111 --sifreli 10101000
```

## Test

```bash
python -m unittest discover -s tests -p "test_*.py"
```
