package com.example.myapplication.Views.ChatScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.myapplication.Utilities.Constants
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The home view which will contain all the code related to the view for HOME.
 *
 * Here we will show the list of chat messages sent by user.
 * And also give an option to send a message and logout.
 */

@Composable
fun ChatView(
    chatViewModel: ChatViewModel = viewModel()
) {
    val message: String by chatViewModel.message.observeAsState(initial = "")
    val messages: List<Map<String, Any>> by chatViewModel.messages.observeAsState(
        initial = emptyList<Map<String, Any>>().toMutableList()
    )
    val firestoreConnectionStatus: String by chatViewModel.firestoreConnectionStatus.observeAsState(initial = "")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display Firestore connection status
        Text(
            text = "Firestore Connection Status: $firestoreConnectionStatus",
            modifier = Modifier.padding(8.dp),
        )

        // Message List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.85f, fill = true),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            reverseLayout = true
        ) {
            items(messages) { message ->
                val isCurrentUser = message[Constants.IS_CURRENT_USER] as Boolean

                SingleMessage(
                    message = message[Constants.MESSAGE].toString(),
                    isCurrentUser = isCurrentUser
                )
            }
        }

        // Message Input
        OutlinedTextField(
            value = message,
            onValueChange = {
                chatViewModel.updateMessage(it)
            },
            label = {
                Text("Type Your Message")
            },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 1.dp)
                .fillMaxWidth()
                .weight(weight = 0.09f, fill = true),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        chatViewModel.addMessage()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Button"
                    )
                }
            }
        )

        // Test Firestore Connection Button
        Button(
            onClick = {
                chatViewModel.testFirestoreReadConnection()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Test Firestore Connection")
        }
    }
}

@Composable
fun SingleMessage(message: String, isCurrentUser: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = if (isCurrentUser) Constants.hubGreen else Constants.hubDarkBlue
                )
                .padding(8.dp),
            color = Constants.hubWhite
        )
    }
}


