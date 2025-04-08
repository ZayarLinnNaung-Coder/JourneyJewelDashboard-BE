package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.request.AllDeliveryServiceRequest;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.request.CreateDeliveryServiceRequest;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.request.UpdateDeliveryServiceRequest;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response.AllDeliveryServiceResponse;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response.CreateDeliveryServiceResponse;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response.DeliveryServiceDetailsResponse;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response.UpdateDeliveryServiceResponse;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.repo.CustomDeliveryRepo;
import rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.service.DeliveryManagementService;
import rootstudio.development.dms.global.document.DeliveryService;
import rootstudio.development.dms.global.document.Zone;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryManagementServiceImpl implements DeliveryManagementService {

    private final CustomDeliveryRepo deliveryRepo;
    private final MongoTemplate mongoTemplate;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public Page<AllDeliveryServiceResponse> getAllDeliveryService(AllDeliveryServiceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return deliveryRepo.getAllDeliveryService(request.getQuery(), pageable);
    }

    @Override
    public DeliveryServiceDetailsResponse getDeliveryServiceById(String id) {
        DeliveryService getDeliveryServiceById = deliveryRepo.getDeliveryServiceById(id);
        return DeliveryServiceDetailsResponse.of(getDeliveryServiceById);
    }

    @Override
    @Transactional
    public CreateDeliveryServiceResponse createDeliveryService(CreateDeliveryServiceRequest request) {
        Zone fromZone = mongoTemplate.findById(request.getFromZoneId(), Zone.class);
        Zone toZone = mongoTemplate.findById(request.getToZoneId(), Zone.class);

        String serviceId = generateServiceId();

        DeliveryService deliveryService = DeliveryService.builder()
                .name(request.getName())
                .serviceId(serviceId)
                .description(request.getDescription())
                .price(request.getPrice())
                .iconUrl(request.getIconUrl())
                .fromZone(fromZone)
                .toZone(toZone)
                .build();

        // Save and convert to response
        DeliveryService savedService = mongoTemplate.save(deliveryService);
        return CreateDeliveryServiceResponse.of(savedService);
    }

    private String generateServiceId() {
        long timestamp = Instant.now().toEpochMilli() % 100000;
        String randomChars = secureRandom.ints(6, 0, 36)
                .mapToObj(i -> i < 10 ? String.valueOf(i) : String.valueOf((char) (i + 55)))
                .collect(Collectors.joining());
        return String.format("SDR-%04d-%s", timestamp, randomChars);
    }

    @Override
    public UpdateDeliveryServiceResponse updateDeliveryService(String id, UpdateDeliveryServiceRequest request) {
        DeliveryService updateDeliveryService = deliveryRepo.updateDeliveryService(id, request);
        return UpdateDeliveryServiceResponse.of(updateDeliveryService);
    }
}
