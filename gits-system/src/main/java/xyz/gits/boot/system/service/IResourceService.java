package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.common.core.basic.BasicService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IResourceService extends BasicService<Resource> {

    List<Resource> selectListByUser(String userId);

}
