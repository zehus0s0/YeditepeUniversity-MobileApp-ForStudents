package com.example.myapplication.Views.ReviewScreen

data class Review(
    val id: Int,                // Yorumun benzersiz ID'si
    val reviewerName: String,   // Yorumu yapan kişinin adı
    val content: String,        // Yorumun içeriği
    val rating: Int             // 1-5 arasında bir puan
)

data class Teacher(
    val id: Int,
    val name: String,
    val rating: Float,
    val photo: Int, // Bu satır fotoğraf için

)
data class Course(
    val id: Int,
    val code: String,         // Dersin kodu (örneğin: VCD 471)
    val name: String,         // Dersin adı
    val instructor: String,   // Dersi veren öğretmen
    val rating: Float         // 1-5 arasında bir puan
)
