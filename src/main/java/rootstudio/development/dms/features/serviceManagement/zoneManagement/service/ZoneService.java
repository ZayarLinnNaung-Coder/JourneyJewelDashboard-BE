package rootstudio.development.dms.features.serviceManagement.zoneManagement.service;

import org.springframework.data.domain.Page;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.AllZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.CreateZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.ZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CreateZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.UpdateZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneDetailResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneResponse;

public interface ZoneService {
    CreateZoneResponse createZone(CreateZoneRequest request);
    Page<ZoneResponse> getAllZones(AllZoneRequest request);
    ZoneDetailResponse getZoneById(String id);
    UpdateZoneResponse updateZone(String id, ZoneRequest request);
}
