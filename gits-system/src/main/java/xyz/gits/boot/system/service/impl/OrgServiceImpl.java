package xyz.gits.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.dto.OrgDTO;
import xyz.gits.boot.api.system.entity.Org;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.enums.StopFlag;
import xyz.gits.boot.api.system.vo.OrgTree;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.constants.SystemConstants;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.core.utils.TreeUtil;
import xyz.gits.boot.api.system.utils.UserUtil;
import xyz.gits.boot.system.mapper.OrgMapper;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.system.service.IUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class OrgServiceImpl extends BasicServiceImpl<OrgMapper, Org> implements IOrgService {

    @Autowired
    private IUserService userService;

    /**
     * 获取所有启用机构
     */
    @Override
    @Cached(name = CacheConstants.ORG_TREE)
    public List<OrgTree> getOrgTree() {
        List<Org> orgList = baseMapper.selectList(Wrappers.<Org>lambdaQuery()
            .orderByDesc(Org::getOrgOrder)
            .eq(Org::getRecordStopFlag, StopFlag.ENABLE));

        List<OrgTree> orgTrees = orgList.stream().map(org -> {
            OrgTree tree = new OrgTree();
            tree.setId(org.getOrgId());
            tree.setParentId(org.getParentOrgId());
            tree.setOrgName(org.getOrgName());
            return tree;
        }).collect(Collectors.toList());
        return TreeUtil.bulid(orgTrees, SystemConstants.ORG_ROOT_ID);
    }

    /**
     * 新增机构
     *
     * @param dto
     */
    @Override
    @CacheInvalidate(name = CacheConstants.ORG_TREE)
    public Boolean saveOrg(OrgDTO dto) {
        Org org = new Org();
        BeanUtils.copyPropertiesIgnoreNull(dto, org);
        org.setCreateUserId(UserUtil.getUserId());
        org.setCreateTime(LocalDateTime.now());
        baseMapper.insert(org);
        return Boolean.TRUE;
    }

    /**
     * 修改机构
     *
     * @param dto
     */
    @Override
    @CacheInvalidate(name = CacheConstants.ORG_TREE)
    public void updateOrg(OrgDTO dto) {
        Org org = baseMapper.selectById(dto.getOrgId());
        if (ObjectUtil.isEmpty(org)) {
            log.warn("[机构管理 - 修改] - 机构[OrgId={}]不存在", dto.getOrgId());
            throw new SystemNoLogException(ResponseCode.ORG_NOT_EXIST);
        }
        Org entity = new Org();
        BeanUtils.copyPropertiesIgnoreNull(dto, org);
        entity.setUpdateUserId(UserUtil.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(org);
    }

    /**
     * 删除机构，机构下存在用户和有子机构时，不能删除
     *
     * @param orgId
     */
    @Override
    @CacheInvalidate(name = CacheConstants.ORG_TREE)
    public void delete(String orgId) {
        IPage<User> page = userService.getPage(Wrappers.<User>lambdaQuery().eq(User::getOrgId, orgId));
        if (page.getTotal() > 0) {
            log.warn("[机构管理 - 删除] - 机构[OrgId={}]下存在用户", orgId);
            throw new SystemNoLogException(ResponseCode.ORG_HAVE_USER);
        }
        List<Org> orgs = baseMapper.selectList(Wrappers.<Org>lambdaQuery().eq(Org::getParentOrgId, orgId));
        if (CollUtil.isNotEmpty(orgs)) {
            log.warn("[机构管理 - 删除] - 机构[OrgId={}]存在子机构", orgId);
            throw new SystemNoLogException(ResponseCode.ORG_HAVE_SUB);
        }

        this.removeById(orgId);
    }
}
