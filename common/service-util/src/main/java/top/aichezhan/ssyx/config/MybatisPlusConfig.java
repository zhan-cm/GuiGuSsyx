package top.aichezhan.ssyx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("top.aichezhan.ssyx.*.mapper")
public class MybatisPlusConfig {

    //mp插件
    @Bean
    public MybatisPlusInterceptor optimisticLockingInterceptor() {
        // 1. 创建一个 MyBatis-Plus 拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2. 给拦截器添加一个分页插件（指定数据库为 MySQL）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 3. 返回配置好的拦截器，交给 Spring 管理
        return interceptor;
    }
    /*
    * 设置Mybatis的拦截器，用于实现分页功能
    *
    * 代码中的方法名是 optimisticLockingInterceptor()，但实际添加的是分页插件
    *
    * MyBatis-Plus 的插件系统是基于拦截器设计的。分页功能不是内置的，而是通过拦截器在 SQL 执行前"拦截"并修改 SQL 语句。
    *
    * Spring 需要知道这个拦截器的存在，才能在 MyBatis-Plus 执行 SQL 时让它发挥作用。
    *
    * ==============================================================
    *
    * 你写的代码：userMapper.selectPage(page, null)
          ↓
      拦截器拦截
          ↓
      自动修改 SQL：SELECT * FROM user → SELECT * FROM user LIMIT 0,10
         ↓
      执行修改后的 SQL
      *
      * ===============================================================
    * */
}
