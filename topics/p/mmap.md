## MMAP内存映射
常规文件操作需要从磁盘到页缓存再到用户主内存的两次数据拷贝，由于页缓存（Page Cache 它位于内存和文件之间缓冲区，文件IO操作实际上只和page cache交互，不直接和内存交互）处在内核空间，不能被用户进程直接寻址，所以还需要将页缓存中数据页再次拷贝到内存对应的用户空间中。而mmap操作文件，先建立了虚拟内存区域和文件磁盘地址的映射，只需要从磁盘到用户主内存的一次数据拷贝过程。

[从内核文件系统看文件读写过程](http://www.cnblogs.com/huxiao-tee/p/4657851.html)

[分析mmap](http://www.cnblogs.com/huxiao-tee/p/4660352.html)

[linux mmap](http://man7.org/linux/man-pages/man2/mmap.2.html)
