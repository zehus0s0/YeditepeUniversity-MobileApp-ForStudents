package com.example.myapplication.Views.ChatScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

data class ChatMessage(
    val id: String,
    val text: String,
    val senderId: String,
    val timestamp: String
)

class ChatViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages
    val currentUserId = Firebase.auth.currentUser?.uid
    var chatName: String = ""
        private set

    fun loadMessages(chatId: String) {
        // Önce chat bilgilerini al
        firestore.collection("chats").document(chatId).get()
            .addOnSuccessListener { document ->
                chatName = document.getString("name") ?: ""
            }

        // Mesajları dinle
        firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val messageList = snapshot?.documents?.mapNotNull { doc ->
                    val data = doc.data ?: return@mapNotNull null
                    val timestamp = data["timestamp"] as? Timestamp
                    ChatMessage(
                        id = doc.id,
                        text = data["text"] as? String ?: "",
                        senderId = data["senderId"] as? String ?: "",
                        timestamp = formatTimestamp(timestamp)
                    )
                } ?: emptyList()

                _messages.value = messageList
            }
    }

    fun sendMessage(chatId: String, text: String) {
        val message = hashMapOf(
            "text" to text,
            "senderId" to (currentUserId ?: return),
            "timestamp" to Timestamp.now()
        )

        firestore.collection("chats").document(chatId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener { messageRef ->
                // Son mesajı güncelle
                firestore.collection("chats").document(chatId)
                    .update(
                        mapOf(
                            "lastMessage" to text,
                            "lastMessageTimestamp" to Timestamp.now()
                        )
                    )

                // userChats koleksiyonunda da son mesaj zamanını güncelle
                val participants = listOf(currentUserId ?: return@addOnSuccessListener)
                participants.forEach { userId ->
                    firestore.collection("userChats")
                        .document(userId)
                        .collection("chats")
                        .document(chatId)
                        .update("lastMessageTimestamp", Timestamp.now())
                }
            }
    }

    private fun formatTimestamp(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(timestamp.toDate())
    }
}