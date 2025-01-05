package com.example.myapplication.DataLayer.Models

import com.google.firebase.Timestamp

// ChatData: Ana sohbet dokümanını temsil eder
data class ChatData(
    val chatId: String,                 
    val chatName: String = "",          // Özel sohbetlerde karşı kullanıcının adı, grup sohbetlerinde grup adı
    val chatType: String = "",          // PRIVATE veya GROUP
    val lastMessage: String = "",
    val lastMessageTimestamp: Timestamp? = null,
    val participants: List<String> = emptyList(),
    val participantNames: Map<String, String> = emptyMap(), // Katılımcı ID'leri ve isimleri
    val chatImageUrl: String = ""       // Profil veya grup resmi
)

// MessageData: Bir mesajı temsil eder
data class MessageData(
    val messageId: String = "",              // Firestore'daki message document ID
    val chatId: String = "",                 // Bağlı olduğu chatId
    val senderId: String = "",
    val message: String = "",
    val timestamp: Timestamp? = null
)

