package com.ms.system.service.impl;

import java.util.List;
import com.ms.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.system.mapper.DeviceMapper;
import com.ms.system.domain.Device;
import com.ms.system.service.IDeviceService;

/**
 * 设备台账Service业务层处理
 * 
 * @author ms
 * @date 2026-05-12
 */
@Service
public class DeviceServiceImpl implements IDeviceService 
{
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询设备台账
     * 
     * @param id 设备台账主键
     * @return 设备台账
     */
    @Override
    public Device selectDeviceById(Long id)
    {
        return deviceMapper.selectDeviceById(id);
    }

    /**
     * 查询设备台账列表
     * 
     * @param device 设备台账
     * @return 设备台账
     */
    @Override
    public List<Device> selectDeviceList(Device device)
    {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     * 新增设备台账
     * 
     * @param device 设备台账
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)
    {
        device.setCreateTime(DateUtils.getNowDate());
        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改设备台账
     * 
     * @param device 设备台账
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)
    {
        device.setUpdateTime(DateUtils.getNowDate());
        return deviceMapper.updateDevice(device);
    }

    /**
     * 批量删除设备台账
     * 
     * @param ids 需要删除的设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceByIds(Long[] ids)
    {
        return deviceMapper.deleteDeviceByIds(ids);
    }

    /**
     * 删除设备台账信息
     * 
     * @param id 设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceById(Long id)
    {
        return deviceMapper.deleteDeviceById(id);
    }
}
