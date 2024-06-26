package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.Code;
import com.example.controller.Result;
import com.example.dao.DoctorDao;
import com.example.dao.UserDao;
import com.example.domain.Checkups;
import com.example.dao.CheckupsDao;
import com.example.domain.Doctor;
import com.example.domain.User;
import com.example.service.ICheckupsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 戴金磊
 * @since 2024-04-18
 */
@Service
public class CheckupsServiceImpl extends ServiceImpl<CheckupsDao, Checkups> implements ICheckupsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CheckupsDao checkupsDao;
    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Result select(Checkups checkups) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        if (checkups.getUser().getUsername() != null && !"".equals(checkups.getUser().getUsername())) {
            userWrapper.like("username", checkups.getUser().getUsername());
        }
        List<User> users = userDao.selectList(userWrapper);
        QueryWrapper<Checkups> wrapper = new QueryWrapper<>();
        if (users != null) {
            List<Integer> userIds = new ArrayList<>();
            for (User user : users) {
                userIds.add(user.getUserID());
            }
            wrapper.in("userID", userIds);
        }
        if (checkups.getCheckupId() != null) {
            wrapper.eq("checkup_id", checkups.getCheckupId());
        }
        if (checkups.getCheckupDate() != null) {
            wrapper.eq("checkup_date", checkups.getCheckupDate());
        }
        if (checkups.getUserID() != null) {
            wrapper.eq("userID ", checkups.getUserID());
        }
        List<Checkups> list = checkupsDao.selectList(wrapper);
        if (list == null) {
            return new Result(null, Code.GET_ERR, "查询不到数据");
        }
        setUser(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    private void setUser(List<Checkups> list) {
        for (Checkups c : list) {
            User user = userDao.selectById(c.getUserID());
            c.setUser(user);
        }
    }

    @Override
    public Result getCheckups() {
        //        获取医生id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        Doctor doctor = doctorDao.selectByUserID(userID);
        QueryWrapper<Checkups> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctorID", doctor.getDoctorID());
        List<Checkups> checkups = checkupsDao.selectList(queryWrapper);
        setUser(checkups);
        if (checkups.size()==0){
            return new Result(null, Code.GET_ERR, "未查询到数据");
        }
        return new Result(checkups, Code.GET_OK, "查询成功");
    }

    @Override
    public Result add(Checkups checkups) {
        User user = userDao.selectById(checkups.getUserID());
        if (user == null) {
            return new Result(checkups, Code.SAVE_ERR, "新增失败，系统中无此人员，请先自行注册或检查账号是否正确");
        }
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        wrapper.eq("userID", userID);
        Doctor doctor = doctorDao.selectOne(wrapper);
        checkups.setDoctorID(doctor.getDoctorID());
        setCheckupsDate(checkups);
        int insert = checkupsDao.insert(checkups);
        if (insert != 0) {
            return new Result(checkups, Code.SAVE_OK, "新增成功");
        } else {
            return new Result(checkups, Code.SAVE_ERR, "新增失败，请联系管理员");
        }
    }

    private void setCheckupsDate(Checkups checkups) {
        try {
            // 解析前端传递的日期字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(checkups.getCheckupDate());
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            LocalDate nextDay = localDate.plusDays(1);
            Date nextDate = Date.from(nextDay.atStartOfDay(zoneId).toInstant());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nextDayFormatted = outputFormat.format(nextDate);
            checkups.setCheckupDate(nextDayFormatted);
        } catch (Exception e) {
            System.out.println("日期转换失败：" + e.getMessage());
        }
    }

    @Override
    public Result update(Checkups checkups) {
        Checkups selectById = checkupsDao.selectById(checkups.getCheckupId());
        if (selectById == null) {
            return new Result(null, Code.UPDATE_ERR, "无此用户的体检报告");
        }
        setCheckupsDate(checkups);
        checkupsDao.updateById(checkups);
        return new Result(checkups, Code.UPDATE_OK, "修改成功");
    }

    @Override
    public Result delete(Integer id) {
        int i = checkupsDao.deleteById(id);
        if (i != 0) {
            return new Result(null, Code.DELETE_OK, "删除成功");
        } else {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
    }

    @Override
    public Result getme() {
        QueryWrapper<Checkups> wrapper = new QueryWrapper<>();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        wrapper.eq("userID", userID);
        List<Checkups> list = checkupsDao.selectList(wrapper);
        setUserAndDoctor(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    private void setUserAndDoctor(List<Checkups> list) {
        if (list != null) {
            for (Checkups checkups : list) {
                User user = userDao.selectById(checkups.getUserID());
                checkups.setUser(user);
                Doctor doctor = doctorDao.selectById(checkups.getDoctorID());
                if (doctor != null) {
                    User selectById = userDao.selectById(doctor.getUserID());
                    doctor.setUser(selectById);
                }
                checkups.setDoctor(doctor);
            }
        }
    }

    @Override
    public Result getAll() {
        List<Checkups> list = checkupsDao.getAll();
        setUserAndDoctor(list);
        return new Result(list, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getUserHeartRatesWithDate(Integer userID) {
        List<Checkups> list = checkupsDao.selectListByuserID(userID);

        // 统计心率数据
        Map<String, Integer> heartRateData = new HashMap<>();
        for (Checkups orderItem : list) {
            heartRateData.put(orderItem.getCheckupDate(), orderItem.getHeartRate());
        }

        return new Result(heartRateData, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getMyHeartRatesWithDate() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        List<Checkups> list = checkupsDao.selectListByuserID(userID);

        // 统计心率数据
        Map<String, Integer> heartRateData = new HashMap<>();
        for (Checkups orderItem : list) {
            heartRateData.put(orderItem.getCheckupDate(), orderItem.getHeartRate());
        }

        return new Result(heartRateData, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getUserBloodPressureWithDate(Integer userID) {
        List<Checkups> list = checkupsDao.selectListByuserID(userID);
        Map<String, Map<String, Integer>> bloodPressureData = new HashMap<>();
        for (Checkups checkup : list) {
            Map<String, Integer> pressureData = new HashMap<>();
            pressureData.put("highPressure", checkup.getBloodPressure());
            pressureData.put("lowPressure", checkup.getBloodPressureLow());
            bloodPressureData.put(checkup.getCheckupDate(), pressureData);
        }

        return new Result(bloodPressureData, Code.GET_OK, "查询成功");
    }

    @Override
    public Result getMyBloodPressureWithDate() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("userID");
        List<Checkups> list = checkupsDao.selectListByuserID(userID);
        Map<String, Map<String, Integer>> bloodPressureData = new HashMap<>();
        for (Checkups checkup : list) {
            Map<String, Integer> pressureData = new HashMap<>();
            pressureData.put("highPressure", checkup.getBloodPressure());
            pressureData.put("lowPressure", checkup.getBloodPressureLow());
            bloodPressureData.put(checkup.getCheckupDate(), pressureData);
        }

        return new Result(bloodPressureData, Code.GET_OK, "查询成功");
    }

}
