package com.erasm.service;

import java.util.List;
import com.erasm.dto.AllocationDTO;

public interface AllocationService {

    AllocationDTO saveAllocation(AllocationDTO allocationDTO);

    AllocationDTO getAllocationById(Long allocationId);

    List<AllocationDTO> getAllAllocations();

    AllocationDTO updateAllocation(Long allocationId, AllocationDTO allocationDTO);

    void deleteAllocation(Long allocationId);
}