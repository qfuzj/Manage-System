package com.ms.system.mapper;

import java.util.List;
import com.ms.system.domain.Device;

/**
 * 设备台账Mapper接口
 * 
 * @author ms
 * @date 2026-05-12
 */
public interface DeviceMapper 
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
     * 删除设备台账
     * 
     * @param id 设备台账主键
     * @return 结果
     */
    public int deleteDeviceById(Long id);

    /**
     * 批量删除设备台账
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(Long[] ids);
}
