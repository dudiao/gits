package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.system.vo.org.OrgTree;
import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.system.dto.org.OrgDTO;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.system.vo.org.OrgTreeVO;
import xyz.gits.boot.system.vo.org.OrgVO;

import java.util.List;
import java.util.Map;

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
     * 根据机构状态查询机构树，默认先从缓存中获取
     *
     * @param stopStatus 启停状态
     * @return {@link List<OrgTree>}
     * @author null
     * @date 2020/7/20 09:44
     */
    List<OrgTree> getOrgTree(String stopStatus);

    /**
     * 机构管理新增
     *
     * @param orgDTO 机构管理新增传输对象
     * @author null
     * @date 2020/5/27 9:49
     */
    void addOrg(OrgDTO orgDTO);

    /**
     * 机构管理-修改
     *
     * @param orgDTO 机构管理修改传输对象
     * @author null
     * @date 2020/5/27 10:16
     */
    void updateOrg(OrgDTO orgDTO);

    /**
     * 根据ID查询机构信息
     *
     * @param orgId 机构ID
     * @return 如果机构为空则返回机构不存在
     * @author null
     * @date 2020/5/27 10:55
     */
    OrgVO findById(String orgId);

    /**
     * 以tree形式获取机构
     *
     * @param stopStatus {@link StopStatus}
     * @return 返回机构树格式
     * @author null
     * @date 2020/5/27 11:42
     */
    List<OrgTreeVO> getOrgListByStopStatus(StopStatus stopStatus);

    /**
     * 根据机构启停状态查询机构信息
     *
     * @param stopStatus 启停标识 {@link StopStatus}
     * @return 机构集合 {@link List< Org>}
     * @author null
     * @date 2020/5/27 11:45
     */
    List<Org> findOrgByStopStatus(StopStatus stopStatus);

    /**
     * 修改机构启停标志 {@link StopStatus}
     *
     * @param orgId      机构ID
     * @param stopStatus 启用停用标志[0:启用;1:停用]
     * @param stopReason 停用原因
     * @author null
     * @date 2020/6/15 17:13
     */
    void updateOrgStatus(String orgId, StopStatus stopStatus, String stopReason);

    /**
     * 获取机构map信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @return 机构map k-orgId,y-List<OrgTreeVO> {@link Map < String, List< OrgTreeVO>>}
     * @author null
     * @date 2020/5/29 14:27
     */
    Map<String, List<OrgTreeVO>> getAllSubOrgIds();

    /**
     * 获取机构map信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param stopStatus {@link StopStatus}
     * @return 机构map k-orgId , k-Org {@link Map< String, Org>}
     * @author null
     * @date 2020/5/29 15:47
     */
    Map<String, Org> getOrgMap(StopStatus stopStatus);

    /**
     * 根据机构id查询机构信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param orgId 机构id
     * @return {@link Org}
     * @author null
     * @date 2020/5/29 17:48
     */
    Org getOrgById(String orgId);

}
