## @Resource 和 @Autowired 的核心区别

**@Autowired** 是 Spring 框架的原生注解，默认按**类型**（byType）进行自动装配。
当找到多个同类型 Bean 时，会尝试按属性名称匹配，如果仍不匹配则报错。
它支持 `required=false` 属性来声明可选依赖，也支持构造器注入，但需要配合 `@Qualifier` 才能按名称注入。
缺点是会与 Spring 框架产生耦合。

```
@Component
public class UserService {
    
    // 按类型注入
    @Autowired
    private UserDao userDao;
    
    // 按名称注入（配合 @Qualifier）
    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;
    
    // 可选依赖（找不到时不报错）
    @Autowired(required = false)
    private LogService logService;
    
    // 构造器注入（推荐）
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    // setter 注入
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

**@Resource** 是 Java 标准（JSR-250）注解，不限于 Spring 框架，默认按**名称**（byName）进行装配。
它会优先使用 `name` 属性指定的名称，如果没有指定则默认使用字段名作为 Bean 名称查找，找不到时才会回退到按类型查找。
它不支持 `required=false` 和构造器注入，但无需额外注解即可实现按名称注入，与框架解耦性更好。

```
@Component
public class UserService {
    
    // 默认按名称注入（属性名 = bean 名称）
    @Resource
    private UserDao userDao;  // 查找名为 "userDao" 的 Bean
    
    // 显式指定名称
    @Resource(name = "userDaoImpl")
    private UserDao userDao;
    
    // 按类型注入（指定 type 属性）
    @Resource(type = UserDao.class)
    private UserDao userDao;
    
    // 方法注入
    @Resource(name = "userDaoImpl")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
```

**简单记忆**：@Autowired 先类型后名称，@Resource 先名称后类型。
如果项目可能迁移到其他 IoC 容器（如 Guice），优先用 @Resource；
如果深度依赖 Spring 特性（如构造器注入、可选依赖），则用 @Autowired。

eg:
```
实战对比示例
场景：存在多个同类型 Bean
java
@Repository
public class UserDaoMySQL implements UserDao {}

@Repository
public class UserDaoOracle implements UserDao {}
使用 @Autowired
java
@Service
public class UserService {
    
    // ❌ 报错：找到多个匹配（UserDaoMySQL, UserDaoOracle）
    @Autowired
    private UserDao userDao;
    
    // ✅ 解决方案1：配合 @Qualifier
    @Autowired
    @Qualifier("userDaoMySQL")
    private UserDao userDao;
    
    // ✅ 解决方案2：属性名匹配
    @Autowired
    private UserDao userDaoMySQL;  // 名称匹配成功
}
使用 @Resource
java
@Service
public class UserService {
    
    // ✅ 按名称匹配（userDaoMySQL）
    @Resource
    private UserDao userDaoMySQL;
    
    // ✅ 显式指定名称
    @Resource(name = "userDaoOracle")
    private UserDao userDao;
    
    // ❌ 报错：未指定名称，按字段名找不到，按类型找到多个
    @Resource
    private UserDao userDao;
}



耦合度看的是：你的代码是否"赖上"了某个特定框架。

@Autowired 赖上了 Spring → 高耦合

@Resource 赖上了 Java 标准 → 低耦合

实际开发中，大多数项目不会换框架，所以接受 Spring 的高耦合也没问题。但如果写通用组件或追求极致解耦，就用 @Resource。


  │
  ├─ 需要构造器注入？ ────Yes──→ 用 @Autowired
  │
  ├─ 需要 required=false？ ──Yes──→ 用 @Autowired
  │
  ├─ 需要 @Qualifier？ ────Yes──→ 用 @Autowired
  │
  ├─ 开发通用组件？ ──────Yes──→ 用 @Resource
  │
  ├─ 按名称注入更清晰？ ───Yes──→ 用 @Resource
  │
  └─ 普通 Spring Boot 项目 ─────→ 两者皆可（推荐 @Autowired）
```

