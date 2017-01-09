package cn.rain.usermanage.service.impl;

import cn.rain.usermanage.dao.UserDao;
import cn.rain.usermanage.dao.impl.UserDaoImpl;
import cn.rain.usermanage.domain.User;
import cn.rain.usermanage.service.UserService;
import cn.rain.usermanage.utils.SystemConstant;
import cn.rain.usermanage.utils.UploadUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by Rain on 2016-12-17.
 * 用户管理系统
 */
public class UserServiceImpl implements UserService {

    /**
     * 校验登录名是否存在
     * @param user
     * @return
     */
    @Override
    public User checkLogonName(User user) {
        UserDao dao = new UserDaoImpl();
        return dao.checkLogonName(user);
    }

    /**
     * 添加用户
      * @param user
     */
    @Override
    public void add(User user) {
        //判断是否上传文件
        if (user.getUpload()!=null) {
            //生成一个uuid的文件名
             String uuid = UploadUtils.generateRandonFileName(user.getUploadFileName());
            //通过uuid文件名获得一个二级目录
             String dir = UploadUtils.generateRandomDir(uuid);
            //生成一个保存路径
             String path = SystemConstant.DEFAULT_DIR + dir+ "/" + uuid;

            try {
                //文件上传
                FileUtils.copyFile(user.getUpload(),new File(path));
                //设置上传路径
                user.setPath(path);
                //设置上传文件放入名称
                user.setFilename(user.getUploadFileName());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件上传错误!");
            }
        }
        UserDao dao = new UserDaoImpl();
        dao.add(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        UserDao dao = new UserDaoImpl();
        User result =  dao.login(user);
        return result;
    }
}
