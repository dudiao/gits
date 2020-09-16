package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.api.system.utils.ConvertUtils;
import xyz.gits.boot.system.vo.org.OrgTree;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.constants.SystemConstants;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.core.utils.TreeUtil;
import xyz.gits.boot.system.dto.org.OrgDTO;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.system.mapper.OrgMapper;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.system.vo.org.OrgTreeVO;
import xyz.gits.boot.system.vo.org.OrgVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private OrgMapper orgMapper;

    /**
     * 根据机构状态查询机构树，默认先从缓存中获取
     *
     * @param stopStatus 启停状态
     * @return {@link List<OrgTree>}
     * @author null
     * @date 2020/7/20 09:44
     */
    @Override
    @Cached(name = CacheConstants.ORG_TREE, key = "#stopStatus")
    public List<OrgTree> getOrgTree(String stopStatus) {
        // stopStatus=all 时，查出所有机构
        StopStatus status = null;
        if (!SystemConstants.ALL.equalsIgnoreCase(stopStatus)) {
            status = StopStatus.fromString(stopStatus);
        }

        List<Org> orgList = baseMapper.selectList(Wrappers.<Org>lambdaQuery()
            .orderByDesc(Org::getOrderNum)
            .eq(!SystemConstants.ALL.equalsIgnoreCase(stopStatus), Org::getStopStatus, status));

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
     * 机构管理新增
     *
     * @param orgDTO 机构管理新增传输对象
     * @author null
     * @date 2020/5/27 9:49
     */
    @Override
    public void addOrg(OrgDTO orgDTO) {
        Org org = orgMapper.selectById(orgDTO.getOrgId());
        //判断机构ID是否存在
        if (ObjectUtil.isNotEmpty(org)) {
            log.warn("[机构管理-机构新增]-机构已存在,机构id:orgId={}", orgDTO.getOrgId());
            throw new SystemNoLogException(ResponseCode.ORG_IS_EXIST);
        }
        Org addOrg = new Org();
        addOrg.setStopStatus(StopStatus.ENABLE);
        //拷贝字段
        BeanUtils.copyPropertiesIgnoreNull(orgDTO, addOrg);
        //补全信息
        addOrg.setCreateUserId(AuthUtils.getUserId());
        addOrg.setCreateTime(LocalDateTime.now());
        addOrg.setStopStatus(StopStatus.fromString(orgDTO.getStopStatusCode()));
        //检验父节点有效性并赋值
        checkOrgPId(orgDTO);
        orgMapper.insert(addOrg);
    }

    /**
     * 检验父节点有效性并赋值
     *
     * @param orgDTO
     * @return
     * @author null
     * @date 2020/7/8 14:32
     */
    private void checkOrgPId(OrgDTO orgDTO) {
        //判断添加节点是否父节点
        if (!StrUtil.equals(orgDTO.getParentOrgId(), SystemConstants.TOP_ORG)) {
            Org POrg = orgMapper.selectById(orgDTO.getParentOrgId());
            if (ObjectUtil.isEmpty(POrg)) {
                log.warn("[机构管理-机构新增]-无效机构父节点,父节点ID:parentOrgId={}", orgDTO.getParentOrgId());
                throw new SystemNoLogException(ResponseCode.ORG_PID_ERROR);
            }
        }
    }

    /**
     * 机构管理-修改
     *
     * @param orgDTO 机构管理修改传输对象
     * @author null
     * @date 2020/5/27 10:16
     */
    @Override
    public void updateOrg(OrgDTO orgDTO) {
        Org org = orgMapper.selectById(orgDTO.getOrgId());
        if (ObjectUtil.isEmpty(org)) {
            log.warn("[机构管理-机构修改]-机构不存在,机构id:orgId={}", orgDTO.getOrgId());
            throw new SystemNoLogException(ResponseCode.ORG_NOT_EXIST);
        }
        //拷贝字段
        BeanUtils.copyPropertiesIgnoreNull(orgDTO, org);
        //获取修改用户信息
        org.setUpdateUserId(AuthUtils.getUserId());
        org.setUpdateTime(LocalDateTime.now());
        //检验父节点有效性并赋值
        checkOrgPId(orgDTO);
        orgMapper.updateById(org);
    }

    /**
     * 根据ID查询机构信息
     *
     * @param orgId 机构ID
     * @return 如果机构为空则返回机构不存在
     * @author null
     * @date 2020/5/27 10:55
     */
    @Override
    public OrgVO findById(String orgId) {
        Org org = orgMapper.selectById(orgId);
        if (BeanUtil.isEmpty(org)) {
            log.warn("[机构管理-根据Id查询机构]-机构不存在,机构id:orgId={}", orgId);
            throw new SystemNoLogException(ResponseCode.ORG_NOT_EXIST);
        }
        OrgVO orgVO = new OrgVO();
        BeanUtils.copyPropertiesIgnoreNull(org, orgVO);
        ConvertUtils.convertUser(orgVO);
        return orgVO;
    }

    /**
     * 以tree形式获取机构
     *
     * @param stopStatus {@link StopStatus}
     * @return 返回机构树格式
     * @author null
     * @date 2020/5/27 11:42
     */
    @Override
    public List<OrgTreeVO> getOrgListByStopStatus(StopStatus stopStatus) {
        List<Org> orgList = findOrgByStopStatus(stopStatus);
        //List<Org>  ->  List<OrgTreeVO>
        List<OrgTreeVO> collect = orgListToOrgTreeVOS(orgList);
        List<OrgTreeVO> orgVOS = new ArrayList<>();
        //转换为树结构
        if (CollectionUtil.isNotEmpty(collect)) {
            orgVOS = TreeUtil.bulid(collect, SystemConstants.TOP_ORG);
        }
        return orgVOS;
    }

    /**
     * List<Org>  ->  List<OrgTreeVO>
     *
     * @param orgList 机构集合
     * @return {@link List< OrgTreeVO>}
     * @author null
     * @date 2020/7/8 14:28
     */
    private List<OrgTreeVO> orgListToOrgTreeVOS(List<Org> orgList) {
        return orgList.stream().map(org -> {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            orgTreeVO.setId(org.getOrgId());
            orgTreeVO.setParentId(org.getParentOrgId());
            orgTreeVO.setOrgName(org.getOrgName());
            orgTreeVO.setAreaCode(org.getAreaCode());
            return orgTreeVO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据机构启停状态查询机构信息
     *
     * @param stopStatus 启停标识 {@link StopStatus}
     * @return 机构集合 {@link List< Org>}
     * @author null
     * @date 2020/5/27 11:45
     */
    @Override
    public List<Org> findOrgByStopStatus(StopStatus stopStatus) {
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BeanUtil.isNotEmpty(stopStatus), Org::getStopStatus, stopStatus).orderByDesc(Org::getOrderNum);
        List<Org> orgList = orgMapper.selectList(queryWrapper);
        return orgList;
    }

    /**
     * 修改机构启停标志 {@link StopStatus}
     *
     * @param orgId      机构ID
     * @param stopStatus 启用停用标志[0:启用;1:停用]
     * @param stopReason 停用原因
     * @author null
     * @date 2020/6/15 17:13
     */
    @Override
    public void updateOrgStatus(String orgId, StopStatus stopStatus, String stopReason) {
        if (stopStatus.equals(StopStatus.STOP) && BeanUtil.isEmpty(stopReason)) {
            log.warn("[机构管理-修改机构启停状态] - 停用原因不能为空");
            throw new SystemNoLogException(ResponseCode.ORG_STOP_REASON);
        }
        Org org = orgMapper.selectById(orgId);
        org.setStopStatus(stopStatus);
        orgMapper.updateById(org);
    }

    /**
     * 根据机构树，获取所有下级机构 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param orgTree 机构树
     * @param subList 下级机构列表
     * @return 机构树list
     * @author null
     * @date 2020/5/29 14:08
     */
    private List<OrgTreeVO> getSubOrgIds(List<OrgTreeVO> orgTree, List<OrgTreeVO> subList) {
        for (OrgTreeVO orgTreeVO : orgTree) {
            subList.add(orgTreeVO);
            if (CollUtil.isNotEmpty(orgTreeVO.getChildren())) {
                getSubOrgIds(orgTreeVO.getChildren(), subList);
            }
        }
        return subList;
    }

    /**
     * 获取机构map信息
     *
     * @return 机构map k-orgId,y-List<OrgVO> {@link Map < String, List< OrgVO>>}
     * @author null
     * @date 2020/5/29 14:27
     */
    @Override
    public Map<String, List<OrgTreeVO>> getAllSubOrgIds() {
        // TODO: 2020/5/29 缓存
        List<Org> orgList = findOrgByStopStatus(StopStatus.ENABLE);
        // List<Org> -> List<OrgTreeVOS>
        List<OrgTreeVO> orgTreeVOList = orgListToOrgTreeVOS(orgList);
        // 将 List<OrgVO> 转换成 k-orgId,y-List<OrgVO>
        Map<String, List<OrgTreeVO>> collect = orgTreeVOList.stream().collect(Collectors.toMap(orgTreeVO -> (String) orgTreeVO.getId(), orgTreeVO -> {
            //转换为树结构
            List<OrgTreeVO> orgTreeVOS = TreeUtil.bulid(orgTreeVOList, orgTreeVO.getId());
            ArrayList<OrgTreeVO> subList = new ArrayList();
            subList.add(orgTreeVO);
            return getSubOrgIds(orgTreeVOS, subList);
        }));
        return collect;
    }

    /**
     * 获取机构map信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param stopStatus {@link StopStatus}
     * @return 机构map k-orgId , k-Org {@link Map< String, Org>}
     * @author null
     * @date 2020/5/29 15:47
     */
    @Override
    public Map<String, Org> getOrgMap(StopStatus stopStatus) {
        // TODO: 2020/5/29 缓存
        List<Org> orgList = findOrgByStopStatus(stopStatus);
        Map<String, Org> orgMap = orgList.stream().collect(Collectors.toMap(Org::getOrgId, Function.identity()));
        return orgMap;
    }

    /**
     * 根据机构id查询机构信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param orgId 机构id
     * @return 机构信息 {@link Org}
     * @author null
     * @date 2020/5/29 17:48
     */
    @Override
    public Org getOrgById(String orgId) {
        // TODO: 2020/5/29 缓存
        return orgMapper.selectById(orgId);
    }
}
