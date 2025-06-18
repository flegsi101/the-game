package de.kugma.the_game.common

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
class PersistedState<T>(
    val file: File,
    val state: MutableStateFlow<T>
) {
    fun asStateFlow(): StateFlow<T> {
        return state.asStateFlow()
    }
}

inline fun <reified T> PersistedState<T>.update(function: (T) -> T) {
    val updated = function(state.value)
    this.file.writeText(Json.encodeToString(updated))
    this.state.update { updated }
}

inline fun <reified T> createPersistedState(
    fileName: String,
    initial: T
): PersistedState<T> {
    val file = Storage.file(fileName)
    val json = file.readText(Charsets.UTF_8)
    val value = if (json.isNotEmpty()) Json.decodeFromString<T>(json) else initial

    return PersistedState(file, MutableStateFlow(value))
}

