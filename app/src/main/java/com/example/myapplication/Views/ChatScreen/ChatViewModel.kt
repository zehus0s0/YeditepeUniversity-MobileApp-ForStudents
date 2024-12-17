package com.example.myapplication.Views.ChatScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.DataLayer.Models.ChatData
import com.example.myapplication.DataLayer.Models.MessageData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import java.lang.IllegalArgumentException

class ChatViewModelFactory(private val chatId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chatId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ChatViewModel(private val chatId: String) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _firestoreConnectionStatus = MutableLiveData("")
    val firestoreConnectionStatus: LiveData<String> = _firestoreConnectionStatus

    private val _messages = MutableLiveData<List<MessageData>>(emptyList())
    val messages: LiveData<List<MessageData>> = _messages

    init {
        getMessages(chatId)
    }

    /**
     * Update message value as user types
     */
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    /**
     * Update messages list
     */
    private fun updateMessages(list: List<MessageData>) {
        _messages.value = list.asReversed()
    }

    /**
     * Send message
     */
    fun addMessage() {
        val currentMessage: String = _message.value ?: throw IllegalArgumentException("Message is empty")
        if (currentMessage.isNotEmpty()) {
            val messageData = hashMapOf(
                "chatId" to chatId,         // Use the unique chatId
                "message" to currentMessage,
                "senderId" to Firebase.auth.currentUser?.uid,
                "timestamp" to Timestamp.now()
            )

            firestore.collection("chats")
                .document(chatId)          // Use the unique chatId
                .collection("messages")
                .add(messageData)
                .addOnSuccessListener {
                    _message.value = ""
                    Log.d("ChatViewModel", "Message sent successfully.")
                }
                .addOnFailureListener { e ->
                    Log.w("ChatViewModel", "Error sending message", e)
                }
        }
    }

    /**
     * Get messages from Firestore
     */
    private fun getMessages(chatId: String) {
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("ChatViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val list = mutableListOf<MessageData>()

                if (value != null) {
                    for (doc in value) {
                        val data = doc.data
                        val messageData = mapToMessageData(doc.id, data)
                        list.add(messageData)
                    }
                }
                updateMessages(list)
            }
    }

    /**
     * Map Firestore document data to ChatData
     */
    fun mapToChatData(documentId: String, data: Map<String, Any>): ChatData {
        return ChatData(
            chatId = documentId,
            chatName = data["chatName"] as? String ?: "",
            chatType = data["chatType"] as? String ?: "",
            lastMessage = data["lastMessage"] as? String ?: "",
            lastMessageTimestamp = data["lastMessageTimestamp"] as? Timestamp,
            participants = data["participants"] as? List<String> ?: emptyList()
        )
    }

    /**
     * Map Firestore document data to MessageData
     */
    fun mapToMessageData(documentId: String, data: Map<String, Any>): MessageData {
        return MessageData(
            messageId = documentId,
            chatId = data["chatId"] as? String ?: "",
            senderId = data["senderId"] as? String ?: "",
            message = data["message"] as? String ?: "",
            timestamp = data["timestamp"] as? Timestamp
        )
    }

    /**
     * Test Firestore connection
     */
    fun testFirestoreReadConnection() {
        FirebaseFirestore.getInstance().collection("chats")
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    _firestoreConnectionStatus.value = "Firestore connected successfully"
                } else {
                    _firestoreConnectionStatus.value = "No data found"
                }
            }
            .addOnFailureListener { exception ->
                _firestoreConnectionStatus.value = "Firestore connection failed: ${exception.message}"
            }
    }
}