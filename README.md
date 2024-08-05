# SQL to Quickwit Query Converter

Bu proje, SQL sorgularını Quickwit sorgularına dönüştürmek için geliştirilmiş bir prototiptir. Proje, Java programlama dili ve Maven yapılandırma aracı kullanılarak geliştirilmiştir. Spring Boot framework ile yapılandırılmıştır.

## Özellikler

- SQL sorgularını parse eder.
- SQL `SELECT` sorgularını Quickwit sorgularına dönüştürür.
- `WHERE` koşullarını Quickwit formatına çevirir.

## Kullanılan Teknolojiler

- Java
- Maven
- Spring Boot
- JSQLParser

## Kurulum ve Çalıştırma

1. Projeyi klonlayın:
    ```sh
    git clone https://github.com/bersankilic/sql-to-quickwit-converter.git
    cd sql-to-quickwit-converter
    ```

2. Maven bağımlılıklarını yükleyin:
    ```sh
    mvn clean install
    ```

3. Uygulamayı çalıştırın:
    ```sh
    mvn spring-boot:run
    ```

## Kullanım

Uygulama, verilen SQL sorgusunu parse eder ve Quickwit sorgusuna dönüştürür. `SQLParser` sınıfı SQL sorgusunu parse ederken, `QuickwitQueryConverter` sınıfı bu sorguyu Quickwit formatına çevirir.

## Not

Bu proje tüm SQL sorgu türleri ve koşulları için tam destek sağlamaz.
