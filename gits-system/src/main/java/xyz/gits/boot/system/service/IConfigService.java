package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.system.dto.config.ConfigDTO;
import xyz.gits.boot.system.dto.config.ConfigQueryDTO;
import xyz.gits.boot.system.entity.Config;
import xyz.gits.boot.system.vo.config.ConfigVO;

/**
 * 配置中心 服务接口类
 *
 * @author songyinyin
 * @since 2020-05-20
 */
public interface IConfigService extends BasicService<Config> {

    /**
     * 新增配置
     *
     * @param configDTO 配置传输对象
     * @author null
     * @date 2020/6/15 15:04
     */
    void save(ConfigDTO configDTO);



    /**
     * 根据配置id删除
     *
     * @param configId 配置ID
     * @author null
     * @date 2020/5/25 17:37
     */
    void delete(String configId);


    /**
     * 修改
     *
     * @param configDTO 配置传输对象
     * @author null
     * @date 2020/5/25 17:38
     */
    void update(ConfigDTO configDTO);


    /**
     * 根据配置id查询
     *
     * @param configId 配置ID
     * @author null
     * @date 2020/5/27 15:51
     */
    ConfigVO find(String configId);


    /**
     * 查询所有配置
     *
     * @param  configDTO  配置传输对象
     * @return 前端配置展示对象 {@link Config}
     * @author null
     * @date 2020/5/25 17:38
     */
    IPage<Config> getPage(ConfigQueryDTO configDTO);


}
