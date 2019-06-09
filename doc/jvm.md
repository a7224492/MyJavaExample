##类加载
- ###什么是类加载
    - 类加载就是把class文件的数据加载到内存中，解析为一个class结构的过程
    
- ###类加载的典型应用
    - 修改一个java文件源代码后，无需重启进程即可生效，可以参考一下知识点
        - instrumentation
        - javaagent
            - agentMain