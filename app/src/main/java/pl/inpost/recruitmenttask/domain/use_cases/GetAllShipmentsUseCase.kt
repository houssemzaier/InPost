package pl.inpost.recruitmenttask.domain.use_cases

import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

class GetAllShipmentsUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
) {
    suspend operator fun invoke() = shipmentRepository.getAllShipments()
}
