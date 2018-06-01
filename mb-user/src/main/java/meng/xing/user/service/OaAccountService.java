package meng.xing.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class OaAccountService implements AccountService {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(OaAccountService.class);

    @Autowired
    public OaAccountService(@Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean initAccount(String account, String password, long account_id) {
        String sql = "SELECT count(*) FROM sys_user where account='" + account + "'";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        if (count != 0) {
            sql = "SELECT password FROM sys_user where account='" + account + "'";
            String oaPassword = jdbcTemplate.queryForObject(sql, String.class);
            LOGGER.warn("Oa模块已注册:{},用户密码：{}，现有加密得出的密码：{}", account, oaPassword, md5(md5(password) + account));
            return false;
        } else {
            LOGGER.info("Oa模块初始化开始:{}", account);
            // TODO: 2018/6/1  
            LOGGER.info("Oa模块初始化完成:{}", account);
            return true;
        }
    }

    //    md5(md5($password) . $account)
    private String md5(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
            StringBuilder buf = new StringBuilder();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
