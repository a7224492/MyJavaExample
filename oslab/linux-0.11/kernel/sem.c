//
// Created by jiangzhen on 2019/4/23.
//
#include <linux/sched.h>
#include <string.h>
#include <asm/system.h>

#define MAX_SEM_NUM 10
#define MAX_NAME_LEN 10

struct sem_st
{
    char name[MAX_NAME_LEN];
    struct task_struct *head;
    int use;
    int sem_v;
};

struct sem_st ksem[MAX_SEM_NUM];
static int init = 0;
static int sem_v;

int sys_sem_open(const char *name, int value)
{
    int i, unused;
    int nameLen;

    if (name == NULL || (nameLen = strlen(name)) == 0 || value < 0) {
        return -1;
    }

    cli();

    if (!init)
    {
        for (i = 0; i < MAX_SEM_NUM; ++i) {
            memset(ksem[i].name, 0, MAX_NAME_LEN);
            ksem[i].use = 0;
            ksem[i].head = NULL;
        }

        init = 1;
    }

    for (i = 0; i < MAX_SEM_NUM; ++i) {
        if (strcmp(ksem[i].name, name) == 0) {
            return i;
        }

        if (!ksem[i].use) {
            unused = i;
        }
    }

    strncpy(ksem[unused].name, name, nameLen);
    ksem[unused].use = 1;
    ksem[unused].sem_v = value;

    sti();

    return i;
}

int sys_sem_wait(int sem)
{
    if (sem < 0 || sem >= MAX_SEM_NUM) {
        return -1;
    }

    cli();

    if (!ksem[sem].use) {
        return -1;
    }

    ksem[sem].sem_v--;
    while (ksem[sem].sem_v < 0) {
        sleep_on(ksem[sem].head);
    }

    sti();
}

int sys_sem_post(int sem)
{
    if (sem < 0 || sem >= MAX_SEM_NUM) {
        return -1;
    }

    cli();

    if (!ksem[sem].use) {
        return -1;
    }

    ksem[sem].sem_v++;
    while (ksem[sem].sem_v <= 0) {
        wake_up(ksem[sem].head);
    }

    sti();
}

int sys_sem_unlink(int sem)
{
    return 0;
}