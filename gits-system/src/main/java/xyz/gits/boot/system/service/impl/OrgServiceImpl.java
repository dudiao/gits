package xyz.gits.boot.system.service.impl;

import xyz.gits.boot.api.system.entity.Org;
import xyz.gits.boot.system.mapper.OrgMapper;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.common.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Service
public class OrgServiceImpl extends BasicServiceImpl<OrgMapper, Org> implements IOrgService {

}
