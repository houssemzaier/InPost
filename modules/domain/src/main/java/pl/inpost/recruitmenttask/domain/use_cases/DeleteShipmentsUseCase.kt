package pl.inpost.recruitmenttask.domain.use_cases

import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

class DeleteShipmentsUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
) {
    suspend operator fun invoke(shipmentId: String) {
        shipmentRepository.delete(shipmentId)
    }
}
