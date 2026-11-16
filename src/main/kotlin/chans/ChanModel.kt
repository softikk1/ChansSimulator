package chans

import kotlinx.serialization.Serializable

@Serializable
data class ChanModel(
    val id: Long,
    val name: String,
    val age: Int,
    val height: Int,
    val tsundere: Boolean
)
