package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.dto.OrgDTO;
import xyz.gits.boot.api.system.entity.Org;
import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.common.core.response.RestResponse;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IOrgService extends BasicService<Org> {

    /**
     * 获取所有启用机构
     */
    List<Org> getEnableOrgList();

    /**
     * 新增机构
     * @param dto
     */
    Boolean saveOrg(OrgDTO dto);

    /**
     * 修改机构
     * @param dto
     * @return
     */
    RestResponse updateOrg(OrgDTO dto);

    /**
     * 删除机构，机构下存在用户和有子机构时，不能删除
     * @param orgId
     * @return
     */
    RestResponse delete(String orgId);

}
