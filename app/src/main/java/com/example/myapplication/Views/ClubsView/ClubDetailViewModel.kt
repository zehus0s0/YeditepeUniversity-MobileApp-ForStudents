package com.example.myapplication.Views.ClubsView

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubDetailUiState(
    val clubName: String = "",
    val clubDescription: String = "",
    val clubIcon: String = "",
    val memberCount: Int = 0
)

class ClubDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ClubDetailUiState())
    val uiState: StateFlow<ClubDetailUiState> = _uiState.asStateFlow()

    fun updateClubDetails(
        clubName: String,
        clubDescription: String,
        clubIcon: String,
        memberCount: Int
    ) {
        _uiState.value = ClubDetailUiState(
            clubName = clubName,
            clubDescription = clubDescription,
            clubIcon = clubIcon,
            memberCount = memberCount
        )
    }
} 