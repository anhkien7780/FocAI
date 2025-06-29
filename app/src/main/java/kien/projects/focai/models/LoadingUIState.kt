package kien.projects.focai.models

sealed class LoadingUIState {
    object Loading: LoadingUIState()
    object Error: LoadingUIState()
    object Success: LoadingUIState()
    object Idle: LoadingUIState()
}