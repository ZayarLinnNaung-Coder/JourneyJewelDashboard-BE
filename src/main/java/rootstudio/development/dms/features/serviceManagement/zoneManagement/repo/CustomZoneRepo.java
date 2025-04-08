package rootstudio.development.dms.features.serviceManagement.zoneManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.ZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneDetailResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneResponse;
import rootstudio.development.dms.global.document.Zone;

public interface CustomZoneRepo {
    Page<ZoneResponse> getAllZones(String query, Pageable pageable);
    ZoneDetailResponse getZoneById(String id);
    Zone updateZone(String id, ZoneRequest request);
}
