package com.example.myapplication.Views.ClubsView

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubModel(
    val clubId: String,
    val clubName: String,
    val clubIcon: String // Icon resource name or URL
)

enum class ClubTab {
    MY_CLUBS,
    ALL_CLUBS
}

class ClubsViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(ClubTab.MY_CLUBS)
    val selectedTab: StateFlow<ClubTab> = _selectedTab.asStateFlow()

    private val _clubs = MutableStateFlow<List<ClubModel>>(
        listOf(
            ClubModel("1", "Cinema Club", "cinema_icon"),
            ClubModel("2", "Theatre Club", "theatre_icon"),
            ClubModel("3", "Music Club", "music_icon")
        )
    )
    val clubs: StateFlow<List<ClubModel>> = _clubs.asStateFlow()

    fun onTabSelected(tab: ClubTab) {
        _selectedTab.value = tab
        // Burada gerçek bir uygulamada seçilen taba göre kulüpleri filtreleyebilirsiniz
    }
} 