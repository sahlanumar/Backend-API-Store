# Aplikasi Kursus Online

Aplikasi ini adalah backend service untuk platform kursus online yang dibangun menggunakan Java dan Spring Boot.

## Fitur Utama

-   Autentikasi Pengguna (Register dan Login)
-   Manajemen Produk (Kursus)
-   Manajemen Pelanggan
-   Proses Transaksi
-   Perhitungan Pajak
-   Laporan dalam format PDF
-   Integrasi dengan Cloudinary untuk penyimpanan gambar
-   Integrasi dengan Midtrans sebagai gateway pembayaran

## Teknologi yang Digunakan

-   **Bahasa Pemrograman:** Java 17
-   **Framework:** Spring Boot 3
-   **Build Tool:** Maven
-   **Database:** PostgreSQL (diasumsikan, dapat disesuaikan di `application.properties`)
-   **Kontainerisasi:** Docker
-   **Lain-lain:**
    -   Spring Security (untuk autentikasi dan otorisasi)
    -   Spring Data JPA (untuk interaksi database)
    -   OpenAPI/Swagger (untuk dokumentasi API)
    -   Cloudinary (untuk manajemen file gambar)
    -   Midtrans (untuk proses pembayaran)

## Prasyarat

-   JDK 17 atau lebih tinggi
-   Maven 3.6 atau lebih tinggi
-   Docker dan Docker Compose (opsional, untuk menjalankan dengan kontainer)
-   PostgreSQL Server

## Instalasi dan Menjalankan Proyek

### 1. Clone Repository

```bash
git clone <URL_REPOSITORY_ANDA>
cd LiveCodeSpringboot
```

### 2. Konfigurasi Environment

Buat file `.env` di root direktori proyek dan salin isi dari `.env.example` (jika ada) atau tambahkan variabel berikut:

```env
# Konfigurasi Database
DB_URL=jdbc:postgresql://localhost:5432/nama_database_anda
DB_USERNAME=username_db_anda
DB_PASSWORD=password_db_anda

# Konfigurasi Cloudinary
CLOUDINARY_CLOUD_NAME=nama_cloud_anda
CLOUDINARY_API_KEY=api_key_anda
CLOUDINARY_API_SECRET=api_secret_anda

# Konfigurasi Midtrans
MIDTRANS_SERVER_KEY=server_key_anda
MIDTRANS_CLIENT_KEY=client_key_anda
```

Sesuaikan juga file `src/main/resources/application.properties` untuk menunjuk ke variabel environment di atas.

### 3. Menjalankan Aplikasi

#### Menggunakan Maven Wrapper:

```bash
# Untuk Windows
./mvnw spring-boot:run

# Untuk macOS/Linux
./mvnw spring-boot:run
```

#### Menggunakan Docker Compose:

Pastikan Docker sudah berjalan di sistem Anda.

```bash
docker-compose up --build
```

Aplikasi akan berjalan dan dapat diakses di `http://localhost:8080`.

## Dokumentasi API

Dokumentasi API tersedia menggunakan Swagger UI. Setelah aplikasi berjalan, buka URL berikut di browser Anda:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
