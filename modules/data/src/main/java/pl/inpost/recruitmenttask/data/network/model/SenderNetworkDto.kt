package pl.inpost.recruitmenttask.data.network.model

import pl.inpost.recruitmenttask.domain.model.Sender

data class SenderNetworkDto(
    val email: String?,
    val phoneNumber: String?,
    val name: String?
) {
    companion object {
        fun SenderNetworkDto.toDomain(): Sender {
            return Sender(
                email = email,
                phoneNumber = phoneNumber,
                name = name,
            )
        }
    }
}
