package top.aichezhan.ssyx.sys.service;

import top.aichezhan.ssyx.model.sys.Region;

import java.util.List;

public interface RegionService {
    List<Region> findRegionByKeyword(String keyword);
}
