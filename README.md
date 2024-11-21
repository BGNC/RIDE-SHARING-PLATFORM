# 🚗 Ride Sharing Platform 
## 📝 Proje Açıklaması

Bu proje, modern bir ride-sharing platformunun Spring Boot tabanlı backend uygulamasıdır. JWT tabanlı güvenlik yapısı ile güçlendirilmiş, sürücü ve yolcu rollerini destekleyen, gerçek zamanlı konum takibi ve sürücü puanlama özellikleri sunan kapsamlı bir API sağlamaktadır.

## ✨ Temel Özellikler

### 👤 Kullanıcı Yönetimi
- Çoklu rol desteği (Sürücü/Yolcu)
- JWT tabanlı kimlik doğrulama ve yetkilendirme
- Güvenli oturum yönetimi
- Kullanıcı profil yönetimi

### 🗺️ Konum ve Harita İşlemleri
- Sürücü konum takibi
- Yolculuk rota planlaması
- Mesafe hesaplamaları
- Gerçek zamanlı konum güncellemeleri

### ⭐ Değerlendirme Sistemi
- Sürücü puanlama mekanizması
- Yolcu yorumları
- Ortalama puan hesaplama
- Değerlendirme geçmişi

## 🛠️ Teknoloji Yığını

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Maven

### Frontend (Planlanan)
- React.js
- React Router
- Axios
- Google Maps API
- Material-UI/Tailwind CSS

### Geliştirme Araçları
- Docker
- Docker Compose
- Git

## 📁 Proje Yapısı
```
uber-clone-backend/
├── src/
│   ├── main/
│   │   ├── java/com/uber/clone/
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthenticationController.java
│   │   │   │   ├── DriverController.java
│   │   │   │   └── PassengerController.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── model/
│   │   │   │   └── User.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── CustomExceptions.java
│   │   │   ├── jwt/
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── JwtService.java
│   │   │   │   └── JwtUtils.java
│   │   │   ├── repository/
│   │   │   │   ├── DriverRepository.java
│   │   │   │   └── PassengerRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AuthenticationService.java
│   │   │   │   ├── DriverService.java
│   │   │   │   └── PassengerService.java
│   │   │   └── utils/
│   │   │       ├── Constants.java
│   │   │       ├── LocationUtils.java
│   │   │       └── ValidationUtils.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker/
│   └── docker-compose.yml
└── pom.xml
```

## 🚀 Kurulum

### Ön Gereksinimler
- JDK 17
- Maven
- Docker ve Docker Compose
- Git

### Backend Kurulum
1. Projeyi klonlayın
```bash
git clone https://github.com/BGNC/Uber-Clone-Backend.git
cd Uber-Clone-Backend
```

2. Docker ile PostgreSQL'i başlatın
```bash
docker-compose up -d
```

3. Uygulamayı derleyin ve çalıştırın
```bash
mvn clean install
mvn spring-boot:run
```

## 🔒 API Endpoints (Planlanan)

### Kimlik Doğrulama
- `POST /api/authenticate` - Kullanıcı kimlik doğrulama
- `POST /api/register` - Yeni kullanıcı kaydı

### Sürücü İşlemleri
- `GET /api/drivers` - Aktif sürücüleri listele
- `PUT /api/drivers/{id}/location` - Sürücü konumunu güncelle
- `GET /api/drivers/{id}/ratings` - Sürücü değerlendirmelerini getir

### Yolcu İşlemleri
- `POST /api/passengers/ride-request` - Yolculuk talebi oluştur
- `POST /api/passengers/rate-driver` - Sürücüyü değerlendir

## 🔐 JWT Yapısı

Proje, JWT (JSON Web Token) tabanlı kimlik doğrulama sistemi kullanmaktadır:

- `jwt` paketi altında token oluşturma, doğrulama ve işleme mekanizmaları
- Token tabanlı oturum yönetimi
- Role-based erişim kontrolü
- Token yenileme mekanizması

## 🛠️ Utility Sınıfları

`utils` paketi altında yer alan yardımcı sınıflar:

- `Constants`: Sistem genelinde kullanılan sabit değerler
- `LocationUtils`: Konum hesaplamaları ve mesafe ölçümleri
- `ValidationUtils`: Veri doğrulama işlemleri

## 📝 Yapılacaklar
- [ ] JWT refresh token implementasyonu
- [ ] Rol tabanlı yetkilendirme sistemi
- [ ] Gerçek zamanlı konum takip sistemi
- [ ] React frontend geliştirmesi
- [ ] Sürücü-yolcu eşleştirme algoritması
- [ ] Yolculuk ücret hesaplama sistemi
- [ ] Bildirim sistemi

## 🤝 Katkıda Bulunma
1. Bu depoyu forklayın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'feat: Add amazing feature'`)
4. Branch'inize push yapın (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 👨‍💻 Geliştirici
- [BGNC](https://github.com/BGNC)

## 📜 Lisans
Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakınız.

## ⚠️ Not
Bu proje eğitim ve geliştirme amaçlıdır. Ticari kullanım için gerekli yasal izinlerin alınması gerekmektedir.
