# Gunakan image resmi OpenJDK sebagai base image
FROM openjdk:21-jdk-slim

# Set direktori kerja di dalam container
WORKDIR /app

# Copy seluruh file proyek ke dalam container
COPY . .

# Compile dan build aplikasi (jika menggunakan Maven)
RUN ./mvnw package -DskipTests

# Expose port aplikasi (sesuaikan dengan konfigurasi aplikasi)
EXPOSE 8080

# Jalankan aplikasi
CMD ["java", "-jar", "target/LiceCodeSpringboot-0.0.1-SNAPSHOT.jar"]