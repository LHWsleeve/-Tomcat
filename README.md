# -Tomcat
基于tomcat4/5
包目录：
│  README.md
│
└─Tomcat
    │  pom.xml
    │  Tomcat.iml
    │
    ├─.idea
    │
    ├─CatalinaAPI
    │
    ├─ch01-SimpleHttp
    │
    ├─ch02-SimpleServlet
    │....
    ├─lib
    │
    └─webroot

**Tomcat:** Maven父模块
**ch0x-xxx:** 子模块，渐进式实现简版tomcat
**CatalinaAPI：** Catalina内核
**lib:** 引入的第三方jar包，主要还是tomcat
**webroot:** 静态资源文件

---
ch01-SimpleHttp:简易实现Http请求和响应
ch02-SimpleServlet：实现处理简单的servlet和静态资源



---


# 参考资料
- 《深入剖析Tomcat》
- 《Tomcat架构解析》
