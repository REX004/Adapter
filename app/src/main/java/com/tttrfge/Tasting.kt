//package com.tttrfge
//import com.tttrfge.ViewModel.CharacterListViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//class Tasting {
//
//
//    suspend fun loadCharactersCoroutine(viewModel: CharacterListViewModel, currentPage: Int): List<Character> {
//        return withContext(Dispatchers.IO) {
//            viewModel.loadCharacters(currentPage)
//            viewModel.loadingLiveData.postValue(true) // Показать прогресс-бар
//
//            return@withContext suspendCancellableCoroutine { continuation ->
//                viewModel.charactersLiveData.observeForever { characters ->
//                    if (characters.isNotEmpty()) {
//                        continuation.resume(characters)
//                    } else {
//                        continuation.resume(emptyList())
//                    }
//                }
//
//                viewModel.errorMessageLiveData.observeForever { errorMessage ->
//                    continuation.resumeWithException(Exception(errorMessage))
//                }
//
//                continuation.invokeOnCancellation {
//                    viewModel.charactersLiveData.removeObservers(this)
//                    viewModel.errorMessageLiveData.removeObservers(this)
//                }
//            }
//        }
//    }
//
//    private fun loadCharacters() {
//        GlobalScope.launch {
//            try {
//                val characters = loadCharactersCoroutine(viewModel, currentPage)
//                if (characters.isNotEmpty()) {
//                    characterAdapter.updateCharacters(characters)
//                    currentPage++
//                } else {
//                    showError("No characters found")
//                }
//            } catch (e: Exception) {
//                showError(e.message ?: "Unknown error")
//            } finally {
//                viewModel.loadingLiveData.postValue(false) // Скрыть прогресс-бар
//            }
//        }
//    }
//
//}