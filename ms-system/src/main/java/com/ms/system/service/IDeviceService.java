package com.ms.system.service;

import java.util.List;
import com.ms.system.domain.Device;

/**
 * 设备台账Service接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface IDeviceService 
{
    /**
     * 查询设备台账
     * 
     * @param id 设备台账主键
     * @return 设备台账
     */
    public Device selectDeviceById(Long id);

    /**
     * 查询设备台账列表
     * 
     * @param device 设备台账
     * @return 设备台账集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备台账
     * 
     * @param device 设备台账
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备台账
     * 
     * @param device 设备台账
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 批量删除设备台账
     * 
     * @param ids 需要删除的设备台账主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(Long[] ids);

    /**
     * 删除设备台账信息
     * 
     * @param id 设备台账主键
     * @return 结果
     */
    public int deleteDeviceById(Long id);
}
