/*
 * Created by jz on 2019/4/14.
 */
//

#ifndef LINUX_0_11_MYDEFINES_H
#define LINUX_0_11_MYDEFINES_H

#include <linux/sched.h>

#define PROCESS_TRACE_LOG_FD 3
#define RECORD_N(pid) printProcessTrace(pid, 'N')
#define RECORD_J(pid) printProcessTrace(pid, 'J')
#define RECORD_R(pid) printProcessTrace(pid, 'R')
#define RECORD_W(pid) printProcessTrace(pid, 'W')
#define RECORD_E(pid) printProcessTrace(pid, 'E')

extern int fprintk(int fd, char *fmt, ...);

// 进程日志描述符
static int printProcessTrace(int pid, char state)
{
    return fprintk(PROCESS_TRACE_LOG_FD, "%d\t%c\t%d\n", pid, state, jiffies);
}

#endif //LINUX_0_11_MYDEFINES_H
